import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;


import javafx.scene.image.*;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

/**
 * ProfileEditor.java
 * This class controls how the user interacts with the GUI, which is stored in Profile.fxml
 * @author Benjamin Thomas (905010)
 */
public class ProfileEditor extends Application{
	
	public static final int EDIT_WINDOW_WIDTH = 700;
	public static final int EDIT_WINDOW_HEIGHT = 400;
	public static final String EDIT_WINDOW_TITLE = "Profile";
	@FXML TextField firstNameTextBox;
	@FXML TextField lastNameTextBox;
	@FXML TextField userNameTextBox;
	@FXML TextField mobileTextBox;
	@FXML TextField addressTextBox;
	@FXML TextField postCodeTextBox;
	@FXML TextField addFavouriteTextBox;
	@FXML TextField removeFavouriteTextBox;
	@FXML Button saveButton;
	@FXML Button exitButton;
	@FXML Button addFavouriteButton;
	@FXML Button removeFavouriteButton;
	@FXML Button chooseImageButton;
	@FXML ListView<String> favouritesListBox;
	@FXML ListView<String> bidsListBox;
	@FXML ListView<String> placedArtworksListBox;
	@FXML ListView<String> wonArtworksListBox;
	@FXML Pane rootPane;
	@FXML ImageView profileImageDisplay;
	private Image profilePic;
	
	@FXML Profile currentProfile;
	@FXML ProfileManager manager;
	

	
	@FXML
	public void initialize() {
		
		saveButton.setOnAction(e -> {
			handleSaveButtonAction(e);
		});
		
		exitButton.setOnAction(e -> {
			handleExitButtonAction(e);
		});
		
		addFavouriteButton.setOnAction(e -> {
			handleAddFavouritesButton(e);
		});
		
		removeFavouriteTextBox.setOnAction(e -> {
			handleRemoveFavouritesButton(e);
		});
		
		removeFavouriteTextBox.setOnAction(e -> {
			handleChooseImageButton(e);
		});
	}
	
	@FXML
	public void getProfileInfo(ProfileManager manager, Profile currentProfile) {
		this.manager = manager;
		this.currentProfile = currentProfile;
		
		firstNameTextBox.setText(currentProfile.getFirstName());
		lastNameTextBox.setText(currentProfile.getLastName());
		userNameTextBox.setText(currentProfile.getUserName());
		mobileTextBox.setText(Long.toString(currentProfile.getMobile()));
		addressTextBox.setText(currentProfile.getAddress());
		postCodeTextBox.setText(currentProfile.getPostCode());
		File imageFile = new File(currentProfile.getPhotoLocation());
		profilePic = new Image(imageFile.toURI().toString());
		profileImageDisplay.setImage(profilePic);
		
		displayFavourites();
		displayBids();
		displayPlacedArtworks();
		displayWonArtworks();
		
	}
	
	@FXML
	private void handleAddFavouritesButton(ActionEvent e) {
		if (!addFavouriteTextBox.getText().isEmpty()) {
			String newFavourite = addFavouriteTextBox.getText();
			if (!this.currentProfile.isInFavourites(newFavourite)){
				Profile newProfile = manager.searchProfile(newFavourite);
				this.currentProfile.addNewFavourite(newProfile);
			}
			displayFavourites();
		}
	}
	
	@FXML
	private void handleRemoveFavouritesButton(ActionEvent e) {
		if (!addFavouriteTextBox.getText().isEmpty()) {
			String noLongerFavourite = removeFavouriteTextBox.getText();
			this.currentProfile.removeFavourite(noLongerFavourite);
			int numFavourites = currentProfile.numOfFavourites();
			
			favouritesListBox.getItems().clear();
			if (numFavourites > 0) {
				displayFavourites();
			}
		}
		
	}
	
	@FXML
	private void displayFavourites() {
		favouritesListBox.getItems().clear();
		for (int i = 0; i < currentProfile.numOfFavourites(); i++) {
			favouritesListBox.getItems().add(currentProfile.findFavourite(i).getUserName());
		}
	}
	
	@FXML
	private void displayBids() {
		bidsListBox.getItems().clear();
		for (int i = 0; i < currentProfile.numOfBids(); i++) {
			bidsListBox.getItems().add(currentProfile.findBid(i).toString());
		}
	}
	
	@FXML
	private void displayPlacedArtworks() {
		placedArtworksListBox.getItems().clear();
		for (int i = 0; i < currentProfile.numOfPlacedArtworks(); i++) {
			placedArtworksListBox.getItems().add(currentProfile.findPlacedArtwork(i).getTitle());
		}
	}
	
	@FXML
	private void displayWonArtworks() {
		wonArtworksListBox.getItems().clear();
		for (int i = 0; i < currentProfile.numOfWonArtworks(); i++) {
			wonArtworksListBox.getItems().add(currentProfile.viewWonArtwork(i));
		}
	}
	
