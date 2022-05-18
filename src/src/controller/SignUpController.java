package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import dbconnection.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;

import static javafx.fxml.FXMLLoader.load;

/**
 * FXML Controller class
 *
 * @author aimih
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsernme;
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtHomeTown;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        txtId.setText("");
        txtPassword.setText("");
        txtUsernme.setText("");
        txtPhoneNo.setText("");
        txtHomeTown.setText("");

    }

    @FXML
    private void CreateAccountActionMethod(ActionEvent event) throws IOException{

        boolean isUserNameExists = false;
        Customer cust = null;

        // Check user enter the ID
        if (!txtId.getText().trim().isEmpty()) {

            // Check user enter the ID
            if (!txtPassword.getText().trim().isEmpty()) {

                // Check user enter the ID
                if (!txtUsernme.getText().trim().isEmpty()) {

                    // Check whether entered User Name already exists or not from Data Base
                    if(isUserNameExists(txtUsernme.getText().trim())){

                        isUserNameExists = true;
                        System.out.println("User Name Exists");

                    }

                    // User Name does not exists then
                    if (!isUserNameExists) {

                        // Check user enter the Home Town
                        if (!txtHomeTown.getText().trim().isEmpty()) {

                            // Check user enter the Phone Number
                            if (!txtPhoneNo.getText().trim().isEmpty()) {

                                cust = new Customer();
                                cust.setId(Integer.valueOf(txtId.getText().trim()));
                                cust.setUname(txtUsernme.getText().trim());
                                cust.setPassword(txtPassword.getText().trim());
                                cust.setHomeTown(txtHomeTown.getText().trim());
                                cust.setPhoneNumber(txtPhoneNo.getText().trim());

                                // Add the Customer to Database
                                addCustomer(cust);

                                new Alert(Alert.AlertType.INFORMATION, "Account Created").showAndWait();

                                // Move window to Login window
                                Parent root = load((getClass().getResource("/view/Login.fxml")));
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();

                            } else {
                                new Alert(Alert.AlertType.ERROR, "Phone No required").showAndWait();
                            }
                        } else {
                            new Alert(Alert.AlertType.ERROR, "Home Town required").showAndWait();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Username already exist").showAndWait();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Username required").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Password required").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Id required").showAndWait();
        }

    }

    // CHECKS USER EXISTS in Database
    public boolean isUserNameExists(String uname) {

        Connection con = DataBaseConnection.getDBConnection();
        ResultSet result;

        try {

            PreparedStatement preparedStatement = con.prepareStatement("select username from customers where username = ?");

            preparedStatement.setString(1, uname);

            result = preparedStatement.executeQuery();

            return (result.next());

        } catch (SQLException ex) {

            System.out.println("Exception!");
        }
        return false;
    }

    // ADD CUSTOMER IN DATABASE
    public Integer addCustomer(Customer c) {

        Connection con = DataBaseConnection.getDBConnection();
        int result = 0;

        try {

            PreparedStatement preparedStatement = con.prepareStatement("insert into customers values(?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, c.getId());
            preparedStatement.setString(2,c.getUname());
            preparedStatement.setString(3,c.getPassword());
            preparedStatement.setString(4,c.getHomeTown());
            preparedStatement.setString(5,c.getPhoneNumber());

            result = preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            System.out.println(" Add Customer Exception!");
        }

        return result;
    }
}
