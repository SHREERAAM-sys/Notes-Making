package com.app.notifier.Service;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.app.notifier.Model.Note;
import com.app.notifier.Model.NoteBook;
import com.app.notifier.Model.Status;
import com.app.notifier.Model.Tag;
import com.app.notifier.Model.User;

public class HibernateUtil {
	
	
	private static  SessionFactory sessionFactory = buildSessionFactory();
	
	
   
	private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	
        	Configuration configuration = new Configuration();
            configuration.configure()
            			 .addAnnotatedClass(User.class)
            			 .addAnnotatedClass(NoteBook.class)
            			 .addAnnotatedClass(Note.class)
            			 .addAnnotatedClass(Tag.class)
            			 .addAnnotatedClass(Status.class);
            			 
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            											.applySettings(configuration.getProperties())
            											.build();
            
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
 
    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}
