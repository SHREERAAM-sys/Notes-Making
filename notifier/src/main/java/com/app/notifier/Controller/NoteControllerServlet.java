package com.app.notifier.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import com.app.notifier.Model.*;
import com.app.notifier.Service.NoteBookService;
import com.app.notifier.Service.NoteService;
import com.spring.DomainClass.Note;



/**
 * Servlet implementation class NoteControllerServlet
 */
@WebServlet("/NoteControllerServlet")
public class NoteControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private NoteBookService noteBookService;
	private NoteService noteService;
	   
	ArrayList<Note> list1 = new ArrayList<Note>();
	long millis = System.currentTimeMillis();
	java.sql.Date date = new java.sql.Date(millis);
	Note noteObjDate=new Note();
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		try {
		
			noteBookService = new NoteBookService();
			noteService = new NoteService();
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		
	}
	
    public NoteControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			String theCommand = request.getParameter("command");
			
			if(theCommand == null) theCommand = "LIST";
			
			switch(theCommand)
			{
				
				case "LIST":
					listOfNotes(request,response);
					break;
				case "LISTALL":
					listAllNotes(request,response);
					break;
				case "UPDATENOTE":
					updateNote(request,response);
					break;
				case "DELETENOTE":
					deleteNoteFromNotes(request,response);
					
			}
			
			
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try
		{
			String theCommand = request.getParameter("command");
			
			if(theCommand == null) theCommand = "LIST";
			
			switch(theCommand)
			{
				case "ADD":
					addNotes(request,response);
				case "LIST":
					listOfNotes(request,response);
					break;
				case "LISTALL":
					listAllNotes(request,response);
					break;
				case "UPDATENOTE":
					updateNote(request,response);
					break;
					
			}
			
			
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
	}

	private void deleteNoteFromNotes(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Integer id = Integer.parseInt(request.getParameter("id"));
		Note note = new Note();
		note = noteService.getNoteObject(id);
		noteService.deleteNote(note);
	}
	private void listAllNotes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		
		User currentUser = (User) session.getAttribute("user");
		
		ArrayList<Note> list = new ArrayList<Note>();
		list = noteService.listAllNotes(currentUser);
		Note noteObj=new Note();
		ArrayList<Note> l1=new ArrayList<Note>();
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		/*Integer countRemainder= 0;
		for(Note note:list)
		{
			noteObj=noteService.getRemainderOfAllNotes(note,date);
			if(noteObj!=null)
				 countRemainder++;
			noteObjDate=noteService.getDates(note,date);
			if(noteObjDate!=null) {
				    l1.add(noteObjDate);
			}
	    }
		model.addAttribute("noteObjDate",l1);
		model.addAttribute("countRemainder",countRemainder);
		HttpSession session = request.getSession();
		session.removeAttribute("noteBook");*/
		request.setAttribute("note", list);
		//return new ModelAndView("notes", "note", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WelcomeNotes.jsp");
		dispatcher.forward(request, response);
	}

	private void updateNote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String noteName = request.getParameter("noteName");
		String noteDescription = request.getParameter("noteDescription");
		String statusName = request.getParameter("statusName");
		String tagName = request.getParameter("tagName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String remainderDate = request.getParameter("remainderDate");
		
		Integer noteid = Integer.parseInt(request.getParameter("noteId"));
		Integer noteBookId = Integer.parseInt(request.getParameter("noteBookId"));
		NoteBook noteBook = new NoteBook();
		noteBook = noteBookService.getNoteBookObject(noteBookId);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date start_Date = sdf.parse(startDate);
		java.sql.Date start_date = new java.sql.Date(start_Date.getTime());

		java.util.Date end_Date = sdf.parse(endDate);
		java.sql.Date end_date = new java.sql.Date(end_Date.getTime());

		java.util.Date remainder_Date = sdf.parse(remainderDate);
		java.sql.Date remainder_date = new java.sql.Date(remainder_Date.getTime());

		Note noteObj = new Note();
		noteObj = noteService.getNoteObject(noteid);

		Status status = new Status(noteObj.getStatus().getId(), statusName);
		Tag tag = new Tag(noteObj.getTag().getId(), tagName);
		Note note = new Note(noteid, noteName, noteDescription, start_date, end_date, remainder_date, status, tag,
				noteBook);
		noteService.updateNote(note);
		
		HttpSession session = request.getSession();
		noteBook = (NoteBook) session.getAttribute("noteBook");
		if(noteBook==null)
			listAllNotes(request,response);
			
		else
		{

			Integer notebookId = noteBook.getId();
			request.setAttribute("id",notebookId);
			request.setAttribute("addnotesId", true);
			listOfNotes(request, response);
		}

		
	}

	public void listOfNotes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer id;
		if((Boolean)request.getAttribute("addnotesId")==true)
		{
			id = (Integer) request.getAttribute("id");
			
			System.out.println("addnotesId="+id+"\n");
		}
		else
		{
			id = Integer.parseInt(request.getParameter("id"));
			System.out.println("listnotesId="+id+"\n");
		}
		NoteBook noteBook = new NoteBook();
		noteBook = noteBookService.getObjectId(id);
		
		HttpSession session = request.getSession();
		session.setAttribute("noteBook", noteBook);
		ArrayList<Note> list = new ArrayList<Note>();
		list = (ArrayList<Note>) noteService.listOfNotes(id);
		list1.clear();
		for(Note note:list) {
			noteObjDate=noteService.getDates(note,date);
			if(noteObjDate!=null) {
				list1.add(noteObjDate);
			}
		}
		request.setAttribute("noteObjDate",list1);
		/*Integer countRemainder= noteService.getRemainderCount(noteBook, date);
		request.setAttribute("countRemainder",countRemainder);*/
		request.setAttribute("note", list);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("notes.jsp");
		dispatcher.forward(request, response);
	}

	private void addNotes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String noteName = request.getParameter("noteName");
		String noteDescription = request.getParameter("noteDescription");
		String statusName = request.getParameter("statusName");
		String tagName = request.getParameter("tagName");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String remainderDate = request.getParameter("remainderDate");
		
		Status status = new Status(statusName);
		Tag tag = new Tag(tagName);
		NoteBook noteBook = new NoteBook();
		HttpSession session = request.getSession();
		noteBook = (NoteBook) session.getAttribute("noteBook");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date start_Date = sdf.parse(startDate);
		java.sql.Date start_date = new java.sql.Date(start_Date.getTime());

		java.util.Date end_Date = sdf.parse(endDate);
		java.sql.Date end_date = new java.sql.Date(end_Date.getTime());

		java.util.Date remainder_Date = sdf.parse(remainderDate);
		java.sql.Date remainder_date = new java.sql.Date(remainder_Date.getTime());

		Note note = new Note(noteName, noteDescription, start_date, end_date, remainder_date, status, tag, noteBook);
		noteService.createNote(note);
		
		Integer noteBookId = noteBook.getId();
		request.setAttribute("id",noteBookId);
		request.setAttribute("addnotesId", true);
		listOfNotes(request, response);
		//return ("redirect:listOfNotes.do?itemId="+noteBook.getId());

		
	}

}
