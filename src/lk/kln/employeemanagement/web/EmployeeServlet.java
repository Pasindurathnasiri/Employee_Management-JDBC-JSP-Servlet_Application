package lk.kln.employeemanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lk.kln.employeemanagement.dao.EmployeeDAO;
import lk.kln.employeemanagement.model.Employee;


@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
    	this.employeeDAO = new EmployeeDAO();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
		
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			try {
				insertEmployee(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/delete": 
			try {
				deleteEmployee(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateEmployee(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		default:
			try {
				listEmployee(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	 
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	private void listEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException,ServletException,IOException{
		List<Employee> listEmployee = employeeDAO.selectAllEmployees();
		request.setAttribute("listEmployee", listEmployee);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-list.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException,IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		dispatcher.forward(request,response);
			
	}
	
	private void insertEmployee (HttpServletRequest request,HttpServletResponse response) 
		throws SQLException,IOException{
		String name = request.getParameter("name");
		String nic = request.getParameter("nic");
		String address = request.getParameter("address");
		
		Employee newEmployee = new Employee(name,nic,address);
		employeeDAO.insertEmployee(newEmployee);
		response.sendRedirect("list");
	}
	
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException,IOException{
		int Id = Integer.parseInt(request.getParameter("Id"));
		employeeDAO.deleteEmployee(Id);
		response.sendRedirect("list");
	}
	
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException,ServletException,IOException{
		int Id = Integer.parseInt(request.getParameter("Id"));
		Employee existingEmployee = employeeDAO.selectEmployee(Id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employee-form.jsp");
		request.setAttribute("employee", existingEmployee);
		dispatcher.forward(request, response);
	}
	
	private void updateEmployee (HttpServletRequest request,HttpServletResponse response) 
			throws SQLException,IOException{
		    int Id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			String nic = request.getParameter("nic");
			String address = request.getParameter("address");
			
			Employee employee = new Employee(Id,name,nic,address);
			employeeDAO.updateEmployee(employee);
			response.sendRedirect("list");
	}

}
