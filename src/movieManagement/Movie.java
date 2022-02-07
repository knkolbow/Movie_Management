package movieManagement;

import java.util.Date;


public class Movie {
	//Data Fields
	private Date releaseDate;
	private String name;
	private String description;
	private Date receiveDate;
	public enum Status { RECEIVED, RELEASED }
	private Status movieStatus;

	 // Constructors
	public Movie() {}	

	public Movie(Date released, String movieName, String movieDescription, Date received, Status status) {
		releaseDate = released;
		name = movieName;
		description = movieDescription;
		receiveDate = received;
		movieStatus = status;
	}
	
	//Getters
	
	public Date getReleaseDate() { return releaseDate; }
	public String getMovieName() { return name; }	
	public String getDescription() { return description; }	
	public Date getReceiveDate() { return receiveDate; }	
	public Status getStatus() { return movieStatus; }
	
	//Setters
	
	public void setReleaseDate(Date released) { releaseDate = released; }
	public void setMovieName(String movieName) { name = movieName; }	
	public void setDescription(String movieDescription) { description = movieDescription; }	
	public void setReceiveDate(Date received) { receiveDate = received; }	
	public void setStatus(Status status) { movieStatus = status; }
	
	// Methods
	
	@SuppressWarnings("deprecation")
	public String outputMovie() {
		return getStatus().toString() + " " + releaseDate.getMonth() + "/" + releaseDate.getDate() + "/" + releaseDate.getYear() +
				" " + getDescription() + " " + receiveDate.getMonth() + "/" + receiveDate.getDate() + "/" + receiveDate.getYear() + " " + getMovieName();
	}
}
