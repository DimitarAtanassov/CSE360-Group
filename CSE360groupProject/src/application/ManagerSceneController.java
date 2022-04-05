package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ManagerSceneController {

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML TextField deleteItemField;
	
	//Add item fields
	@FXML TextField itemNameField;
	@FXML TextField itemPriceField;
	@FXML TextField itemTimeField;
	@FXML TextArea ingredientsField;
	@FXML CheckBox appetizerBox;
	@FXML CheckBox entreeBox;
	@FXML CheckBox spacialsBox;
	@FXML CheckBox dessertBox;
	@FXML CheckBox drinksBox;
	
	//Assigning Coupons
	@FXML TextField searchOrderNum;
	@FXML TextArea potentialLoyalCustomers;
	@FXML TextArea selectedLoyalCustomers;
	@FXML ComboBox<String> cBoxLoyalCustomer;
	
	private ArrayList<String> potLoyalCustomersListGlobal = new ArrayList<String>();
	
	public void switchToHomeNonValidate(ActionEvent event) throws IOException { 
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(getClass().getResource("home.fxml"));
		 root = loader.load();
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 
		 HomeSceneController controller = loader.getController();
		 controller.initData();
		 
		 stage.setScene(scene);
		 stage.show(); 
	}
	
	public void switchToMenu(ActionEvent event) throws IOException
	{
		 FXMLLoader loader = new FXMLLoader();
		 loader.setLocation(getClass().getResource("menu.fxml"));
		 root = loader.load();
		 stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		 scene = new Scene(root);
		 
		 MenuSceneController controller = loader.getController();
		 controller.initData();
		 
		 stage.setScene(scene);
		 stage.show();  
	}
	
	public void logOut(ActionEvent event) throws IOException
	{
		root = FXMLLoader.load(getClass().getResource("login.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		
		LoggedInAccountData.cachedCustomers.add(LoggedInAccountData.loggedInCustomer);
		LoggedInAccountData.loggedInCustomer = null;
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void deleteMenuItem() throws IOException
	{
		LoggedInAccountData.appetizerMenu.removeItem(LoggedInAccountData.appetizerMenu.getItemByName(deleteItemField.getText()));
		LoggedInAccountData.entreeMenu.removeItem(LoggedInAccountData.entreeMenu.getItemByName(deleteItemField.getText()));
		LoggedInAccountData.spacialsMenu.removeItem(LoggedInAccountData.spacialsMenu.getItemByName(deleteItemField.getText()));
		LoggedInAccountData.desertMenu.removeItem(LoggedInAccountData.desertMenu.getItemByName(deleteItemField.getText()));
		LoggedInAccountData.drinksMenu.removeItem(LoggedInAccountData.drinksMenu.getItemByName(deleteItemField.getText()));
		deleteItemField.setText("");
	}
	
	public void addMenuItem() throws IOException
	{
		int itemPrice = Integer.parseInt(itemPriceField.getText());
		int timeToMake = Integer.parseInt(itemTimeField.getText());
		
		String str = ingredientsField.getText();
        String[] ingredients = str.split(",");
		
		FoodItem newItem = new FoodItem(itemPrice, itemNameField.getText(), ingredients, timeToMake);
		
		if(appetizerBox.isSelected())
		{
			LoggedInAccountData.appetizerMenu.addItem(newItem);
		}
		
		if(entreeBox.isSelected())
		{
			LoggedInAccountData.entreeMenu.addItem(newItem);
		}
		
		if(spacialsBox.isSelected())
		{
			LoggedInAccountData.spacialsMenu.addItem(newItem);
		}
		
		if(dessertBox.isSelected())
		{
			LoggedInAccountData.desertMenu.addItem(newItem);
		}
		
		if(drinksBox.isSelected())
		{
			LoggedInAccountData.drinksMenu.addItem(newItem);
		}
		
		itemPriceField.setText("");
		itemTimeField.setText("");
		ingredientsField.setText("");
		itemNameField.setText("");
	}
	
	public void searchForLoyalCustomers(ActionEvent event) {
		ArrayList<String> potLoyalCustomersList = new ArrayList<String>();
		for(int i = 0; i<LoggedInAccountData.cachedCustomers.size();i++) {
			if(Integer.parseInt(searchOrderNum.getText()) == (LoggedInAccountData.cachedCustomers.get(i).getOrderHistory().size())) {
				potLoyalCustomersList.add(LoggedInAccountData.cachedCustomers.get(i).getUserName());
			}
			
		}
		populateComboBox(potLoyalCustomersList);
		populatePotTextArea(potLoyalCustomersList);
	}
	
	public void populateComboBox(ArrayList<String> potLoyalCustomersList) {
		ObservableList<String> observableList5 = FXCollections.observableList(potLoyalCustomersList);
		cBoxLoyalCustomer.setItems(observableList5);
		potLoyalCustomersListGlobal = potLoyalCustomersList;
		
	}
	
	public void populatePotTextArea(ArrayList<String> potLoyalCustomersList) {
		potentialLoyalCustomers.setText(formatPotLoyalCustomerList(potLoyalCustomersList));
	}
	
	public void populateChosenTextArea(ArrayList<String> potLoyalCustomersList) {
		
	}
	
	
	public String formatPotLoyalCustomerList(ArrayList<String> potLoyalCustomersList) {
		StringBuilder fieldContent = new StringBuilder("");
		for(int i = 0; i < potLoyalCustomersList.size(); i++) {
			fieldContent.append(potLoyalCustomersList.get(i) + "\n\n");
		}
		return fieldContent.toString();
	}
	
	public void addLoyalCustomer(ActionEvent event) {
		
		if(cBoxLoyalCustomer.getValue() != null) {
			String selectedLoyalCust = cBoxLoyalCustomer.getValue();
			selectedLoyalCustomers.appendText(selectedLoyalCust + "\n\n");
			potLoyalCustomersListGlobal.add(selectedLoyalCust);
		}
		
	}
	
	public void removeLoyalCustomer(ActionEvent event) {
		
		if(cBoxLoyalCustomer.getValue() != null) {
			String selectedLoyalCust = cBoxLoyalCustomer.getValue();
			//refreshCombo();
			potLoyalCustomersListGlobal.remove(potLoyalCustomersListGlobal.indexOf(selectedLoyalCust));
			for(int i=0;i<potLoyalCustomersListGlobal.size();i++) {
				System.out.println("CustomerGlobalArray:" + potLoyalCustomersListGlobal.get(i));
			}
			
			selectedLoyalCustomers.setText("");
			for(int i=0;i<potLoyalCustomersListGlobal.size();i++) {
				selectedLoyalCustomers.appendText(potLoyalCustomersListGlobal.get(i) + "\n\n");
			}
			
		}
	}
//	public void refreshCombo() {
//		ArrayList<String> potLoyalCustomersList = new ArrayList<String>();
//		for(int i = 0; i<LoggedInAccountData.cachedCustomers.size();i++) {
//			if(Integer.parseInt(searchOrderNum.getText()) == (LoggedInAccountData.cachedCustomers.get(i).getOrderHistory().size())) {
//				potLoyalCustomersList.add(LoggedInAccountData.cachedCustomers.get(i).getUserName());
//			}
//			
//		}
//		populateComboBox(potLoyalCustomersList);
//	}
	
	

}
