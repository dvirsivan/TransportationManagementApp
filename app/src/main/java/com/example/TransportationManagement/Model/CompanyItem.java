package com.example.TransportationManagement.Model;

import com.example.TransportationManagement.Entities.UserLocation;

import java.util.LinkedList;

public class CompanyItem {
    private String source;
    private String numPas;
    private String cName;
    private String startDate;
    private String sumDays;
    private LinkedList<String> destinations = new LinkedList<>();
    private Boolean accepted;



    private String phone;

    public CompanyItem(UserLocation source, String numPas, String cName, String startDate, String sumDays, String phone, LinkedList<UserLocation> destinations, Boolean accepted) {
        this.source = source.toString();
        this.numPas = numPas;
        this.cName = cName;
        this.startDate = startDate;
        this.sumDays = sumDays;
        this.phone=phone;
        this.accepted = accepted;
        for (UserLocation userLocation : destinations){
            this.destinations.add(userLocation.toString());
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNumPas() {
        return numPas;
    }

    public void setNumPas(String numPas) {
        this.numPas = numPas;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getSumDays() {
        return sumDays;
    }

    public void setSumDays(String sumDays) {
        this.sumDays = sumDays;
    }

    public String getPhone() { return phone; }

    public LinkedList<String> getDestinations() {
        return destinations;
    }

    public void setDestinations(LinkedList<String> destinations) {
        this.destinations = destinations;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
