import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Profile.java
 * This class stores and retrieves information about a single user's profile
 * @author Benjamin Thomas (905010)
 */

public class Profile {
	
	private String userName;
	private String firstName;
	private String lastName;
	private long mobile;
	private String address;
	private String postCode;
	private int lastDate;
	private int lastTime;
	private int lastMinute;
	private String photoLocation;
	private ArrayList<Bid> biddingList = new ArrayList<Bid>();
	private ArrayList<Profile> favouriteUsers = new ArrayList<Profile>();
	private ArrayList<Artwork> placedArtwork = new ArrayList<Artwork>();
	private ArrayList<Artwork> wonArtwork = new ArrayList<Artwork>();
	
	
	
	/**
	 * The constructor for the profile object
	 * @param username The user's username, used for logging in
	 * @param fullName The user's first and last name
	 * @param mobile The user's mobile number
	 * @param address The user's home address
	 * @param postCode The user's home postcode
	 */
	public Profile(String username, String fullName, long mobile, String address, String postCode) {
		String fName = splitName(fullName, true);
		String lName = splitName(fullName, false);
		setUserName(username);
		setFirstName(fName);
		setLastName(lName);
		setMobile(mobile);
		setAddress(address);
		setPostCode(postCode);
		setPhotoLocation(ReadInformation.DEFAULT_PHOTO1_FILE_NAME);
	}
	
	
	/**
	 * Gets the user's username
	 * @return the user's username
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the user's first name
	 * @return the user's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the user's last name
	 * @return the user's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the user's mobile number
	 * @return the user's mobile number
	 */
	public long getMobile() {
		return mobile;
	}

	/**
	 * Gets the user's home address
	 * @return the user's home address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Gets the user's post code
	 * @return the user's post code
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Gets the last date that the user logged in at
	 * @return the date of the user's last log in
	 */
	public int getLastDate() {
		return lastDate;
	}

	/**
	 * Gets the last hour that the user logged in at
	 * @return the hour of the user's last log in
	 */
	public int getLastTime() {
		return lastTime;
	}

	/**
	 * Gets the last minute that the user logged in at
	 * @return the minute of the user's last log in
	 */
	public int getLastMinute() {
		return lastMinute;
	}

	/**
	 * Gets the profile image's file location
	 * @return The profile image's file location
	 */
	public String getPhotoLocation() {
		return photoLocation;
	}

	/**
	 * Splits the user's first and last name apart
	 * @param fullName The user's full name to be split apart
	 * @param isFirstName Indicates whether we are searching for the user's first name
	 * 		or last name
	 * @return Either the first or last name
	 */
	public String splitName(String fullName, Boolean isFirstName) {
		String result = "";
		int splitValue = 0;
		
		//Finds where the space character is in the string
		for (int i = 0; i < fullName.length(); i++) {
			if (Character.isSpaceChar(fullName.charAt(i))) {
				splitValue = i;
			}
		}
		
		//Returns either the first or last name, depending on the parameter isFirstName
		if (isFirstName == true) {
			result = fullName.substring(0, splitValue);
			return result;
		} else {
			result = fullName.substring(splitValue + 1, fullName.length());
			return result;
		}

	}
	
	/**
	 * Updates the file location of the user's profile image
	 * @param location The location of the image
	 */
	public void updatePhotoLocation(String location) {
		setPhotoLocation(location);
	}

	/**
	 * This updates the user's login time to the system
	 */
	public void updateLoginTime() {
		int date = LocalDateTime.now().getDayOfMonth();
		int hour = LocalDateTime.now().getHour();
		int minutes = LocalDateTime.now().getMinute();
		setLastDate(date);
		setLastTime(hour);
		setLastMinute(minutes);
	}

	/**
	 * Updates the user login time when being read from storage
	 * @param date The date of the last login
	 * @param hour The hour of the last login
	 * @param minutes The minutes of the last login
	 */
	public void updateLoginTime(int date, int hour, int minutes) {
		setLastDate(date);
		setLastTime(hour);
		setLastMinute(minutes);
	}
	
