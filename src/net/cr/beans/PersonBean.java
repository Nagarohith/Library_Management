package net.cr.beans;

/**
 * 
 * 		PersonBean.java : This bean handles the person data might be user, librarian for insertion, deletion.
 * @author nagr0616
 *
 */
public class PersonBean {	
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String phone;
	private String email;
	private boolean status;
	private String role;
	private boolean duplicate;
	
	public void setDuplicate(boolean duplicate) {
		this.duplicate = duplicate;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

	public String getRole(){
		return role;
	}
	
	public void setRole(String r){
		role = r;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
