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
import com.eduhub.app.dao.CommonDao;
import com.eduhub.app.entity.Category;
import com.eduhub.app.entity.Standard;
import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.util.EmailSender;
import com.eduhub.app.util.Encrypt;

@Repository("commonDao")
@PropertySource({ 
	  "classpath:messages.properties"
	})
public class CommonDaoImpl implements CommonDao {
	
	@Autowired
	public SessionFactory sessionFactory;
	@Autowired
	private Environment env;
	@Autowired
	EmailSender mailService;
	@Autowired
	CommonMethods commonMethods;
	
	/* get Subjects functionality implementation */
	@Override
	public ResponseEntity<?> getCategory(String authToken) {
		System.out.println("get subject implementation called");
		Session session=sessionFactory.openSession();
		String message="";
		HttpStatus status = HttpStatus.OK;
		List<Category> categories = null ;
		
		/* check for valid authtoken */
		User user = commonMethods.checkAuthToken(authToken);
		if(user!=null){
			
			try{	
				session.beginTransaction();
				
				/* get all subjects */
				categories = session.createCriteria(Category.class).list();
			}
			catch(Exception e){
				session.getTransaction().rollback();
				message=env.getProperty(e.toString());
				status = HttpStatus.BAD_REQUEST;
			}
			finally{
				session.getTransaction().commit();
				session.close();
				
				Map<String,Object> responseMap = new HashMap<String,Object>();
				responseMap.put("message",env.getProperty("message.success"));
				responseMap.put("data", categories);
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
	
	/* get Standards functionality implementation */
	@Override
	public ResponseEntity<?> getStandard(String authToken) {
		System.out.println("get standard implementation called");
		Session session=sessionFactory.openSession();
		String message="";
		HttpStatus status = HttpStatus.OK;
		List<Category> categories = null ;
		
		/* check for valid authtoken */
		User user = commonMethods.checkAuthToken(authToken);
		if(user!=null){
			
			try{	
				session.beginTransaction();
				
				/* get all subjects */
				categories = session.createCriteria(Standard.class).list();
			}
			catch(Exception e){
				session.getTransaction().rollback();
				message=env.getProperty(e.toString());
				status = HttpStatus.BAD_REQUEST;
			}
			finally{
				session.getTransaction().commit();
				session.close();
				
				Map<String,Object> responseMap = new HashMap<String,Object>();
				responseMap.put("message",env.getProperty("message.success"));
				responseMap.put("data", categories);
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

}
