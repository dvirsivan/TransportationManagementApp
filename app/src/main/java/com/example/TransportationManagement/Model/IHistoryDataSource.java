package com.example.TransportationManagement.Model;

import com.example.TransportationManagement.Entities.Travel;

import java.util.List;

public interface IHistoryDataSource {
    public void addTravel(Travel p);

    public void addTravel(List<Travel> travelList);

    public void editTravel(Travel p);

    public void deleteTravel(Travel p);

    public void clearTable();
}