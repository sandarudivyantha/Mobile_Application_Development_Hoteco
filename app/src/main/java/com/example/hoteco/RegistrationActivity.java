package com.example.hoteco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.layout_register)
    ScrollView layout;

    @BindView(R.id.txtUserNameRegistration)
    EditText txt_userName;
    @BindView(R.id.txtEmailRegistration)
    EditText txt_email;
    @BindView(R.id.txtPasswordRegistration)
    EditText txt_password;
    @BindView(R.id.txtNicRegistration)
    EditText txt_nic;
    @BindView(R.id.txtAddressRegistration)
    EditText txt_address;
    @BindView(R.id.txtMobileNoRegistration)
    EditText txt_mobileNo;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btnSubmitRegistration)
    void registration() {
        boolean isValid = true;

        String userName = txt_userName.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String nic = txt_nic.getText().toString();
        String mobile = txt_mobileNo.getText().toString();
        String address = txt_address.getText().toString();

        if (userName.isEmpty()) {
            isValid = false;
            txt_userName.setError("User Name Required!");
        }
        if (email.isEmpty()) {
            isValid = false;
            txt_email.setError("Email Required!");
        } else if (!Validator.isValidEmail(email)) {
            isValid = false;
            txt_email.setError("Invalid Email Format!");
        }
        if (password.isEmpty()) {
            isValid = false;
            txt_password.setError("Password Required!");
        } else if (!Validator.isValidPassword(password)) {
            isValid = false;
            txt_password.setError("Password must contains at least 8 digits!");
        }

        if (nic.isEmpty()) {
            isValid = false;
            txt_nic.setError("NIC Required!");
        } else if (!Validator.isValidNIC(nic)) {
            isValid = false;
            txt_nic.setError("Invalid NIC Format!");
        }

        if (mobile.isEmpty()) {
            isValid = false;
            txt_mobileNo.setError("Mobile Number Required!");
        } else if (!Validator.isValidMobile(mobile)) {
            isValid = false;
            txt_mobileNo.setError("Please, Enter 10 digit Mobile Number");
        }

        if (address.isEmpty()) {
            isValid = false;
            txt_email.setError("Address Required!");
        }

        if (isValid) {
            UserDTO userDTO = new UserDTO(userName, email, nic, address, "", mobile);


            DocumentReference docIdRef = db.collection("users").document(email);
            docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Utils.showMessage(layout, "User already exists.");
                        } else {
                            register(email, password, userDTO);
                        }
                    } else {
                        Utils.showMessage(layout, "Registration failed.");
                    }
                }
            });

        }

    }
    void register(String email, String password, UserDTO userDTO) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String,Object> user = null;     //whether user is already existing user
                            try {
                                user = userDTO.getHashMap();
                                db.collection("users").document(email)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(RegistrationActivity.this, "Registration Success.",
                                                        Toast.LENGTH_SHORT).show();

                                                Utils.showMessage(layout,"Registration Success.");

                                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();       //avoid going back
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                                currentUser.delete();                                       //failure->delete user
                                                Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                                        Toast.LENGTH_SHORT).show();
                                                Utils.showMessage(layout,"Registration failed.");
                                            }
                                        });
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                currentUser.delete();                                                    //illegal access->delete user
                                Toast.makeText(RegistrationActivity.this, "Registration failed.",
                                        Toast.LENGTH_SHORT).show();
                                Utils.showMessage(layout,"Registration failed.");
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            Utils.showMessage(layout,"Authentication failed.");
                        }
                    }
                });
    }
}













