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
import java.util.*;

import dbconnection.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static javafx.fxml.FXMLLoader.load;

/**
 * FXML Controller class
 *
 * @author aimih
 */
public class SeachServiceController implements Initializable {

    @FXML
    private ComboBox<String> cbService;
    @FXML
    private ComboBox<Integer> cbSalary;
    @FXML
    private Slider slider;
    @FXML
    private Label lblSlider;

    static String service = "";
    static int salary = 0;
    static int distance = 0;
    @FXML
    private Button btnMeeting;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        boolean flag = false;

        // Fetch List of Service Names from Database
        List <String> servicesNameList = getAllServicesNames();

        // Initialize the Service Combo Box
        cbService.getItems().addAll(servicesNameList);

        // Initialize the Salary
        cbSalary.getItems().addAll(Arrays.asList(1, 2, 3, 4, 5));

    }

    @FXML
    private void moveSlider(MouseEvent event) {
        lblSlider.setText(String.format("%1.0f", slider.getValue()));
    }

    @FXML
    private void btnActionMethod(ActionEvent event) {

        // Check Service Name is Selected
        if (cbService.getSelectionModel().getSelectedIndex() != -1) {

            // Check Salary is Selected
            if (cbSalary.getSelectionModel().getSelectedIndex() != -1) {

                // Check Distance is greater than zero
                if (Integer.parseInt(lblSlider.getText()) != 0) {

                    // Get All from field From Window
                    service = cbService.getSelectionModel().getSelectedItem();
                    salary = cbSalary.getSelectionModel().getSelectedItem();
                    distance = Integer.parseInt(lblSlider.getText());

                    //System.out.println(String.format("SName: %s, Salary: %s, Dis: %s", service, salary, distance));

                    try {

                        Parent root = load((getClass().getResource("/view/MeetingPag.fxml")));
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();

                    } catch (IOException e) {
                        System.out.println(e);
                    }

                } else {
                    new Alert(Alert.AlertType.ERROR, "Distance should be greater than 0").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Select Rate").showAndWait();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Select Service").showAndWait();
        }

    }

    // Get All Services Names from DataBase
    public List<String> getAllServicesNames() {

        Connection con = DataBaseConnection.getDBConnection();

        List <String> allServicesNamesList = new ArrayList(0);

        try {
            PreparedStatement preparedStatement = con.prepareStatement("Select distinct sname from services;");

            ResultSet resultSet = preparedStatement.executeQuery();

            allServicesNamesList = new ArrayList();

            while(resultSet.next()){

                // Add Service Name to List
                allServicesNamesList.add(resultSet.getString("sname"));

            }

        } catch (SQLException ex) {

            System.out.println("Exception!");
        }

        return allServicesNamesList;
    }

}
