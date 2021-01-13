package com.example.TransportationManagement.Entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static android.location.Location.distanceBetween;


@Entity
public class Travel {

    @NonNull
    @PrimaryKey
    private String travelId = "id";
    private String startDate; //
    private String endDate; //
    private String creatingDate; //
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    @TypeConverters(RequestType.class)
    private RequestType status;
    @TypeConverters(ArrayListConverter.class)
    private ArrayList<UserLocation> destinations;
    private UserLocation source;
    private String amountTravelers;


    public RequestType getStatus() {return status;}

    public String getStartDate() {return new String(startDate);}

    public String getEndDate() {return new String(endDate);}

    public String getCreatingDate() {return new String(creatingDate);}

    public ArrayList<UserLocation> getDestinations() {return new ArrayList<>(destinations);}

    public UserLocation getSource() {return source;}

    public HashMap<String, Boolean> getCompany() {return company;}

    public String getAmountTravelers() {return new String(amountTravelers);}

    public String getTravelId() {
        return new String(travelId);
    }

    public String getClientName() {
        return new String(clientName);
    }

    public String getClientPhone() {
        return new String(clientPhone);
    }

    public String getClientEmail() {
        return new String(clientEmail);
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setSource(UserLocation source) {this.source = source;}

    public void setAmountTravelers(String amountTravelers) {this.amountTravelers = amountTravelers;}

    public void setStatus(RequestType status) {this.status = status;}

    public void addDestinations(List dest){
        destinations.addAll(dest);
    }
/*
    public void setStartDate(Date startDate) {
        DateConverter converter = new DateConverter();
        this.startDate = converter.dateToTimestamp(startDate);
    }


    public void setEndDate(Date endDate) {DateConverter converter = new DateConverter();
        this.endDate = converter.dateToTimestamp(endDate);
    }*/

    public long  getSumDays(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        try {
            Date firstDate = sdf.parse(startDate);
            Date secondDate = sdf.parse(endDate);
            long diffInMillies = Math.abs(firstDate.getTime() - secondDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            return diff;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return -1;
    }

    public static class ArrayListConverter {
        @TypeConverter
        public ArrayList<UserLocation> fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            String[] locations = value.split(" ");
            ArrayList<UserLocation> result = new ArrayList<>();
            for (int i = 0; i < locations.length; i += 2){
                UserLocation temp = new UserLocation(Double.parseDouble(locations[i]),Double.parseDouble(locations[i+1]));
                result.add(temp);
            }
            return result;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @TypeConverter
        public String asString(ArrayList<UserLocation> list) {
            if (list == null)
                return null;
            String result = list.stream().map(UserLocation::toString).collect(Collectors.joining(" "));
            return result;
        }
    }
    public float calcKilometers(){
        float[] res = new float[3];
        distanceBetween(source.getLat(),source.getLon(),destinations.get(0).getLat(),destinations.get(0).getLon(),res);
        float ans = res[0];
        for (int i = 0; i < destinations.size() -1; i++){
            UserLocation first = destinations.get(i);
            UserLocation second = destinations.get(i+1);
            distanceBetween(first.getLat(),first.getLon(),second.getLat(),second.getLon(),res);
            ans += res[0];
        }
        return ans/1000;
    }

    //@TypeConverters(RequestType.class)
    //private RequestType requestType;

    //@TypeConverters(DateConverter.class)
    //private Date travelDate;

    //@TypeConverters(DateConverter.class)
    //private Date arrivalDate;


    private HashMap<String, Boolean> company = new HashMap<>();

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

        public void setDestinations(ArrayList<UserLocation> destinations) {
        this.destinations = destinations;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        this.company = company;
    }
    public void setCompany(String key, Boolean confirm){
        company.put(key,confirm);
    }

    public Travel() {
        Date now = new Date();
        DateConverter converter = new DateConverter();
        creatingDate = converter.dateToTimestamp(new Date(now.getTime()));
        destinations = new ArrayList<>();
    }

    public void setTravelId(@NonNull String travelId) {
        this.travelId = travelId;
    }

    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }



    public enum RequestType {
        sent(0), accepted(1), run(2), close(3);
        private final Integer code;
        RequestType(Integer value) {
            this.code = value;
        }
        public Integer getCode() {
            return code;
        }
        @TypeConverter
        public static RequestType getType(Integer numeral) {
            for (RequestType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }
        @TypeConverter
        public static Integer getTypeInt(RequestType requestType) {
            if (requestType != null)
                return requestType.code;
            return null;
        }
    }




    public static class CompanyConverter {
        @TypeConverter
        public HashMap<String, Boolean> fromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*company string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }
}

