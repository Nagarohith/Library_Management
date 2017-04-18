package net.cr.beans;

/**
 * 			LibraryBean.java : This bean contains the getters and setters to handle Library transactions like issue book, return book etc
 * @author nagr0616
 *
 */
public class LibraryBean {
	private String bookId;
	private String username;
	private String issueDate;
	private String dueDate;
	private String returnDate;
	private boolean status;
	private boolean available;
	private boolean userInvalid;
	private boolean removeBook;
	
	public boolean isRemoveBook() {
		return removeBook;
	}
	public boolean isUserInvalid() {
		return userInvalid;
	}
	public void setUserInvalid(boolean userInvalid) {
		this.userInvalid = userInvalid;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setUserInValid(boolean userInvalid) {
		this.userInvalid = userInvalid;
	}
	public void setRemoveBook(boolean removeBook) {
		this.removeBook = removeBook;
		
	}
}
