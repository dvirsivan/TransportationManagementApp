package com.example.TransportationManagement.Model;

import java.util.LinkedList;

public class CompanyItem {
    private String source;
    private String numPas;
    private String cName;
    private String startDate;
    private String sumDays;
    private LinkedList<String> destinations = new LinkedList<>();
    private Boolean accepted;

    public CompanyItem(String source, String numPas, String cName, String startDate, String sumDays, LinkedList<String> destinations,Boolean accepted) {
        this.source = source;
        this.numPas = numPas;
        this.cName = cName;
        this.startDate = startDate;
        this.sumDays = sumDays;
        this.destinations = destinations;
        this.accepted = accepted;
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
