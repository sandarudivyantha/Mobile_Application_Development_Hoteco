package com.example.hoteco.dto;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UserDTO extends SuperDTO {

    public String UserName;
    public String Email;
    public String Nic;
    public String UserAddress;
    public String ProfilePhoto;
    public String MobileNo;

    public UserDTO() {
    }

    public UserDTO(String UserName, String userAddress, String mobileNo)
    {
        this.UserName = UserName;
        UserAddress = userAddress;
        MobileNo = mobileNo;

    }



    //android to firebase
    public Map<String,Object> getHashMap() throws IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        for (Field f : getClass().getDeclaredFields()) {
            String name = f.getName();
            Object value = f.get(this);
            map.put(name,value);
        }
        return map;
    }

    //Write data- Firebase to android
    public void setDataFromHashMap(Map<String,Object> map) throws IllegalAccessException {
        for(Field field : this.getClass().getFields()){
            if(map.containsKey(field.getName())){
                field.set(this,map.get(field.getName()));
            }
        }
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", UserName='" + UserName + '\'' +
                ", Email='" + Email + '\'' +
                ", Nic='" + Nic + '\'' +
                ", UserAddress='" + UserAddress + '\'' +
                ", ProfilePhoto='" + ProfilePhoto + '\'' +
                ", MobileNo='" + MobileNo +
                '}';
    }
}