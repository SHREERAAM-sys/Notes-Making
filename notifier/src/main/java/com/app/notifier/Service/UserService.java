package com.app.notifier.Service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.app.notifier.Model.User;

public class UserService {

	Criteria criteria = null;
	
	Transaction transaction=null;
	public UserService()
	{
		super();
	}
	
	
	//creating a new user with the object from the addUser function in UserControllerServlet 
	public void createUser(User user) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
		//session = HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		
		session.save(user);                      //saving user Object
		
		transaction.commit();
		}
		catch(Exception e)
		{
			if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
		}
		
        
		
	}

	public User loginUser(String email, String password) {
		
		User user=null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
		//session = HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		
		criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("password", password));
		
        user = (User) criteria.uniqueResult();
		
		transaction.commit();
		
		}
		catch(Exception e)
		{

			if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
		}

		
        return user;
		
        
	}

	//Updating user object obtained from updateuser function in  UserControllerServlet
	public void updateUser(User user) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession();) {
			//session = HibernateUtil.getSessionFactory().openSession();
			transaction=session.beginTransaction();
			
			session.update(user);              //Updating user Object
			
			transaction.commit();
			
			}
			catch(Exception e)
			{
				if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
			}
		
	}

}
