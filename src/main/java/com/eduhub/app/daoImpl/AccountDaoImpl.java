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
	
}
