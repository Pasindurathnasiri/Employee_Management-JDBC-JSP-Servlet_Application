package lk.kln.employeemanagement.dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.kln.employeemanagement.model.Employee;

public class EmployeeDAO {

	private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";
	
	private static final String INSERT_EMPLOYEES_SQL = "INSERT INTO employees"+"(name,nic,address) VALUES"+"(?,?,?);";
	
	private static final String SELECT_EMPLOYEE_BY_ID = "SELECT Id,name,nic,address FROM employees WHERE Id=?";
	
	private static final String SELECT_ALL_EMPLOYEES = "SELECT * FROM employees";
	
	private static final String DELETE_EMPLOYEE_SQL = "DELETE FROM employees WHERE Id=?;";
	
	private static final String UPDATE_EMPLOYEE_SQL = "UPDATE employees SET name = ?,nic=?,address=? WHERE Id=?;";
	
	
	protected Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return connection;
		
	}
	
	//insert employee method
	public void insertEmployee(Employee employee) throws SQLException {
		try(Connection connection= getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(INSERT_EMPLOYEES_SQL)){
			preparedstatement.setString(1,employee.getName());
			preparedstatement.setString(2,employee.getNic());
			preparedstatement.setString(3,employee.getAddress());
			preparedstatement.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//update employee method
	public boolean updateEmployee(Employee employee) throws SQLException{
		boolean rowUpdated;
		try(Connection connection= getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(UPDATE_EMPLOYEE_SQL)){
			preparedstatement.setString(1,employee.getName());
			preparedstatement.setString(2,employee.getNic());
			preparedstatement.setString(3,employee.getAddress());
			preparedstatement.setInt(4, employee.getId());
			rowUpdated = preparedstatement.executeUpdate() > 0;
		} 
		return rowUpdated;
	}
	
	//select employee by id
	public Employee selectEmployee(int Id) {
		Employee employee = null;
		
		try(Connection connection= getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID);){
			preparedstatement.setInt(1, Id);
			System.out.println(preparedstatement);
			
			ResultSet rs = preparedstatement.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("name");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				employee = new Employee(Id,name,nic,address);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employee;
	}
	
	//select all employees
	public List<Employee> selectAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		
		try(Connection connection= getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(SELECT_ALL_EMPLOYEES);){
			
			System.out.println(preparedstatement);
			
			ResultSet rs = preparedstatement.executeQuery();
			
			while(rs.next()) {
				int Id = rs.getInt("Id");
				String name = rs.getString("name");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				employees.add(new Employee(Id,name,nic,address));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return employees;
	}
	
	//delete employee
	public boolean deleteEmployee(int Id) throws SQLException{
		boolean rowDeleted;
		try(Connection connection= getConnection();
				PreparedStatement preparedstatement = connection.prepareStatement(DELETE_EMPLOYEE_SQL);){
			preparedstatement.setInt(1,Id);
			rowDeleted = preparedstatement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
}
