package com.example.hoteco;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String,Object> getHashMapFromInstance(SuperDTO superDTO, Class _class) {
        Map<String, Object> dict = new HashMap<>();
        Field[] allFields = _class.getDeclaredFields();
        for (Field field : allFields) {
            try {
                Object value = field.get(superDTO);
                dict.put(field.getName(), value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return dict;
    }

    public static void showMessage(RelativeLayout layout, String message)
    {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showMessage(ConstraintLayout layout, String message)
    {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showMessage(ScrollView layout, String message)
    {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void showMessage(DrawerLayout layout, String message)
    {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void logOutUser(Context context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}
