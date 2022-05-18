package controller;

import dbconnection.DataBaseConnection;
import filter_service_criteria.AndCriteria;
import filter_service_criteria.CriteriaDistance;
import filter_service_criteria.CriteriaSalary;
import filter_service_criteria.CriteriaServiceName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Service;
import model.ServiceDetails;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.fxml.FXMLLoader.load;

public class MeetingController implements Initializable {

    @FXML
    private Button btnApply;

    @FXML
    private ComboBox<String> cbTime;

    @FXML
    private TableColumn<Service, Integer> disCol;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TableColumn<Service, Integer> salaryCol;

    @FXML
    private TableColumn<Service, String> servCol;

    @FXML
    private TableView<Service> servicesTable;

    @FXML
    private TextField txtPlace;

    @FXML
    void btnActionMethod(ActionEvent event) {

       // System.out.println(dpDate.getValue());

        if(servicesTable.getSelectionModel().getSelectedItem() != null){

            if (!txtPlace.getText().trim().isEmpty()) {

                if (cbTime.getSelectionModel().getSelectedIndex() != -1) {

                    if (dpDate.getValue() != null) {

                        ServiceDetails serDetails = new ServiceDetails();

                        serDetails.setUsername(LoginController.userName);
                        serDetails.setMeetingPlace(txtPlace.getText());
                        serDetails.setServiceId(servicesTable.getSelectionModel().getSelectedItem().getId());

                        //System.out.println(cbTime.getValue());
                        //System.out.println(dpDate.getValue());
                        serDetails.setMeetingTime(Time.valueOf(cbTime.getValue().toString()+":00"));
                        serDetails.setMeetingDate(Date.valueOf(dpDate.getValue().toString()));

                        addServiceDetails(serDetails);

                        try {

                            Parent root = load((getClass().getResource("/view/Login.fxml")));
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();

                        } catch (IOException e) {
                            System.out.println(e);
                        }

                    } else {
                        new Alert(Alert.AlertType.ERROR, "Choose Date").showAndWait();
                    }
                } else {
                    new Alert(Alert.AlertType.ERROR, "Choose Time").showAndWait();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Enter place").showAndWait();
            }

        }else{
            new Alert(Alert.AlertType.ERROR, "Select Service First").showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Specify the Field names to Columns
        disCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        servCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Display the Filtered row in Program
        String sName = SeachServiceController.service;
        Integer dis = SeachServiceController.distance;
        Integer sal = SeachServiceController.salary;

        AndCriteria searchCriteria = new AndCriteria(new CriteriaServiceName(sName), new CriteriaSalary(sal), new CriteriaDistance(dis));
        List<Service> filteredServices = searchCriteria.meetCriteria(getAllServicesDetails());
        servicesTable.setItems(FXCollections.observableArrayList(filteredServices));


        // Initialize the Time
        ObservableList timeList = FXCollections.observableArrayList();

        // Append zeros in Time
        for (int i = 1; i <= 24; i++) {

            timeList.add(String.format("%d:00", i));

        }
        cbTime.setItems(timeList);
    }


    // Get All Services Details from DataBase
    public List<Service> getAllServicesDetails() {

        Connection con = DataBaseConnection.getDBConnection();

        List <Service> allServicesList = new ArrayList();

        Service ser;

        try {
            PreparedStatement preparedStatement = con.prepareStatement("Select * from services;");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                ser = new Service();

                ser.setId(resultSet.getInt("sid"));
                ser.setName(resultSet.getString("sname"));
                ser.setSalary(resultSet.getInt("ssalary"));
                ser.setDistance(resultSet.getInt("sdistance"));

                // Add Service Object to List
                allServicesList.add(ser);
            }

        } catch (SQLException ex) {

            System.out.println("Exception!");
        }

        return allServicesList;
    }

    // ADD Service Details IN DATABASE
    public Integer addServiceDetails(ServiceDetails sd) {

        Connection con = DataBaseConnection.getDBConnection();
        int result = 0;

        try {

            PreparedStatement preparedStatement = con.prepareStatement("insert into servicedetails (username, sid, meetingplace, meetingdate, meetingtime)values(?, ?, ?, ?, ?)");

            preparedStatement.setString(1, sd.getUsername());
            preparedStatement.setInt(2,sd.getServiceId());
            preparedStatement.setString(3,sd.getMeetingPlace());
            preparedStatement.setDate(4,sd.getMeetingDate());
            preparedStatement.setTime(5,sd.getMeetingTime());

            result = preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            System.out.println(" Add Service Details Exception!");
        }

        return result;
    }
}
