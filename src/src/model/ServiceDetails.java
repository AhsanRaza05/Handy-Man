package model;
import java.sql.Date;
import java.sql.Time;

public class ServiceDetails {

    private Integer id;
    private String username;
    private Integer serviceId;
    private String meetingPlace;
    private Date meetingDate;
    private Time meetingTime;
    private Customer c;

    public Customer getCustomer() {
        return c;
    }

    public void setCustomer(Customer c) {
        this.c = c;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public Time getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Time meetingTime) {
        this.meetingTime = meetingTime;
    }
}
