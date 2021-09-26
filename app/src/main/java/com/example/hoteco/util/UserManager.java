package com.example.hoteco.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hoteco.dto.UserDTO;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class UserManager {

    //user privileges
    private static SharedPreferences userPreferences;
    private static Context context;
    private static Gson gson;
    private static UserManager userManager = null;

    private UserManager() {
    }

    public static UserManager getInstance(Context _context) {
        if(userManager == null) {
            userManager = new UserManager();
            context = _context;
            gson = new Gson();
            userPreferences = context.getSharedPreferences(AppConstants.USER_DATA, MODE_PRIVATE);
        }
        return userManager;
    }

    public UserDTO getUser() {
        String user_details = userPreferences.getString(AppConstants.USER_DATA, null);
        if (null != user_details)
            return gson.fromJson(user_details, UserDTO.class);
        else
            throw new RuntimeException("------------------------> UserDTO object not made when Logging in");
    }

    public void logInUser(UserDTO userDTO) {
        String s = gson.toJson(userDTO);
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.putString(AppConstants.USER_DATA, s);
        edit.apply();
    }

    public void clearAllData() {
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.clear();
        edit.apply();
    }

    public void setAction(String action){
        SharedPreferences.Editor edit = userPreferences.edit();
        edit.putString(AppConstants.USER_ACTION, action);
        edit.apply();
    }

    public String getAction(){
        String user_action = userPreferences.getString(AppConstants.USER_ACTION, null);
        return user_action ;
    }
}
