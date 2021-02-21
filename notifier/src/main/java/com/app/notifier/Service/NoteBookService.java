package com.app.notifier.Service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.app.notifier.Model.NoteBook;
import com.app.notifier.Model.User;

public class NoteBookService {

	Criteria criteria=null;
	Transaction transaction=null;
	
	public NoteBookService()
	{
		
	}

	//creating a notebook from the noteBook Object from addNoteBook() method in NootBookController
	public void createNoteBook(NoteBook noteBook) {
		
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			
			session.save(noteBook);                             //Saving the noteBook Object
			
			transaction.commit();
			
		}
		catch(Exception e)
		{
			if(transaction!=null) transaction.rollback();
			e.printStackTrace();
		}
	}

	//Obtaining the note book object with id
	public NoteBook getObjectId(Integer id) {
		
		//System.out.println("in getObjectId");
		NoteBook notebook=null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction=session.beginTransaction();
			criteria = session.createCriteria(NoteBook.class);
			criteria.add(Restrictions.eq("id", id));
			
			notebook=(NoteBook) criteria.uniqueResult();
		
			transaction.commit();
			
		}
		catch(Exception e)
		{
			if(transaction!=null) transaction.rollback();
			e.printStackTrace();
		}
		
		return notebook;
	}
	
	//Obtaining the note book object with id
	public NoteBook getNoteBookObject(Integer id) {
		NoteBook notebook=null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction=session.beginTransaction();
			criteria = session.createCriteria(NoteBook.class);
			criteria.add(Restrictions.eq("id", id));
			
			notebook=(NoteBook) criteria.uniqueResult();
		
			transaction.commit();
			
		}
		catch(Exception e)
		{
			if(transaction!=null) transaction.rollback();
			e.printStackTrace();
		}
		
		return notebook;
	}
	
	//Updating the note book name with the object got from NoteBookControllerServlet 
	public void updateNotebook(NoteBook noteBookUpdate) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			
			session.update(noteBookUpdate);                            //Updating the noteBook Object
			
			transaction.commit();
		
		}
		catch(Exception e)
		{
			if(transaction!=null)  transaction.rollback();
			e.printStackTrace();
		}
	}
	
	//listing the notbook of particular user
	public List<NoteBook> listOfNoteBook(User user) {
		List<NoteBook> list=null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(NoteBook.class);
			criteria.addOrder(Order.asc("id"))
					.add(Restrictions.eq("user", user));
			list = criteria.list();
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction!=null)  transaction.rollback();
			e.printStackTrace();
		}
		
		return list;
	}
	
	public void deleteNoteBook(NoteBook noteBook) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			session.delete(noteBook);
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction!=null)  transaction.rollback();
			e.printStackTrace();
		}
	}

}