	/**
	 * Returns the user's full name
	 * @return The user's first and last name together
	 */
	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	/**
	 * Returns the object as a string
	 * @return The profile as a readable sentence
	 */
	public String toString() {
		return getFullName() + " from " + getAddress();
	}

	/**
	 * Adds a new profile to the current user's favourites list
	 * @param p The profile to be added to favourites
	 */
	public void addNewFavourite(Profile p) {
		favouriteUsers.add(p);
	}
	
	/**
	 * Finds a user listed in favourites
	 * @param username The favourite user's username to be searched by
	 * @return The profile if it is found
	 */
	public Profile findFavourite(String username) {
		for (int i = 0; i < favouriteUsers.size(); i++) {
			if (favouriteUsers.get(i).getUserName().equals(username)) {
				return favouriteUsers.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Finds a user listed in favourites by their position in the list
	 * @param index The number that looks at a position in the list
	 * @return The profile that is stored in the index position
	 */
	public Profile findFavourite(int index) {
		return favouriteUsers.get(index);
	}
	
	/**
	 * Finds a bid that the user has placed
	 * @param index The number that looks at a position in the list
	 * @return The profile that is stored in the index position
	 */
	public Bid findBid(int index) {
		return biddingList.get(index);
	}
	
	/**
	 * Finds a piece of artwork that the user has placed
	 * @param index The number that looks at a position in the list
	 * @return The artwork that is stored in the index position
	 */
	public Artwork findPlacedArtwork(int index) {
		return placedArtwork.get(index);
	}
	
	/**
	 * Finds the number of favourite users
	 * @return The number of favourite users
	 */
	public int numOfFavourites() {
		return favouriteUsers.size();
	}
	
	/**
	 * Finds the number of bids the user has placed
	 * @return The number of bids the user has placed
	 */
	public int numOfBids() {
		return biddingList.size();
	}
	
	/**
	 * Finds the number of placed artworks
	 * @return The number of placed artworks
	 */
	public int numOfPlacedArtworks() {
		return placedArtwork.size();
	}
	
	
	/**
	 * Removes a user from the current user's favourites list
	 * @param username The username of the profile to be removed
	 */
	public void removeFavourite(String username) {
		favouriteUsers.remove(findFavourite(username));
	}

	/**
	 * Views all of the bids that the user has placed
	 * @return The bids that the user have placed in a readable format
	 */
	public String viewBids() {
		String result = "";
		for (int i = 0; i< biddingList.size(); i++) {
			result += biddingList.get(i).getBidArt() + " ";
			result += biddingList.get(i).getAmountPlaced() + " ";
			result += biddingList.get(i).getDateOfBid()+ "\n"; 			
		} 
		return result;
	}
	
	/**
	 * Adds a new bid to their bidding list
	 * @param b The bid to be added
	 */
	public void addNewBid(Bid b) {
		biddingList.add(b);
	}
	
	/**
	 * Submit a new piece of artwork into the system
	 * @param title The title of the artwork
	 * @param desc The description for the artwork
	 * @param photo The path for the photo used
	 * @param reserve The minimum amount of money for the bid
	 * @param maxBidAmount The maximum number of bids required to
	 * 			finish the bidding
	 */
	public void addNewArtwork(String title, String desc, String photo, 
    		double reserve, int maxBidAmount) {
		Artwork newArtWork = new Artwork(title, desc, photo, this, reserve, maxBidAmount);
		placedArtwork.add(newArtWork);
	}
	
	public Artwork findArtwork(String title){
		for (int i = 0; i < placedArtwork.size(); i++) {
			if (placedArtwork.get(i).getTitle().equals(title)) {
				return placedArtwork.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Views all of the user's submitted artwork
	 * @return The list of artworks that the user has added
	 */
	public String viewArtworks() {
		String result = "";
		for (int i = 0; i< placedArtwork.size(); i++){
			result += placedArtwork.get(i).getTitle() + " ";
			result += placedArtwork.get(i).getReservedPrice() + " ";
			result += placedArtwork.get(i).getDateUploaded()+ "\n"; 			
		} 
		return result;
	}
	
	/**
	 * When a user wins a piece of artwork, it is added to their list
	 * @param a The artwork that was won by the user
	 */
	public void addWonArtwork(Artwork a) {
		wonArtwork.add(a);
	}
	
	/**
	 * Returns the user's won artworks
	 * @return A list of the user's won artworks
	 */
	public String viewWonArtworks() {
		String result = "";
		for (int i = 0; i< wonArtwork.size(); i++) {
			result += wonArtwork.get(i).getTitle() + " ";
			result += wonArtwork.get(i).getReservedPrice() + " ";
			result += wonArtwork.get(i).getDateUploaded()+ "\n"; 			
		} 
		return result;
	}
	
	/**
	 * Returns a single artwork that the user has won
	 * @param index Where the artwork is stored in the list
	 * @return The won artwork as a readable sentence
	 */
	public String viewWonArtwork(int index) {
		return wonArtwork.get(index).toString();
	}
	
	/**
	 * Finds the number of artworks that the user has won
	 * @return The number of artworks that the user has won
	 */
	public int numOfWonArtworks() {
		return wonArtwork.size();
	}
	
	/**
	 * Returns whether or not the user is already a favourite
	 * @param username The username of the favourite user
	 * @return True if the user is already in the favourites list, otherwise false
	 */
	public boolean isInFavourites(String username){
		for(int i = 0; i < favouriteUsers.size(); i++) {
			if (favouriteUsers.get(i).getUserName().equalsIgnoreCase(username)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * The setter for the username
	 * @param username The user's username
	 */
	public void changeUserName(String username) {
		this.userName = username;
	}

	/**
	 * The setter for the first name
	 * @param firstName The user's first name
	 */
	public void changeFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * The setter for the last name
	 * @param lastName The user's last name
	 */
	public void changeLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * The setter for the user's mobile number
	 * @param mobNumber The user's mobile number
	 */
	public void changeMobile(long mobNumber) {
		this.mobile = mobNumber;
	}

	/**
	 * The setter for the user's address
	 * @param address The user's address
	 */
	public void changeAddress(String address) {
		this.address = address;
	}

	/**
	 * The setter for the user's postcode
	 * @param postCode The user's postcode
	 */
	public void changePostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Sets the last login-time's date
	 * @param lastDate The date that the user last logged in
	 */
	public void changeLastDate(int lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * Sets the last login-time's hour
	 * @param lastTime The hour that the user last logged in
	 */
	public void changeLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * Sets the last login-time's minutes
	 * @param lastMinute The minutes that the user last logged in
	 */
	public void changeLastMinute(int lastMinute) {
		this.lastMinute = lastMinute;
	}

	/**
	 * Sets the location of the user's profile image
	 * @param photoLocation The string of the photo's file location
	 */
	public void changePhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
	
	/**
	 * The setter for the username
	 * @param username The user's username
	 */
	private void setUserName(String username) {
		this.userName = username;
	}

	/**
	 * The setter for the first name
	 * @param firstName The user's first name
	 */
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * The setter for the last name
	 * @param lastName The user's last name
	 */
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * The setter for the user's mobile number
	 * @param mobNumber The user's mobile number
	 */
	private void setMobile(long mobNumber) {
		this.mobile = mobNumber;
	}

	/**
	 * The setter for the user's address
	 * @param address The user's address
	 */
	private void setAddress(String address) {
		this.address = address;
	}

	/**
	 * The setter for the user's postcode
	 * @param postCode The user's postcode
	 */
	private void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Sets the last login-time's date
	 * @param lastDate The date that the user last logged in
	 */
	private void setLastDate(int lastDate) {
		this.lastDate = lastDate;
	}

	/**
	 * Sets the last login-time's hour
	 * @param lastTime The hour that the user last logged in
	 */
	private void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * Sets the last login-time's minutes
	 * @param lastTime The minutes that the user last logged in
	 */
	private void setLastMinute(int lastMinute) {
		this.lastMinute = lastMinute;
	}

	/**
	 * Sets the location of the user's profile image
	 * @param photoLocation The string of the photo's file location
	 */
	private void setPhotoLocation(String photoLocation) {
		this.photoLocation = photoLocation;
	}
}