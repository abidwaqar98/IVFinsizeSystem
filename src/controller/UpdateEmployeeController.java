package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Main;
import database.MySQLDatabase;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;

import javafx.scene.control.TextArea;

import javafx.scene.control.PasswordField;

public class UpdateEmployeeController {
	@FXML
	private TextField IDFeild;
	@FXML
	private TextArea addressArea;
	@FXML
	private TextField nameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField phoneField;
	@FXML
	private Button find;
	@FXML
	private Label lbl;
	@FXML
	private Button UpdateButton;
	@FXML
	private Button Back;
	MySQLDatabase DB;
	

	// Event Listener on Button[#find].onAction
	@FXML
	public void FindInfo(ActionEvent event) throws SQLException
	{
		
		if(validateInput())
		{
			String d = IDFeild.getText();
			try {
				DB = MySQLDatabase.getInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ArrayList<ArrayList<String>> data = DB.getIndexValue("employee", "ID", d);
			
			if(data.size()>0 && data.get(0).size()>0)
			{
				lbl.setText("");
				nameField.setDisable(false);
				passwordField.setDisable(false);
				phoneField.setDisable(false);
				addressArea.setDisable(false);	
				System.out.println(data.get(0).get(0));
				System.out.println(data.get(0).get(1));
				System.out.println(data.get(0).get(2));
				System.out.println(data.get(0).get(3));
				nameField.setText(data.get(0).get(1));
				passwordField.setText(data.get(0).get(2));
				phoneField.setText(data.get(0).get(3));
				addressArea.setText(data.get(0).get(4));		
			}
			else
			{
				Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Invalid Employee ID");
        		alert.setHeaderText(null);
        		alert.setContentText("No Information Found! Please Enter Employee Valid Employee ID");
        		alert.showAndWait();
        		nameField.clear();
				passwordField.clear();
				phoneField.clear();
				addressArea.clear();
				nameField.setDisable(true);
				passwordField.setDisable(true);
				phoneField.setDisable(true);
				addressArea.setDisable(true);	
        	}
		}
		
	}
	// Event Listener on Button[#UpdateButton].onAction
	@FXML
	public void UpdateEmp(ActionEvent event)
	{
		String ID = IDFeild.getText();
		String name = nameField.getText();
		String address = addressArea.getText();
		String phone = phoneField.getText();
		String password = passwordField.getText();
		
		try {
			MySQLDatabase.getInstance().updateEmployee(ID, name, password, phone, address);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Employee Updated");
			alert.setHeaderText("Updated");
			alert.setContentText("Employee account has been Updated successfully");
			alert.showAndWait();
			handleCancel(event);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Employee ID not Found");
			alert.setHeaderText(null);
			alert.setContentText("No Information Found! Please Enter Employee Valid Employee ID");
			alert.showAndWait();
		}
	}
	// Event Listener on Button[#Back].onAction
	@FXML
	public void handleCancel(ActionEvent event) throws IOException {
		System.out.println("Authenticate");
		Parent root = FXMLLoader.load(getClass().getResource("/view/ManagerScreen.fxml"));
		Scene scene = new Scene(root, 1920, 990);
		Main.Get_Stage().setScene(scene);
		Main.Get_Stage().show();
	}
	public boolean validateInput() 
	{

        String errorMessage = "";
      
        if (IDFeild.getText() == null || IDFeild.getText().length()<=0) {
            errorMessage += "Please enter Employee ID!\n";
            lbl.setText(errorMessage);
            nameField.clear();
			passwordField.clear();
			phoneField.clear();
			addressArea.clear();
			nameField.setDisable(true);
			passwordField.setDisable(true);
			phoneField.setDisable(true);
			addressArea.setDisable(true);	
            return false;
        
        }

        else
        {
            return true;
        }     }
}
