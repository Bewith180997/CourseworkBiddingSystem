import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * ReadInformation.java
 * This class reads in information about the profiles, their artwork, bids, favourites
 * when the system turns on. And when it closes, it writes the updated information into files.
 * @author Benjamin Thomas (905010)
 */

public class ReadInformation {
	
	public static final String CHOSEN_DELIMITER = ",";
	public static final String DEFAULT_PROFILE_FILE_NAME = "profiles.txt" ;
	public static final String DEFAULT_FAVOURITES_FILE_NAME = "favourites.txt";
	public static final String DEFAULT_ARTWORK_FILE_NAME = "artworks.txt";
	public static final String DEFAULT_FILE_LOC = "images/";
	public static final String DEFAULT_PHOTO_EXTENSION = ".png";
	public static final String DEFAULT_PHOTO1_FILE_NAME = DEFAULT_FILE_LOC + "profile1" + DEFAULT_PHOTO_EXTENSION;
	public static final String DEFAULT_PHOTO2_FILE_NAME = DEFAULT_FILE_LOC + "profile2" + DEFAULT_PHOTO_EXTENSION;
	public static final String DEFAULT_PHOTO3_FILE_NAME = DEFAULT_FILE_LOC + "profile3" + DEFAULT_PHOTO_EXTENSION;
	public static final String DEFAULT_PHOTO4_FILE_NAME = DEFAULT_FILE_LOC + "profile4" + DEFAULT_PHOTO_EXTENSION;
	public static final String DEFAULT_PHOTO5_FILE_NAME = DEFAULT_FILE_LOC + "profile5" + DEFAULT_PHOTO_EXTENSION;
	
	/**
	 * The constructor for the read information class
	 */
	public ReadInformation() {
		
	}
	
	

	/**
	 * Reads information from different files
	 * @param nameOfFile The file as a path, name and extension to be opened
	 */
	public void readFromFile(ProfileManager manager, String nameOfFile) {
		File inputFile = new File(nameOfFile);
		
		Scanner in = null; 
		try { // If the file exists
			in = new Scanner(inputFile);
		} catch (FileNotFoundException e1) { //If the file does not exist/cannot be opened
			System.out.println ("The file '" + nameOfFile + "' does not exist/cannot be opened");
			System.exit(0);
		} catch (Exception e2) { //If another error occurs
			System.out.println ("An unexpected error has occured");
			System.exit(0);
		}
		
		if (nameOfFile.equals(DEFAULT_PROFILE_FILE_NAME)) {
			searchAllProfiles(manager, in);
		} else if (nameOfFile.equals(DEFAULT_FAVOURITES_FILE_NAME)) {
			readFavourites(manager, in);
		} else if (nameOfFile.equals(DEFAULT_ARTWORK_FILE_NAME)) {
			readProfileArtworks(manager,in);
		}
		
	}
	
