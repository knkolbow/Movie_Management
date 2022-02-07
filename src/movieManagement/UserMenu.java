package movieManagement;

import java.util.Date;
import java.util.Scanner;

import movieManagement.Movie.Status;

public class UserMenu {
	// Data fields
			String inputKey;
			Scanner scnr;
			
			// Constructors
			
			public UserMenu() { 
				inputKey = "0"; 
				scnr = new Scanner(System.in);
			}
			
			// Methods
			
			/**
			 * Displays menu options to the console
			 */
			public void displayMenu(MovieList list) {

				System.out.println("MOVIE MANAGEMENT MENU:\n");
				System.out.println("a) Display All Movies\nb) Add Movie\nc) Edit Release Date\n"
						+ "d) Edit Movie Description\ne) Start Showing Movies in Theater\n"
						+ "f) Display Number of Movies Coming Before a Specified Date\ng) Save Changes\n" + "q) Exit\n");
				
				//loop that prompts for menu options until user quits, presses "q"
						while (!(inputKey.equals("q"))) {

							System.out.println("Enter the letter corresponding to the option you wish to choose: ");
							System.out.println("Enter 'h' to display menu");
							inputKey = scnr.next().toLowerCase().trim();
							
							// Display All movies
							if (inputKey.equals("a")) {
								list.displayComing();
								list.displayShowing();
								
								// Add Movie
							} else if (inputKey.equals("b")) {
								// Prompt user for name, description, dates, and status
								System.out.println("Enter a Title: ");
								//clears out return
								scnr.nextLine();
								String movieName = scnr.nextLine().trim();

								System.out.println("Enter Description: ");
								String description = scnr.next();

								System.out.println("Enter a Release Date (MM/DD/YYYY): ");
								String releaseDate = scnr.next().trim();
								// Checks if date entered is valid
								while (!isValidDate(releaseDate)) {
									System.out.println("Error: Invalid Date");
									System.out.println("Enter Release Date (MM/DD/YYYY): ");
									releaseDate = scnr.next().trim();
								}
								//changes the user given date to Date type
								Date rLDate = MovieList.stringToDate(releaseDate);

								System.out.println("Enter a Received Date (MM/DD/YYYY): ");
								String receivedDate = scnr.next().trim();
								// Checks if date entered is valid
								while (!isValidDate(receivedDate)) {
									System.out.println("Error: Invalid Date");
									System.out.println("Enter Received Date (MM/DD/YYYY): ");
									releaseDate = scnr.next().trim();
								}
								//changes the user given date to Date type
								Date rCDate = MovieList.stringToDate(receivedDate);

								System.out.println("Enter movie status (released or received): ");
								String status = scnr.next().toLowerCase().trim();

								//loops while user hasn't entered 'released' or 'received'
								while(!status.equalsIgnoreCase("released")) {
									while(!status.equalsIgnoreCase("received")) {
										System.out.println("Please Enter a valid form of status!");
										status = scnr.next().toLowerCase().trim();
										//if the user types released within this loop, breaks loop
										if(status.equalsIgnoreCase("released")) {
											break;
										}
									}
									break;
								}
								//adds movie to MovieList, sets status depending on 'released' or 'received'
								if (status.equals("released")) {
									Movie movieToAdd = new Movie(rLDate, movieName, description, rCDate, Status.RELEASED);
									list.addMovie(movieToAdd);
								} else {
									Movie movieToAdd = new Movie(rLDate, movieName, description, rCDate, Status.RECEIVED);
									list.addMovie(movieToAdd);
								}

								// Edit Release Date
							} else if (inputKey.equals("c")) {
								System.out.println("Enter movie name: ");
								//clears return
								scnr.nextLine();
								String movieName = scnr.nextLine().trim();
								// Checks if movie name is in list
								while (list.findMovie(movieName) == null) {
									System.out.println("Error: Movie Does Not Exist");
									System.out.println("Enter movie name: ");
									movieName = scnr.nextLine().trim();
								}
								Movie selection = list.findMovie(movieName);
								System.out.println("Enter new release date (MM/DD/YYYY) : ");
								String editedDate = scnr.next().trim();
								// Checks if date entered is valid
								while (!isValidDate(editedDate)) {
									System.out.println("Error: Invalid Date");
									System.out.println("Enter new release date (MM/DD/YYYY): ");
									editedDate = scnr.next().trim();
								}
								Date newRelease = MovieList.stringToDate(editedDate);
								//sets user release date 
								selection.setReleaseDate(newRelease);
								list.deleteMovie(selection);
								list.addMovie(selection);
								
								// Edit Movie Description
							} else if (inputKey.equals("d")) {
								System.out.println("Enter movie name: ");
								scnr.nextLine();
								String movieName = scnr.nextLine().trim();
								// Checks if movie name is in list
								while (list.findMovie(movieName) == null) {
									System.out.println("Error: Movie Does Not Exist");
									System.out.println("Enter movie name: ");
									movieName = scnr.nextLine().trim();
								}
								Movie selection = list.findMovie(movieName);
								System.out.println("Enter new description: ");
								String editedDesc = scnr.next().trim();
								//sets user description
								selection.setDescription(editedDesc);
								
							
								// Start Showing Movie in theaters with the given release Date
							} else if (inputKey.equals("e")) {
								System.out.println("Enter the release date for the movie you want to start showing (MM/DD/YYYY: ");
								String givenDate = scnr.next().trim();
								// Checks if date entered is valid
								while (!isValidDate(givenDate)) {
									System.out.println("Error: Invalid Date");
									System.out.println("Enter the release date for the movie you want to start showing (MM/DD/YYY): ");
									givenDate = scnr.next().trim();
								}
								Date prefDate = MovieList.stringToDate(givenDate);
								list.startShowingMovie(prefDate);
								
								
								// Display Number of Movies before Specified Date
							} else if (inputKey.equals("f")) {
								System.out.println("Enter a Date (MM/DD/YYYY): ");
								String specifiedDate = scnr.next().trim();
								// Checks if date entered is valid
								while (!isValidDate(specifiedDate)) {
									System.out.println("Error: Invalid Date");
									System.out.println("Enter a Date  (MM/DD/YYY): ");
									specifiedDate = scnr.next().trim();
								}
								Date storedDate = MovieList.stringToDate(specifiedDate);
								int numMovies = list.numMoviesBeforeDate(storedDate);
								System.out.println("Number of movies before " + specifiedDate + " is " + numMovies);
								
								
								 // Save Changes
							} else if (inputKey.equals("g")) {
								try {
								list.printList();
								} catch(Exception e) {
									System.out.println("Unable to save changes");
								}
								
								// Display menu again
							} else if (inputKey.equals("h")) {
								displayMenu(list);
								
								// Quit
							} else if (inputKey.equals("q")) {
								break;
								
								// Invalid input
							} else {
								System.out.println("Invalid Input! Please enter one of the menu characters.\n");
							}
						}
				scnr.close();
			}
			
			/**
			 * Checks if the date is valid
			 * @param date: the date to check
			 * @return: true is date is valid, false if date is NOT valid
			 */
			public static boolean isValidDate(String date) {
				String num[] = date.split("/");
				if (num.length == 3) {
					int month = Integer.valueOf(num[0]);
					int day = Integer.valueOf(num[1]);
					int year = Integer.valueOf(num[2]);
					if (month < 13 && month > 0) {
						if (day < 32 && day > 0) {
							if (year < 2050 && year > 1950) {
								return true;
							}
						}
					}
				}
					return false;
				}

			}

