package com.eduhub.app.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;

@Repository("commonMethods")
@PropertySource({ 
	  "classpath:messages.properties"
	})
public class CommonMethods {
	@Autowired
	public SessionFactory sessionFactory;
	
	/* check for valid authToken returns user if present for authtoken else returns null */
	public User checkAuthToken(String authToken){
		Session session=sessionFactory.openSession();
		User user = null;
		boolean validAuth = false;
		try{
			session.beginTransaction();
			AuthTable authTable=session.get(AuthTable.class, authToken);
			if(authTable!=null){
				user = authTable.getUser();
			}
		}
		catch(Exception e){
			session.getTransaction().rollback();
			user = null;
		}
		finally{
			return user;
		}
	}

	/* Method to check for existing username */
	public boolean checkUsername(String username){
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
	public boolean checkEmail(String email){
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
