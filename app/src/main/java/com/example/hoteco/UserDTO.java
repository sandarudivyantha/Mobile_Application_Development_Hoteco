package com.example.hoteco;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UserDTO {

    public String UserName;
    public String Email;
    public String Nic;
    public String UserAddress;
    public String ProfilePhoto;
    public String MobileNo;

    public UserDTO() {
    }

    public UserDTO(String userName, String email, String nic, String address, String profilePhoto, String mobile) {
        this.UserName = userName;
        Email = email;
        Nic = nic;
        UserAddress = address;
        ProfilePhoto = profilePhoto;
        MobileNo = mobile;
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