package net.cr.beans;

/**
 * 			BookBean.java : This bean contains getters and setters of the book which can handle single row from the database.
 * @author nagr0616
 *
 */
public class BookBean {
	private String bookId;
	private String isbn;
	private String authors;
	private String title;
	private String publisher;
	private String edition;
	private boolean valid;
	private int counts; 
	private String bookUser;
	private String issueDate;
	private String dueDate;
	
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

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getBookUser() {
		return bookUser;
	}

	public void setBookUser(String bookUser) {
		this.bookUser = bookUser;
	}
	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}
	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getTitle(){
		return title;
	}
	
	public void setTitle(String tit){
		title = tit;
	}
	public boolean isValid(){
		return valid;
	}
	public void setValid(boolean valid){
		this.valid = valid;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
}