	/**
	 * Scans through the file and creates profiles from the information
	 * @param manager Where the profiles are going to be stored
	 * @param in The scanner that reads the information
	 */
	public void searchAllProfiles(ProfileManager manager,Scanner in) {
		
		in.useDelimiter(CHOSEN_DELIMITER);
		
		String username;
		String fullName;
		long mobile;
		String address;
		String postcode;
		String photo;
		while (in.hasNext()) {
			try {
				username = in.next();
				fullName = in.next();
				mobile = in.nextLong();
				address = in.next();
				postcode = in.next();
				
				manager.addNewProfile(username, fullName, mobile, address, postcode);
				
				readProfileLogin(manager, username, in);
				photo = readPhoto(in, username);
				manager.searchProfile(username).updatePhotoLocation(photo);
				
				String throwaway = in.nextLine();//Used to remove the \r\n character at the end
				throwaway = removeUnwantedCharaters(throwaway);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
			
		}
		
	}
	
	/**
	 * Removes the unwanted elements that come at the end of a line
	 * @param changeable The line to be changed
	 * @return The changeable element with one less character
	 */
	public String removeUnwantedCharaters(String changeable){
		int length = changeable.length();
		return changeable.substring(0, length - 1);
	}
	
	/**
	 * This reads a user's last log in time
	 * @param manager Where the profiles are stored in memory
	 * @param username The username of the profile to have updated log in times
	 * @param in The scanner that finds and returns the desired inputs
	 */
	public void readProfileLogin(ProfileManager manager, String username, Scanner in) {
		int date = in.nextInt();
		int hour = in.nextInt();
		int minutes = in.nextInt();
		manager.updateProfileLoginTime(username, date, hour, minutes);
	}
	
	/**
	 * Finds a list of a single user's favourite users
	 * @param manager Where the profiles are stored in memory
	 * @param in The scanner that finds and returns the desired inputs
	 */
	public void readFavourites(ProfileManager manager, Scanner in) {
		in.useDelimiter(CHOSEN_DELIMITER);
		String user = "";
		String favourite = "";
		for (int i = 0; i < manager.profileList.size(); i++) {
			user = in.next();
			favourite = in.next();
			int index = manager.profileList.indexOf(manager.searchProfile(user));
			while(!favourite.contains("/FAVENDS")) {
				
				manager.profileList.get(index).addNewFavourite(manager.searchProfile(favourite));
				favourite = in.next();
				
			}
			String throwaway = in.nextLine();//Used to remove the \r\n character at the end
			throwaway = removeUnwantedCharaters(throwaway);
		}
		
	}
	
	/**
	 * Finds and sets the file location of a user
	 * @param in The scanner that finds and returns the desired inputs
	 * @param username The username of the profile to have a profile image
	 * @return The profile image that the user has chosen
	 */
	public String readPhoto(Scanner in, String username) {
		int photo = in.nextInt();
		String photoLocation = "";
		
		if (photo == 1) {
			photoLocation = DEFAULT_PHOTO1_FILE_NAME;
		} else if (photo ==2) {
			photoLocation = DEFAULT_PHOTO2_FILE_NAME;
		}else if (photo ==3) {
			photoLocation = DEFAULT_PHOTO3_FILE_NAME;
		}else if (photo ==4) {
			photoLocation = DEFAULT_PHOTO4_FILE_NAME;
		}else if (photo ==5) {
			photoLocation = DEFAULT_PHOTO5_FILE_NAME;
		} else if (photo ==2) {
			photoLocation = DEFAULT_FILE_LOC + username + DEFAULT_PHOTO_EXTENSION;
		} else { //Should not happen
			photoLocation = DEFAULT_PHOTO1_FILE_NAME;
		}
		
		return photoLocation;
	}
	
	/**
	 * Reads the different artworks that a user has placed in the system
	 * @param manager Where the profiles are stored in memory
	 * @param in The scanner that finds and returns the desired inputs
	 */
	public void readProfileArtworks(ProfileManager manager, Scanner in) {
		in.useDelimiter(CHOSEN_DELIMITER);
		String user = "";
		String title = "";
		String desc = "";
		String photo = ""; 
    
    	double reserve = 0.00;
    	int maxBidAmount = 0;
		for (int i = 0; i < manager.profileList.size(); i++) {
			user = in.next();
			title = in.next();
			int index = manager.profileList.indexOf(manager.searchProfile(user));
			while (!title.contains("/FAVENDS")) {
				desc = in.next();
				photo = in.next();
				reserve = in.nextDouble();
				maxBidAmount = in.nextInt();
				manager.profileList.get(index).addNewArtwork(title, desc, photo, reserve, maxBidAmount);
				title = in.next();
				
			}
		}
		
	}
	
	/**
	 * Reads all of the user's different bids
	 * @param p The individual profile who has placed bids
	 * @param in The scanner that finds and returns the desired inputs
	 */
	public void readProfileBids(Profile p, Scanner in) {
		while (in.hasNext()) {
			String nextBid = in.next();
			if (nextBid.equalsIgnoreCase("/BEGINBID")) {
				
			}
		}
	}
	
	/**
	 * Writes all of the different information into files
	 * @param manager Where the profiles are stored in memory, to be read from
	 */
	public void writeToFile(ProfileManager manager) {
		File newFile = new File(DEFAULT_PROFILE_FILE_NAME);
		try {
			FileWriter writer = new FileWriter(newFile);
			for (int i = 0; i < manager.profileList.size(); i++) {
				
				writer.write(manager.profileList.get(i).getUserName() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getFullName() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getMobile() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getAddress() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getPostCode() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getLastDate() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getLastMinute() + CHOSEN_DELIMITER);
				writer.write(manager.profileList.get(i).getLastTime() + CHOSEN_DELIMITER);
				int profileImageDecider;
				String fileLocation = manager.profileList.get(i).getPhotoLocation();
				if (fileLocation.equals(DEFAULT_PHOTO1_FILE_NAME)) {
					profileImageDecider = 1;
				} else if (fileLocation.equals(DEFAULT_PHOTO2_FILE_NAME)) {
					profileImageDecider = 2;
				}else if (fileLocation.equals(DEFAULT_PHOTO3_FILE_NAME)) {
					profileImageDecider = 3;
				}else if (fileLocation.equals(DEFAULT_PHOTO4_FILE_NAME)) {
					profileImageDecider = 4;
				}else if (fileLocation.equals(DEFAULT_PHOTO5_FILE_NAME)) {
					profileImageDecider = 5;
				} else if (fileLocation.equals(DEFAULT_FILE_LOC 
					+ manager.profileList.get(i).getUserName() + DEFAULT_PHOTO_EXTENSION)) {
					profileImageDecider = 6;
				} else { //Should not happen
					profileImageDecider = 1;
				}
				writer.write(profileImageDecider + CHOSEN_DELIMITER);
				writer.write("\r\n");
				
			}
			
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes every profile's favourite users to a file
	 * @param manager Where the profiles are stored in memory, to be read from
	 */
	public void writeFavouritesToFile(ProfileManager manager) {
		File newFile = new File(DEFAULT_FAVOURITES_FILE_NAME);
		try {
			FileWriter writer = new FileWriter(newFile);
			for (int i = 0; i < manager.profileList.size(); i++) {
				writer.write(manager.profileList.get(i).getUserName() + CHOSEN_DELIMITER);
				for (int z = 0; z < manager.profileList.get(i).numOfFavourites(); z++) {
					writer.write(manager.profileList.get(i).
							findFavourite(i).getUserName() + CHOSEN_DELIMITER);
				}
				writer.write("/FAVENDS" + CHOSEN_DELIMITER);
				writer.write("\r\n");
				
			}
			
			writer.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}