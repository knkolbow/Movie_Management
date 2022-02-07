package movieManagement;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import movieManagement.Movie.Status;

public class MovieList {
	private LinkedList<Movie> showing;
	private LinkedList<Movie> coming;
	private Iterator<Movie> showingIt;
	private Iterator<Movie> comingIt;
	FileInputStream inputFile;

	//constructor

	public MovieList() {
		coming = new LinkedList<Movie>();
		showing = new LinkedList<Movie>();
	}
	
	// setter
	
	public void setInputFile(FileInputStream file) { inputFile = file; }

	// Methods

	/**
	 * Add's a movie to the correct list
	 * @param movieToAdd: the movie object to be added
	 */
	public void addMovie(Movie movieToAdd) {
		Date releaseDate, receiveDate;
		releaseDate = movieToAdd.getReleaseDate();
		receiveDate = movieToAdd.getReceiveDate();
		Status st = movieToAdd.getStatus();
		if (!this.coming.contains(movieToAdd) && !this.showing.contains(movieToAdd)) {
			if (!releaseDate.before(receiveDate)) {
				if(!releaseDate.equals(receiveDate)) {
					if (st == Status.RELEASED) {
						this.showing.add(movieToAdd);
					} else if (st == Status.RECEIVED) {
						this.coming.add(movieToAdd);
						sortList(coming);
					}
				}
			}
		}
	}

	/**
	 * Reads the input file and creates movie objects with data and then adds each movie to list based on its status
	 * @param scanner: the input file to be read
	 */
	public void loadMovies(Scanner scanner) {
		while (scanner.hasNext()) {
			Movie movie;
			String status = scanner.next().toLowerCase();
			String releaseDate = scanner.next();
			Date relDate = stringToDate(releaseDate);
			String description = scanner.next();
			String receiveDate = scanner.next();
			Date recDate = stringToDate(receiveDate);
			String movieName = scanner.nextLine().trim();

			if(status.equals("released")) {	movie = new Movie(relDate, movieName, description, recDate, Movie.Status.RELEASED); }
			else { movie = new Movie(relDate, movieName, description, recDate, Movie.Status.RECEIVED); }
			addMovie(movie);
			sortList(coming);
		}
	}
	
	/**
	 * Displays showing movies from showing linked list to console
	 */
	public void displayShowing() {
		showingIt = showing.iterator();
		System.out.println("Showing Movies");
		if (!showing.isEmpty()) {
			while(showingIt.hasNext()) {
				Movie temp = showingIt.next();
				System.out.println(temp.outputMovie());
			}
			System.out.println();
		}

	}

	/**
	 *Displays coming movies from coming linked list to console 
	 */
	public void displayComing() {
		comingIt = coming.iterator();
		System.out.println("Coming Movies");
		if (!coming.isEmpty()) {
			while(comingIt.hasNext()) {
				Movie temp = comingIt.next();
				System.out.println(temp.outputMovie());
			}
			System.out.println();
		}
	}

	/**
	 * Finds the size of the showing list
	 * @return int: size of showing list
	 */
	public int showingAmount() {
		return showing.size();
	}

	/**
	 * Finds the size of the coming list
	 * @return int: size of the coming list
	 */
	public int comingAmount() {
		return coming.size();
	}
	
	/**
	 * Overwrites the input file with any changes made to the lists
	 * @throws IOException: if file does not exist
	 */
	public void printList() throws IOException {
		FileWriter writer = new FileWriter("Movies.txt");
		comingIt = coming.iterator();
		showingIt = showing.iterator();
		if (!showing.isEmpty()) {
			while(showingIt.hasNext()) {
				Movie temp = showingIt.next();
				writer.write(temp.outputMovie());
				writer.write("\n");
			}
		}
		if (!coming.isEmpty()) {
			while (comingIt.hasNext()) {
				Movie temp = comingIt.next();
				writer.write(temp.outputMovie());
				writer.write("\n");
			}
		}
		writer.close();
		}
		
					
	
	/**
	 * returns Date object, given a string with format "mm/dd/yyyy"
	 * @param: stringDate: string value of the date
	 * @return: Date object
	 */
	@SuppressWarnings("deprecation")
	public static Date stringToDate(String stringDate) {
		String num[] = stringDate.split("/");
		Date newDate = new Date();
		newDate.setMonth(Integer.valueOf(num[0]));
		newDate.setDate(Integer.valueOf(num[1]));
		newDate.setYear(Integer.valueOf(num[2]));
		return newDate;
	}
	
	/**
	 * sorts linked lists of type movie by release date
	 * @param list: list to be sorted
	 */
	public void sortList(LinkedList<Movie> list) {
		comingIt = list.iterator();
		while(comingIt.hasNext()) {
			Movie current = comingIt.next();
			Movie compare = list.getLast();
			Movie temp = null;
			if(comingIt.hasNext() && current.getReleaseDate().after(compare.getReleaseDate())) {
				temp = current;
				list.remove(current);
				list.addLast(temp);
				comingIt = list.iterator();
				
			}
		}
	}
	/**
	 * Removes the movie from the coming list
	 * @param movieToDelete: movie to remove from list
	 */
	public void deleteMovie(Movie movieToDelete) {
		coming.removeFirstOccurrence(movieToDelete);
	}
	
	/**
	 * Releases movies based on user inputed date
	 * @param relDate: user specified release date
	 */
	@SuppressWarnings("deprecation")
	public void startShowingMovie(Date relDate) {
		comingIt = coming.iterator();
		while(comingIt.hasNext()) {
			Movie current = comingIt.next();
			//if(current.getReleaseDate().equals(relDate)) {
			if (current.getReleaseDate().getDate() == relDate.getDate()) {
				if (current.getReleaseDate().getMonth() == relDate.getMonth()) {
					if (current.getReleaseDate().getYear() == relDate.getYear()) {
						current.setStatus(Status.RELEASED);
						showing.add(current);
						coming.remove(current);
						comingIt = coming.iterator();
					}
				}
				
				}
			}
		}
	
	/**
	 * finds a movie object in the coming linked list
	 * @param title: user specified title
	 * @return: movie object if a title match is found, null otherwise
	 */
	public Movie findMovie(String title) {
		comingIt = coming.iterator();
		
		while(comingIt.hasNext()) {
			Movie current = comingIt.next();
			if(current.getMovieName().equalsIgnoreCase(title)) {
				return current;
			}
		}
		return null;
	}
	
	/**
	 * Finds the number of movies before user specified date
	 * @param givenDate: user specified date
	 * @return: integer val of movies before a given date
	 */
	public int numMoviesBeforeDate(Date givenDate) {
		comingIt = coming.iterator();
		int count = 0;
		
		while(comingIt.hasNext()) {
			Movie current = comingIt.next();
			if(givenDate.after(current.getReleaseDate())) {
				count++;
			}
		}
		return count;
	}

}
