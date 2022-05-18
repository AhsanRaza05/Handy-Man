package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dbconnection.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Customer;
import model.ServiceDetails;

/**
 * FXML Controller class
 *
 * @author aimih
 */
public class OfferServiceController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<Customer, String> uNameCol;
    @FXML
    private TableView<Customer> userTable;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPlace;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtPhoneNo;

    List<ServiceDetails> customerList;

    /**
     * Initializes the controller class.
     */
   @Override
    public void initialize(URL url, ResourceBundle rb) {

       txtUsername.setText("");
       txtPlace.setText("");
       txtPhoneNo.setText("");
       txtTime.setText("");

       // Set Columm property to uname
       uNameCol.setCellValueFactory(new PropertyValueFactory<>("uname"));

    }

    @FXML
    private void dtePickerAction(ActionEvent event) {

        boolean flag = false;
        LocalDate ld = datePicker.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = formatter.format(ld);

        // String to Sql Date
        Date meetingDate = Date.valueOf(date);

        // Get All details of Meeting by Date from Data Base
        customerList = getAllDetailByDate(meetingDate);

        // Add the User Name Into User Table
        ObservableList<Customer> cus = FXCollections.observableArrayList();
        Customer c ;

        for(ServiceDetails ser : customerList){

            c = new Customer();
            c.setUname(ser.getUsername());
            cus.add(c);

        }
        userTable.setItems(cus);
    }

    // Get All details of Meeting by Date
    List<ServiceDetails> getAllDetailByDate(Date date){

        Connection con = DataBaseConnection.getDBConnection();
        ResultSet result = null;
        List<ServiceDetails> list = new ArrayList(0);
        ServiceDetails serviceDetails = null;
        Customer cs = null;

        try {

            PreparedStatement preparedStatement = con.prepareStatement("SELECT * \n" +
                    "FROM customers c, servicedetails s\n" +
                   // "WHERE c.`username` = s.`username` AND meetingdate = ? AND c.username = ?");
                    "WHERE c.`username` = s.`username` AND meetingdate = ?");

            preparedStatement.setDate(1, date);
            //       preparedStatement.setString(2, LoginController.userName);


            result = preparedStatement.executeQuery();

            list = new ArrayList();

            while(result.next()){

                cs = new Customer();
                serviceDetails = new ServiceDetails();

                serviceDetails.setMeetingPlace(result.getString("meetingplace"));
                serviceDetails.setMeetingTime(result.getTime("meetingtime"));
                serviceDetails.setUsername(result.getString("username"));

                cs.setPhoneNumber(result.getString("phonenumber"));
                //System.out.println(cs.getPhoneNumber());

                serviceDetails.setCustomer(cs);

                list.add(serviceDetails);
            }


        } catch (SQLException ex) {

            System.out.println("Exception!");
            ex.printStackTrace();
        }

        return list;
    }

    @FXML
    void onSelection(MouseEvent event) {

        Customer c = userTable.getSelectionModel().getSelectedItem();

        //System.out.println(c.getUname());

        for(ServiceDetails ser : customerList){

            //System.out.println(ser.getCustomer().getUname());

            if(userTable.getSelectionModel().getSelectedItem() != null){

                if(ser.getUsername().equals(c.getUname())){

                    //System.out.println(c.getPhoneNumber());
                    txtUsername.setText(c.getUname());
                    txtPhoneNo.setText(ser.getCustomer().getPhoneNumber());
                    txtPlace.setText(ser.getMeetingPlace());
                    txtTime.setText(ser.getMeetingTime().toString());

                }
            }else{new Alert(Alert.AlertType.ERROR, "Select Service First").showAndWait();}
        }

    }


}
