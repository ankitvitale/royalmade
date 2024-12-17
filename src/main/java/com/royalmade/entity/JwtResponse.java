package com.royalmade.entity;

public class JwtResponse {
//
//	private String email;
//	private String jwtToken;
//
//
//	public JwtResponse(String email, String jwtToken) {
//		this.email = email;
//		this.jwtToken = jwtToken;
//
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getJwtToken() {
//		return jwtToken;
//	}
//
//	public void setJwtToken(String jwtToken) {
//		this.jwtToken = jwtToken;
//	}
//
//
//}


	//package com.BusBookingbackend.entity;
//
//public class JwtResponse {
//
	private Employee employee;
	private AppUser appUser;
	private Supervisor supervisor;
	private  Admin admin;
	private String jwtToken;

	public JwtResponse(Employee employee, AppUser appUser, Supervisor supervisor, Admin admin, String jwtToken) {
		this.employee = employee;
		this.appUser = appUser;
		this.supervisor = supervisor;
		this.admin = admin;
		this.jwtToken = jwtToken;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}