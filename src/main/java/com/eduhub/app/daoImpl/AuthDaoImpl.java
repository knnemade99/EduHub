package com.eduhub.app.daoImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.eduhub.app.dao.AuthDao;
import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.util.Encrypt;

@Repository("authDao")
@PropertySource({ 
	  "classpath:messages.properties"
	})
public class AuthDaoImpl implements AuthDao {
	
	@Autowired
	public SessionFactory sessionFactory;
	@Autowired
	private Environment env;
	@Autowired
	CommonMethods commonMethods;

	/* login functionality implementation */
	@Override
	public ResponseEntity<?> login(UserCredential logincredentials) {
		System.out.println("login implementation called");
		Session session=sessionFactory.openSession();
		AuthTable authTable = new AuthTable();

		try{
			session.beginTransaction();
			
			/*fetching usercredential*/
			String fetchCredentialHQL = "FROM usercredential where username='"+logincredentials.getUsername()+"' and password='"+logincredentials.getPassword()+"'";
			Query fetchCredentialQuery = session.createQuery(fetchCredentialHQL);
			List<UserCredential> usercredentialResult = fetchCredentialQuery.list();
	
			/* check if user is valid */
			if(!usercredentialResult.isEmpty()){
				
				/* getting user record for the user credential */
				String fetchUserHQL = "FROM user where username='"+usercredentialResult.get(0).getUsername()+"'";
				Query fetchUserQuery = session.createQuery(fetchUserHQL);
				List<User> userResult = fetchUserQuery.list();
				
				/* check if user exist */
				if(!userResult.isEmpty()){
					/* generate random pin for authtoken */
					String randomPIN = (int)(Math.random()*9000)+1000 +"";
					
					/*removing old authtoken for user if present*/
					String deleteAuthTokenHQL = "delete FROM authtable where userId = :userId";
					Query deleteAuthTokenQuery = session.createQuery(deleteAuthTokenHQL);
					deleteAuthTokenQuery.setInteger("userId", userResult.get(0).getUserId());
					deleteAuthTokenQuery.executeUpdate();
					
					/*storing authtoken in database*/
					authTable.setUser(userResult.get(0));
					authTable.setAuthToken(Encrypt.encrypt(randomPIN));
					System.out.println(authTable + " auth");
					session.save(authTable);
				}
				else{
					authTable = null;
				}
			}
			else{
				authTable = null;
			}
		}
		catch(Exception e){
			session.getTransaction().rollback();
			authTable = null;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			if(authTable!=null){
				Map<String,String> responseMap = new HashMap<String,String>();
				responseMap.put("authToken", authTable.getAuthToken());
				responseMap.put("message",env.getProperty("message.success"));
				return ResponseEntity.ok(responseMap);
			}
			else{
				Map<String,String> responseMap = new HashMap<String,String>();
				responseMap.put("message", env.getProperty("message.worngusernameorpassword"));
				return new ResponseEntity<Map>(responseMap,HttpStatus.UNAUTHORIZED);
			}
		}
	}

	/* logout functionality implementaion */
	@Override
	public ResponseEntity<?> logout(String authToken) {
		System.out.println("logout implementation called");
		Session session=sessionFactory.openSession();
		if(commonMethods.checkAuthToken(authToken)!=null){
			try{
				session.beginTransaction();
				
				/* Deletes all previous entries for the user from autable if Exist*/ 
				String hql = "delete FROM authtable where authToken= :authToken" ;
				Query query = session.createQuery(hql);
				query.setString("authToken", authToken);
				query.executeUpdate();
			}
			catch(Exception e){
				session.getTransaction().rollback();
				
			}finally{
				Map<String,String> responseMap = new HashMap<String,String>();
				responseMap.put("message",env.getProperty("message.success"));
				return ResponseEntity.ok(responseMap);
			}
		}
		else{
			Map<String,String> responseMap = new HashMap<String,String>();
			responseMap.put("message",env.getProperty("message.invalidauthtoken"));
			return new ResponseEntity<Map>(responseMap,HttpStatus.UNAUTHORIZED);
		}
	}

}
