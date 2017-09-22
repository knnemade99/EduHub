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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.eduhub.app.dao.AccountDao;
import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;

@Repository("accountDao")
@PropertySource({ 
	  "classpath:messages.properties"
	})
public class AccountDaoImpl implements AccountDao {
	
	@Autowired
	public SessionFactory sessionFactory;
	@Autowired
	private Environment env;

	/* create account functionality implementation */
	@Override
	public ResponseEntity<?> createAccount(User user) {
		System.out.println("create account implementation called");
		Session session=sessionFactory.openSession();
		String message="";
		HttpStatus status = HttpStatus.OK;
		try{
			session.beginTransaction();
			System.out.println(user);
			
			/* check if username already exist or not */
			if(!checkUsername(user.getUserCredential().getUsername())){
				/* check if email already exist or not */
				if(!checkEmail(user.getEmail())){
					session.save(user.getUserCredential());
					session.save(user);
					message=env.getProperty("message.success");
					status = HttpStatus.CREATED;
				}
				else{
					message=env.getProperty("message.emailExist");
					status = HttpStatus.BAD_REQUEST;
				}
			}
			else{
				message=env.getProperty("message.usernameExist");
				status = HttpStatus.UNAUTHORIZED;
				status = HttpStatus.BAD_REQUEST;
			}
		}
		catch(Exception e){
			session.getTransaction().rollback();
			status = HttpStatus.BAD_REQUEST;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			
			Map<String,String> responseMap = new HashMap<String,String>();
			responseMap.put("message",message);
			return new ResponseEntity<Map>(responseMap,status);
		}
	}
	
	/* Method to check for existing username */
	private boolean checkUsername(String username){
		Session session=sessionFactory.openSession();
		boolean usernameExist = false;
		try{
			session.beginTransaction();
			
			/*fetching usercredential*/
			String fetchCredentialHQL = "FROM usercredential where username='"+username+"'";
			Query fetchCredentialQuery = session.createQuery(fetchCredentialHQL);
			List<UserCredential> usercredentialResult = fetchCredentialQuery.list();
			if(usercredentialResult.size()>0)
				usernameExist = true;
	
		}
		catch(Exception e){
			session.getTransaction().rollback();
			usernameExist =false;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return usernameExist;
		}
	}
	
	/* Method to check for existing email */
	private boolean checkEmail(String email){
		Session session=sessionFactory.openSession();
		boolean emailExist = false;
		try{
			session.beginTransaction();
			
			/*fetching usercredential*/
			String fetchCredentialHQL = "FROM user where email='"+email+"'";
			Query fetchCredentialQuery = session.createQuery(fetchCredentialHQL);
			List<UserCredential> usercredentialResult = fetchCredentialQuery.list();
			if(usercredentialResult.size()>0)
				emailExist = true;
	
		}
		catch(Exception e){
			session.getTransaction().rollback();
			emailExist =false;
		}
		finally{
			session.getTransaction().commit();
			session.close();
			return emailExist;
		}
	}

}