	@FXML
	private void handleSaveButtonAction(ActionEvent e) {
		String handleFirstName = removeInvalidCharacters(firstNameTextBox.getText());
		firstNameTextBox.setText(capitaliseName(handleFirstName));
		
		String handleLastName = removeInvalidCharacters(lastNameTextBox.getText());
		lastNameTextBox.setText(capitaliseName(handleLastName));
		
		String handleAddress = validateAddress(addressTextBox.getText());
		addressTextBox.setText(handleAddress);
		
		String handlePostCode = validateAddress(postCodeTextBox.getText());
		postCodeTextBox.setText(handlePostCode);
		
		currentProfile.changeUserName(userNameTextBox.getText());
		currentProfile.changeFirstName(firstNameTextBox.getText());
		currentProfile.changeLastName(lastNameTextBox.getText());
		long newMobile = Long.parseLong(mobileTextBox.getText());
		currentProfile.changeMobile(newMobile);
		currentProfile.changeAddress(addressTextBox.getText());
		currentProfile.changePostCode(postCodeTextBox.getText());
		
		closeWindow();
		
	}
	
	/**
	 * Close the window.
	 */
	private void closeWindow() {
		Stage stage = (Stage) rootPane.getScene().getWindow();
	    stage.close();
	}
	
	@FXML
	private void handleExitButtonAction(ActionEvent e) {
		Stage stage = (Stage) rootPane.getScene().getWindow();
	    stage.close();
	}

	@FXML
	private void handleChooseImageButton(ActionEvent e) {
		try {
			
			FXMLLoader drawingFXMLLoader = new FXMLLoader(getClass().getResource("Select.fxml"));     
			Pane editRoot = (Pane)drawingFXMLLoader.load();          
			AvatarController editController = drawingFXMLLoader.<AvatarController>getController();
			editController.getProfileImageInfo(this.currentProfile);
			Scene editScene = new Scene(editRoot, 600, 400);
		    
			
			Stage editStage = new Stage();
			editStage.setScene(editScene);
			editStage.setTitle("Change Profile Image");
			
			editStage.initModality(Modality.APPLICATION_MODAL);
			editStage.showAndWait();
			refreshProfileImage();
			
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
	}
	
	private void refreshProfileImage() {
		File imageFile = new File(currentProfile.getPhotoLocation());
		profilePic = new Image(imageFile.toURI().toString());
		profileImageDisplay.setImage(profilePic);
	}
	

	/**
	 * Removes any invalid characters in the input
	 * @param change The input to have invalid characters removed
	 * @return A word/sentence with valid characters
	 */
	public static String removeInvalidCharacters(String change) {
		int i = 0;
		int remove = 0;
		char[] result = change.toCharArray();
		//While there is an invalid character, replace it and keep checking
		while (i < change.length()) {
			if (Character.isLetter(result[i])) {
				i++;
			} else if (result[i]==' ') {
				i++;
			} else {

				for (int z = i; z < result.length - 1; z++) {
					result[z] = result[z + 1];
				}
				remove++;
			}
		}

		//This removes the unwanted leftover characters that are left
		change = "";
		for (int y = 0; y < result.length - remove; y++) {
			change += result[y];
		}
		return change;
	}

	/**
	 * Removes invalid characters such as *, /, ( etc. from a 
	 * @param change The input that needs to be checked
	 * @return An output consisting of only letters and numbers
	 */
	public static String validateAddress(String change) {
		int i = 0;
		int remove = 0;
		char[] result = change.toCharArray();
		//While there is an invalid character, replace it and keep checking
		while (i < change.length()) {
			if (Character.isLetterOrDigit(result[i])) {
				i++;
			} else if (result[i]==' ') {
				i++;
			} else {

				for (int z = i; z < result.length - 1; z++) {
					result[z] = result[z + 1];
				}
				remove++;
			}
		}

		//This removes the unwanted leftover characters that are left
		change = "";
		for (int y = 0; y < result.length - remove; y++) {
			change += result[y];
		}
		return change;
	}
	
	/**
	 * This changes so that the first letter of each name is capital, while the
	 * rest of the characters are not
	 * @param name The string of the name to have corrected capitalisation
	 * @return The name with correct capitalisation
	 */
	public String capitaliseName(String name){
		
		String name1;
		String name2;
		//Split the name into two
		name1 = name.substring(0, 1);		
		name2 = name.substring(1, name.length());
		//The first name part is capital, the rest are not
		name1 = name1.toUpperCase();
		name2 = name2.toLowerCase();
		name = name1 + name2;
		return name;
	}
	
	
	@Override
	public void start(Stage profileStage) {
		try {
			ProfileManager mManager = new ProfileManager();
			ReadInformation read = new ReadInformation();	
			
			
			read.readFromFile(mManager, ReadInformation.DEFAULT_PROFILE_FILE_NAME);
			read.readFromFile(mManager, ReadInformation.DEFAULT_FAVOURITES_FILE_NAME);
			
			//Scene scene = new Scene(root, EDIT_WINDOW_WIDTH, EDIT_WINDOW_HEIGHT);
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));     
			Pane editRoot = (Pane)fxmlLoader.load();
			ProfileEditor editController = fxmlLoader.<ProfileEditor>getController();
			
			Scene editScene = new Scene(editRoot, EDIT_WINDOW_WIDTH, EDIT_WINDOW_HEIGHT);
			editController.getProfileInfo(mManager, mManager.searchProfile("Bewith34"));
	
			
			profileStage.setScene(editScene);
			profileStage.setTitle(EDIT_WINDOW_TITLE);
			profileStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
		
	
}