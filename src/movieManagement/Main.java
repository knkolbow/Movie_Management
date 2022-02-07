package movieManagement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		// Open input and output files and streams
		FileInputStream inputFile = new FileInputStream("Movies.txt");
		Scanner scnr = new Scanner(inputFile);

		// Create list object
		MovieList list = new MovieList();
		
		// Reads movies in from the input file
		list.loadMovies(scnr);

		// Displays menu to console
		UserMenu menu = new UserMenu();
		menu.displayMenu(list);
		
		scnr.close();
		inputFile.close();
	}
}
