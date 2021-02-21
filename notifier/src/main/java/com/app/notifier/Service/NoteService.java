package com.app.notifier.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import com.app.notifier.Model.*;





public class NoteService {
	
	Criteria criteria = null;
	Transaction transaction = null;
	
	
	public Integer getNumberOfNotes(NoteBook noteBook) {
		
		Integer count=0;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Note.class);
			criteria.add( Restrictions.eq("noteBook",noteBook));
			criteria.setProjection(Projections.rowCount());
			count = (Integer)criteria.uniqueResult();
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		
		return count;
	}


	public void createNote(Note noteObj) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			Status status = noteObj.getStatus();
			Tag tag = noteObj.getTag();
			session.save(status);
			session.save(tag);
			session.save(noteObj);
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		
	}
	
	
	public Note getNoteObject(Integer id) {
		

		Note note = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Note.class);
			criteria.add(Restrictions.eq("id", id));
			note = (Note) criteria.uniqueResult();
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		
		return note;
	}
	

	
	public void updateNote(Note noteObj) {
		
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			Status status = noteObj.getStatus();
			Tag tag = noteObj.getTag();
			session.save(status);
			session.save(tag);
			session.update(noteObj);
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

	
	public ArrayList<Note> listOfNotes(Integer id) {
		
		List<Note> notes= null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Note.class);
			criteria.add(Restrictions.eq("noteBook.id", id));
			notes = criteria.list();
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		return (ArrayList<Note>) notes;
	}


	public Note getDates(Note note, Date date) {
		
		Note notes = null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Note.class);
			criteria.add(Restrictions.eq("id", note.getId()));
			criteria.add(Restrictions.ge("endDate",date));
			notes = (Note)criteria.uniqueResult();
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		
		return notes;		
	}


	public ArrayList<Note> listAllNotes(User user) {
		// TODO Auto-generated method stub
		List<Note> notes= null;
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(Note.class,"note");
			criteria.createAlias("noteBook","noteBook");
			criteria.add(Restrictions.eq("noteBook.user", user));
			List<Note> list = criteria.list();
			notes = (ArrayList<Note>) list;
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
		return  (ArrayList<Note>)notes;
		
	}


	public void deleteNote(Note note) {
		// TODO Auto-generated method stub
		try(Session session = HibernateUtil.getSessionFactory().openSession())
		{
			transaction = session.beginTransaction();
			session.delete(note);
			transaction.commit();
		}
		catch(Exception e)
		{
			if(transaction != null) transaction.rollback();
			e.printStackTrace();
		}
	}

}
