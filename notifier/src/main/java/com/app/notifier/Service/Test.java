package com.app.notifier.Service;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.app.notifier.Model.User;

public class Test {
	
	
	public static void main(String[] args) {
		
		Configuration con = new Configuration().configure().addAnnotatedClass(User.class);
        
        SessionFactory sf = con.buildSessionFactory();
        
        Session session = sf.openSession();
        
        
        //session.beginTransaction();
        
        User user = new User("bam","ram123","ram@gmail.com","944567898");
        
        session.save(user);
        
        //session.getTransaction().commit();
        
		/*Session session = HibernateUtil.getSessionFactory().openSession();
 
        session.beginTransaction();
        User user = new User("shreeraam","ram123","ram@gmail.com","945678906");
 
       
 
        session.save(user);
        session.getTransaction().commit();*/
 
    }

}
