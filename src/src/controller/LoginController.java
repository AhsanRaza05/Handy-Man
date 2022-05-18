package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbconnection.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import model.Customer;

import static javafx.fxml.FXMLLoader.load;

/**
 * FXML Controller class
 *
 * @author aimih
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Label signin;
    @FXML
    private RadioButton customer;
    @FXML
    private RadioButton offer;

    static String userName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ToggleGroup tg = new ToggleGroup();
        customer.setToggleGroup(tg);
        offer.setToggleGroup(tg);
        customer.setSelected(true);

    }

    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException {

        boolean isUserExists = false;

        // Check User Entered data or not.
        if (!username.getText().trim().isEmpty()) {

            // Check User Entered Password or not.
            if (!password.getText().trim().isEmpty()) {

                // Check User Credentials i.e whether user exists or not from database.
                 if (getCustomer(username.getText().trim(), password.getText().trim()) != null) {

                     // If exists then Save uername, so that it can be used insert in ServiceDetails table
                     userName = username.getText().trim();
                     isUserExists = true;

                     if (customer.isSelected()) {

                         Parent root = load((getClass().getResource("/view/SearchService.fxml")));
                         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                         stage.setScene(new Scene(root));
                         stage.show();
                     } else if (offer.isSelected()) {

                         Parent root = load((getClass().getResource("/view/OfferService.fxml")));
                         Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                         stage.setScene(new Scene(root));
                         stage.show();
                     }

                 }else{

                     new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").showAndWait();
                 }
            } else {
                new Alert(Alert.AlertType.ERROR, "Enter password").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Enter username").showAndWait();
        }

    }

    @FXML
    private void signUpAction(MouseEvent event) {

        try {

            Parent root = load((getClass().getResource("/view/SignUp.fxml")));

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Code to Search user From database
    public Customer getCustomer(String username, String password) {

        // Get Data Base Connection
        Connection con = DataBaseConnection.getDBConnection();

        Customer cus = null;

        ResultSet result;

        try {

            PreparedStatement preparedStatement = con.prepareStatement("select * from customers where username = ? AND password = ?");

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            result = preparedStatement.executeQuery();

            if(result.next()){
                cus = new Customer();
                cus.setId(result.getInt(1));
                cus.setUname(result.getString(2));
                cus.setPassword(result.getString(3));
                cus.setHomeTown(result.getString(4));
                cus.setPhoneNumber(result.getString(5));

                System.out.println("Customer Exists");

            }
            else
                System.out.println("Customer DOES NOT Exists");

        } catch (SQLException ex) {

            System.out.println("Exception!");
        }
        return cus;
    }

}
