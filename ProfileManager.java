import java.util.ArrayList;

/**
 * ProfileManager.java 
 * This class manages multiple instances of the Profile class 
 * @author Benjamin Thomas (905010) 
 */

public class ProfileManager {
	
	ArrayList<Profile> profileList = null;

	/**
	 * Constructor for the profile manager
	 */
	public ProfileManager() {
		profileList = new ArrayList<Profile>();
	}

	/**
	 * Creates a new profile and adds it to the profile list
	 * @param userName The user's username
	 * @param fullName The user's first and last name separated by a single space character
	 * @param mobile The user's mobile number
	 * @param address The user's address
	 * @param postCode The user's post code
	 */
	public void addNewProfile(String userName, String fullName, long mobile, String address, String postCode) {
		Profile newProfile = new Profile(userName, fullName, mobile, address, postCode);
		profileList.add(newProfile);
	}

	/**
	 * Searches for a profile using their username
	 * @param username The username of the profile being searched for
	 * @return The desired profile if it exists
	 */
	public Profile searchProfile(String username) {
		Profile resultProfile = null;
		for (int i = 0; i < profileList.size(); i++) {
			if (profileList.get(i).getUserName().equals(username)) {
				resultProfile = profileList.get(i);
			}
		}
		if (resultProfile == null) {
			System.out.println("Unable to find profile \"" + username + "\"");
			//throw new NullPointerException("Unable to find profile \"" + userName + "\"");
		}
		return resultProfile;
	}

	/**
	 * Removes a profile from the profile list
	 * @param username The username of the profile to be removed
	 */
	public void removeProfile(String username) {
		profileList.remove(searchProfile(username));
	}
	
	/**
	 * Adds a new user to a specified user's favourites list
	 * @param p The profile adding the user to their favourites list
	 * @param username The user to be added to the favourites list
	 */
	public void addFavourite(Profile p, String username) {
		p.addNewFavourite(searchProfile(username));
	}
	
	/**
	 * Updates the user's login time and returns the logged in profile
	 * @param username The profile's username, taken from the login
	 */
	public void login(String username) {
		searchProfile(username).updateLoginTime();
		searchProfile(username);
		
	}
	
	/**
	 * This is for times where the profile is read from storage but hasn't logged in
	 * @param username The username of the profile to have their login times updated
	 * @param date The date of the last login
	 * @param hour The hour of the last login
	 * @param minutes The minutes of the last login
	 */
	public void updateProfileLoginTime(String username, int date, int hour, int minutes){
		searchProfile(username).updateLoginTime(date, hour, minutes);
	}
	
}