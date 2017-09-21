package com.eduhub.app.directcreatedatabase;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import com.eduhub.app.entity.Address;
import com.eduhub.app.entity.Category;
import com.eduhub.app.entity.Role;
import com.eduhub.app.entity.Standard;
import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.util.Encrypt;



public class Main {
	
	public static void generateStandard(Session session){
		Standard standard1 = new Standard("1");
		Standard standard2 = new Standard("2");
		Standard standard3 = new Standard("3");
		Standard standard4 = new Standard("4");
		Standard standard5 = new Standard("5");
		Standard standard6 = new Standard("6");
		Standard standard7 = new Standard("7");
		Standard standard8 = new Standard("8");
		Standard standard9 = new Standard("9");
		Standard standard10 = new Standard("10");
		Standard standard11 = new Standard("11");
		Standard standard12 = new Standard("12");
		session.save(standard1);
		session.save(standard2);
		session.save(standard3);
		session.save(standard4);
		session.save(standard5);
		session.save(standard6);
		session.save(standard7);
		session.save(standard8);
		session.save(standard9);
		session.save(standard10);
		session.save(standard11);
		session.save(standard12);	
	}
	
	public static void generateCategory(Session session){
		Category category1 = new Category("Maths");
		Category category2 = new Category("Science");
		Category category3 = new Category("English");
		Category category4 = new Category("General Knowledge");
		session.save(category1);
		session.save(category2);
		session.save(category3);
		session.save(category4);
	}
	
	public static void generateRole(Session session){
		Role role1 = new Role("admin");
		Role role2 = new Role("student");
		session.save(role1);
		session.save(role2);
	}
	
	public static void generateUser(Session session){
		User user = new User();
		UserCredential userCredential = new UserCredential();
		Address address = new Address();
		Role role = session.get(Role.class,1);
		Standard standard = session.get(Standard.class, 12);
		
		userCredential.setUsername("admin");
		userCredential.setPassword(Encrypt.encrypt("1234"));
		
		address.setCity("Indore");
		address.setCountry("India");
		address.setState("M.P.");
		address.setZip(452010);
		
		user.setAddress(address);
		user.setUserCredential(userCredential);
		user.setName("Kunal Nemade");
		user.setContact("7697599287");
		user.setDob(new Date());
		user.setEmail("knnemade99@gmail.com");
		user.setGender("Male");
		user.setRole(role);
		user.setStandard(standard);
		
		session.persist(user);		
	}
	
	public static void main(String args[]) {
		
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
		Session session=sessionFactory.openSession();
		
		session.beginTransaction();
		
		try{	
			generateStandard(session);
			generateCategory(session);
			generateRole(session);
			generateUser(session);
		}
		catch(Exception e){
			session.getTransaction().rollback();
		}
		finally{
			session.getTransaction().commit();
			session.close();
			sessionFactory.close();
		}
	}
}
