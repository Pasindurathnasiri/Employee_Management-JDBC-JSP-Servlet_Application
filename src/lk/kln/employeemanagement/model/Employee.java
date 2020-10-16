package lk.kln.employeemanagement.model;

public class Employee {
	private int id;
	private String name;
	private String nic;
	private String address;
	
	public Employee(int id, String name, String nic, String address) {
		super();
		this.id = id;
		this.name = name;
		this.nic = nic;
		this.address = address;
	}
	
	 
	
	public Employee(String name, String nic, String address) {
		super();
		this.name = name;
		this.nic = nic;
		this.address = address;
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	

}
