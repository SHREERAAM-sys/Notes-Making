package com.app.notifier.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.app.notifier.Model.NoteBook;
import com.app.notifier.Model.User;
import com.app.notifier.Model.Note;
import com.app.notifier.Service.NoteBookService;
import com.app.notifier.Service.NoteService;


/**
 * Servlet implementation class NoteBookControllerServlet
 */
@WebServlet("/NoteBookControllerServlet")
public class NoteBookControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private NoteBookService noteBookService;
	
	private NoteService noteService;
	
	private NoteControllerServlet noteControllerServlet;
	
    public NoteBookControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	@Override
	public void init() throws ServletException {
		
		super.init();
		
		try {
			noteControllerServlet = new NoteControllerServlet();
			noteBookService = new NoteBookService();
			noteService = new NoteService();
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String theCommand = request.getParameter("command");
			
			if(theCommand == null) theCommand="LIST";
			switch(theCommand)
			{
				case "LIST":
					noteBookList(request,response);
					break;
				case "DELETE":
					deleteNoteBook(request,response);
					break;
				case "VIEW":
					viewNoteBook(request,response);
					break;
			}
			
		}
	catch (Exception e) {
		throw new ServletException(e);
	}

		
	}

	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
				String theCommand = request.getParameter("command");
				
				if(theCommand == null) theCommand="LIST";
				switch(theCommand)
				{
					case "ADD":
						addNoteBook(request,response);
						break;
					case "UPDATENOTEBOOK":
						updateNoteBook(request,response);
						break;
					case "LIST":
						noteBookList(request,response);
						break;
					case "DELETE":
						deleteNoteBook(request,response);
						break;
				}
				
			}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}


	

	private void deleteNoteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		NoteBook delNoteBook = noteBookService.getNoteBookObject(id);
		noteBookService.deleteNoteBook(delNoteBook);
		noteBookList(request,response);
	}

	//setting the current note book object to the session
	public void viewNoteBook(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		NoteBook noteBook = new NoteBook();
		noteBook = noteBookService.getObjectId(id);
		
		HttpSession session = request.getSession();
		session.setAttribute("noteBook", noteBook);
		noteControllerServlet.listOfNotes(request,response);
	}

	//getting the object to list all the notes
	private void noteBookList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<NoteBook> list = new ArrayList<NoteBook>();
		List<Note> list1 = new ArrayList<Note>();
		Map<NoteBook,Integer> contactMap = new HashMap<NoteBook, Integer>();
		
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("user");
		
		Integer  count=0;
		list = noteBookService.listOfNoteBook(currentUser);
		for(NoteBook temp:list) {
		     //count=noteService.getNumberOfNotes(temp);
		     contactMap.put(temp,count);
		}
		
		request.setAttribute("noteBooks", contactMap);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("notebooks.jsp");
		dispatcher.forward(request, response);
		
		
	}

	
	

	//Update the notebook name 
	private void updateNoteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer id =  Integer.parseInt(request.getParameter("notebookId"));
		
		String notebookname = request.getParameter("noteBookName");
		
		NoteBook noteBookUpdate= noteBookService.getObjectId(id);
		noteBookUpdate.setNoteBookName(notebookname);
		
		noteBookService.updateNotebook(noteBookUpdate);
		
		noteBookList(request,response);
		
	}

	//Adding a new note to the user
	private void addNoteBook(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String noteBookName = request.getParameter("noteBookName");
		
		HttpSession session = request.getSession();
		
		User currentUser = (User) session.getAttribute("user");
		//System.out.println(currentUser);
		NoteBook noteBook = new NoteBook(noteBookName,currentUser);
		
		noteBookService.createNoteBook(noteBook);
		
		noteBookList(request,response);
		
		
	}

}
