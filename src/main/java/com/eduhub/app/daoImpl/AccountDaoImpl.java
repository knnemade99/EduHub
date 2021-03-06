package com.eduhub.app.daoImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
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
import com.eduhub.app.util.EmailSender;
import com.eduhub.app.util.Encrypt;

@Repository("accountDao")
@PropertySource({ 
	  "classpath:messages.properties"
	})
public class AccountDaoImpl implements AccountDao {
	
	@Autowired
	public SessionFactory sessionFactory;
	@Autowired
	private Environment env;
	@Autowired
	EmailSender mailService;
	@Autowired
	CommonMethods commonMethods;

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
			if(!commonMethods.checkUsername(user.getUserCredential().getUsername())){
				/* check if email already exist or not */
				if(!commonMethods.checkEmail(user.getEmail())){
					user.getUserCredential().setPassword(Encrypt.encrypt(user.getUserCredential().getPassword()));
					session.save(user.getUserCredential());
					session.save(user);
					message=env.getProperty("message.success");
					status = HttpStatus.CREATED;
					
					/*sends Email*/
					String text = "Hi "+user.getName()+",\nWelcome to the Edudata";
					mailService.sendEmail(user.getEmail(), "Welcome to Edudata", text);
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

	/* forget password implementation */
	@Override
	public ResponseEntity<?> changePassword(String authToken, String oldPassword, String newPassword) {
		System.out.println("Change Password Implementation called");
		Session session=sessionFactory.openSession();
		String message="";
		HttpStatus status = HttpStatus.OK;
		
		/* check for valid authtoken */
		User user = commonMethods.checkAuthToken(authToken);
		if(user!=null){
			
			/* check if old Password belongs to same user or not */
			if(user.getUserCredential().getPassword().equals(Encrypt.encrypt(oldPassword))){
				try{	
					session.beginTransaction();
					
					/* changing the password */
					UserCredential usercredential=session.get(UserCredential.class, user.getUserCredential().getUsername());
					usercredential.setPassword(Encrypt.encrypt(newPassword));
					
					/* storing the new password */
					session.update(usercredential);
					
					message=env.getProperty("message.success");
					status = HttpStatus.OK;
					
					/*sends Email*/
					String text = "Hi "+user.getName()+",\nYour password for EduData changed successfully";
					mailService.sendEmail(user.getEmail(), "Password Cahnged", text);
				}
				catch(Exception e){
					session.getTransaction().rollback();
					message=env.getProperty(e.toString());
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
			else{
				message=env.getProperty("message.password.incorrect");
				status = HttpStatus.BAD_REQUEST;
				Map<String,String> responseMap = new HashMap<String,String>();
				responseMap.put("message",message);
				return new ResponseEntity<Map>(responseMap,status);
			}
		}
		else{
			message=env.getProperty("message.invalidauthtoken");
			status = HttpStatus.UNAUTHORIZED;
			Map<String,String> responseMap = new HashMap<String,String>();
			responseMap.put("message",message);
			return new ResponseEntity<Map>(responseMap,status);
		}
	}
	
	/* forget password implementation */
	@Override
	public ResponseEntity<?> forgetPassword(String email) {
		System.out.println("Forget Password Implementation called");
		Session session=sessionFactory.openSession();
		String message="";
		HttpStatus status = HttpStatus.OK;
		
		/* check if email registered or not */
		if(commonMethods.checkEmail(email)){
			try{	
				session.beginTransaction();
				
				/* get User for Email */
				String fetchUserHQL = "FROM user where email='"+email+"'";
				Query fetchUserQuery = session.createQuery(fetchUserHQL);
				List<User> userResult = fetchUserQuery.list();
				
				User user = userResult.get(0);
				
				/* generates new password */
				String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@_$!#";
				String pwd = RandomStringUtils.random( 15, characters );
				
				/* fetching user credential */
				UserCredential usercredential=session.get(UserCredential.class, user.getUserCredential().getUsername());
				usercredential.setPassword(Encrypt.encrypt(pwd));
				
				/* storing the new password */
				session.update(usercredential);
				
				message=env.getProperty("message.success");
				status = HttpStatus.OK;
				
				/*sends Email*/
				String text = "Hi "+user.getName()+",\nYour new password is " + pwd;
				mailService.sendEmail(user.getEmail(), "Password Reset", text);
			}
			catch(Exception e){
				session.getTransaction().rollback();
				message=env.getProperty(e.toString());
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
		else{
			Map<String,String> responseMap = new HashMap<String,String>();
			responseMap.put("message",env.getProperty("message.email.notExist"));
			return new ResponseEntity<Map>(responseMap,HttpStatus.NOT_FOUND);
		}
	}
	
}
