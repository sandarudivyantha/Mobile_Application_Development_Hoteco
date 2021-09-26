package com.example.hoteco.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class HotelDTO extends SuperDTO  {

    public String Email;
    public String UserName;
    public String Date;
    public String HotelName;
    public String CityName;
    public String Title;
    public double NoOfBeds;
    public double NoOfBath;
    public double BrPrice;
    public double LuPrice;
    public double DinPrice;
    public double WifiPrice;
    public double PoolPrice;
    public double RoomPrice;
    public double TotalPrice;
    public String Address;
    public String Description;

    public String docID;

    public HotelDTO(){

    }

    public HotelDTO(String email, String userName, String date, String hotelName, String cityName,
                    String title, double noOfBeds, double noOfBaths, double brPrice,
                    double luPrice, double dinPrice, double wifiPrice, double poolPrice,
                    double roomPrice, double totalPrice, String description, String address) {
        super();
        Email = email;
        this.UserName = userName;
        Date = date;
        HotelName = hotelName;
        CityName = cityName;
        Title = title;
        NoOfBeds = noOfBeds;
        NoOfBath = noOfBaths;
        BrPrice = brPrice;
        LuPrice = luPrice;
        DinPrice = dinPrice;
        WifiPrice = wifiPrice;
        PoolPrice = poolPrice;
        RoomPrice = roomPrice;
        TotalPrice = totalPrice;
        Address = address;
        Description = description;
    }

    //android to firebase
    public Map<String, Object> getHashMap() throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        for (Field f : getClass().getDeclaredFields()) {
            String name = f.getName();
            Object value = f.get(this);
            map.put(name, value);
        }
        return map;
    }

    public void setDataFromHashMap(Map<String, Object> map) throws IllegalAccessException {
        for (Field field : this.getClass().getFields()) {
            if (map.containsKey(field.getName())) {
                field.set(this, map.get(field.getName()));
            }
        }
    }

    @Override
    public String toString() {
        return "HotelDTO{" +
                "Email='" + Email + '\'' +
                ", UserName='" + UserName + '\'' +
                ", Date=" + Date +'\'' +
                ", HotelName='" + HotelName + '\'' +
                ", CityName='" + CityName + '\'' +
                ", Title='" + Title + '\'' +
                ", NoOfBeds='" + NoOfBeds + '\'' +
                ", NoOfBath='" + NoOfBath + '\'' +
                ", BrPrice='" + BrPrice + '\'' +
                ", LuPrice='" + LuPrice + '\'' +
                ", DinPrice=" + DinPrice +'\'' +
                ", WifiPrice='" + WifiPrice + '\'' +
                ", PoolPrice='" + PoolPrice + '\'' +
                ", RoomPrice='" + RoomPrice + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                ", Address='" + Address + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

}

