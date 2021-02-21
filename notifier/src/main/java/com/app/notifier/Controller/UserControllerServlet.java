package com.app.notifier.Controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.notifier.Model.User;
import com.app.notifier.Service.UserService;

/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	private UserService userService;
	
	
    public UserControllerServlet()  {
        super();
        // TODO Auto-generated constructor stub
        
    }
    
    
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try
        {
        	userService = new UserService();
        }
        catch(Exception e)
        {
        	throw new ServletException(e);
        }
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String theCommand = request.getParameter("command");
		
		if(theCommand == null) theCommand = "INDEX";
		
		try {
			switch(theCommand)
			{
				case "INDEX":
					goToIndexPage(request,response);
					break;
				case "ADD":
					addUser(request,response);
					break;
				case "LOGIN":
					loginUser(request,response);
					break;
				case "UPDATEUSER":
					updateUser(request,response);
					break;
			}
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
	}
	
	//updating the user got from the Edit User modal from side navigationbar.
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		// TODO Auto-generated method stub
		
		int id=Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String mobilenumber = request.getParameter("mobileNumber");
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		String editCommand = request.getParameter("editCommand");
		
		User userUpdatedObj = new User(id, username, password, email, mobilenumber);
		userService.updateUser(userUpdatedObj);
		
		HttpSession session= request.getSession();
		session.setAttribute("user", userUpdatedObj);
		
		try {
		switch(editCommand)
		{
			case "WelcomeNotes":
			
				response.sendRedirect("WelcomeNotes.jsp");
				break;
			case "notebooks":
				response.sendRedirect("WelcomeNotes.jsp");
				
		}
		}
		catch(Exception e)
		{
			throw new ServletException(e);
		}
		
		/*response.sendRedirect("Welcomenotes.jsp");*/
		
		
	}


	//Getting user credentials for login from index.jsp and verifying using Userservice.java
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email=request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = userService.loginUser(email,password);
		
		RequestDispatcher dispatcher;
		if(user==null)
		{
			request.setAttribute("error", "Email not found");
			request.setAttribute("message", "please enter an valid email");
			
			dispatcher= request.getRequestDispatcher("index.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			HttpSession session= request.getSession();
			session.setAttribute("user", user);
			dispatcher = request.getRequestDispatcher("WelcomeNotes.jsp");
			dispatcher.forward(request, response);
			
		}
		
	}


	//Adding a new user by sending the data to "UserService.java"
	private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("userName");
		String mobilenumber = request.getParameter("mobileNumber");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		User user = new User(username, password, email, mobilenumber);
		
		userService.createUser(user);
		
		goToIndexPage(request, response);
		
	}

	//if the command in null then go to the index.jsp page
	private void goToIndexPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		response.sendRedirect("index.jsp");
	}

}
