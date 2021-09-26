package com.example.hoteco.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.hoteco.MainActivity;
import com.example.hoteco.R;
import com.example.hoteco.dto.HotelDTO;
import com.example.hoteco.dto.UserDTO;
import com.example.hoteco.util.UserManager;
import com.example.hoteco.util.Utils;
import com.example.hoteco.util.Validator;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNewHotelActivity extends AppCompatActivity {

    @BindView(R.id.editDate)
    EditText txt_date;

    EditText date;
    DatePickerDialog datePickerDialog;

    @BindView(R.id.txtUserNameAddHotel)
    TextView add_userName;
    @BindView(R.id.edit_txt_hotelName)
    TextView add_hotelName;
    @BindView(R.id.edit_txt_enterCity)
    TextView add_cityName;
    @BindView(R.id.edit_txt_title)
    TextView add_Title;
    @BindView(R.id.editTxt_bed_addHotel)
    TextView add_noOfBeds;
    @BindView(R.id.editTxt_bath_addHotel)
    TextView add_noOfBaths;
    @BindView(R.id.editTxt_phone_addHotel)
    TextView add_phone;
    @BindView(R.id.editBreakfastPrice)
    TextView add_brPrice;
    @BindView(R.id.editAddLunchPrice)
    TextView add_luPrice;
    @BindView(R.id.editDinnerPrice)
    TextView add_dinPrice;
    @BindView(R.id.editAddWifiPrice)
    TextView add_wifiPrice;
    @BindView(R.id.editPoolPrice)
    TextView add_poolPrice;
    @BindView(R.id.editAddRoomPrice)
    TextView add_roomPrice;
    @BindView(R.id.editTxt_totalPrice_addHotel)
    TextView add_totalPrice;
    @BindView(R.id.editTxt_address_addHotel)
    TextView add_address;
    @BindView(R.id.editTxt_description_addHotel)
    TextView add_description;

    @BindView(R.id.addHotelLayout)
    ScrollView layout;
    private Uri filePath;
    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    @BindView(R.id.imageButton)
    ImageView img;
    private boolean isAddedPhoto = false;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private UserManager userManager;
    private FirebaseStorage storage;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hotel);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add New Hotel");
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userManager = UserManager.getInstance(this);
        storage = FirebaseStorage.getInstance();

        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.editDate);

        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddNewHotelActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        // set Item name
        userName = getIntent().getStringExtra("USER");
        add_userName.setText(userName);

    }

    @OnClick(R.id.btn_save_hotel)
    void AddNewHotelActivity() {

        boolean isValid = true;

        String date = txt_date.getText().toString();
        String hotelName = add_hotelName.getText().toString();
        String cityName = add_cityName.getText().toString();
        String title = add_Title.getText().toString();
        String noOfBeds = add_noOfBeds.getText().toString();
        String noOfBaths = add_noOfBaths.getText().toString();
        String phone = add_phone.getText().toString();
        String brPrice = add_brPrice.getText().toString();
        String luPrice = add_luPrice.getText().toString();
        String dinPrice = add_dinPrice.getText().toString();
        String wifiPrice = add_wifiPrice.getText().toString();
        String poolPrice = add_poolPrice.getText().toString();
        String roomPrice = add_roomPrice.getText().toString();
        String totalPrice = add_totalPrice.getText().toString();
        String address = add_address.getText().toString();
        String description = add_description.getText().toString();

        if(date.isEmpty()) {
            isValid = false;
            txt_date.setError("Please enter date!");
        }
        if(hotelName.isEmpty()) {
            isValid = false;
            add_hotelName.setError("Please enter Hotel Name!");
        }
        if(cityName.isEmpty()) {
            isValid = false;
            add_cityName.setError("Please enter City!");
        }
        if(noOfBeds.isEmpty()) {
            isValid = false;
            add_noOfBeds.setError("Please enter No of Beds!");
        }
        if(noOfBaths.isEmpty()) {
            isValid = false;
            add_noOfBaths.setError("Please enter No of Bathrooms!");
        }
        if (phone.isEmpty()) {
            isValid = false;
            add_phone.setError("Please enter Telephone Number!");
        } else if (!Validator.isValidMobile(phone)) {
            isValid = false;
            add_phone.setError("Please, Enter 10 digit Mobile Number");
        }
        if(address.isEmpty()) {
            isValid = false;
            add_address.setError("Please enter Address!");
        }

        if(isValid) {

            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Please wait...");
            progressDialog.show();


            UserDTO user = userManager.getUser();

            HotelDTO hotel = new HotelDTO(user.Email,user.UserName,date,hotelName,cityName,title,
                    Double.parseDouble(noOfBeds), Double.parseDouble(noOfBaths), Double.parseDouble(brPrice),
                    Double.parseDouble(luPrice),Double.parseDouble(dinPrice),Double.parseDouble(wifiPrice),
                    Double.parseDouble(poolPrice),Double.parseDouble(roomPrice),Double.parseDouble(totalPrice),
                    description,address);
            hotel.Date = new Date().toString();
            hotel.docID = "";

            Map<String,Object> data = null;
            try {
                data = hotel.getHashMap();
                db.collection("hotel")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                String docID = documentReference.getId();
                                if(isAddedPhoto) {
                                    StorageReference ref = storage.getReference().child("hotel/" + docID);
                                    ref.putFile(filePath)
                                            .addOnSuccessListener(
                                                    new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                                        {
                                                            progressDialog.dismiss();
                                                        }
                                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e)
                                                {
                                                    progressDialog.dismiss();
                                                }
                                            });
                                    Utils.showMessage(layout,"Added your Hotel!");
                                    goToMain();
                                } else {
                                    progressDialog.dismiss();
                                    Utils.showMessage(layout,"Added your Hotel!");
                                    goToMain();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Utils.showMessage(layout,"Failed to add your Hotel and Try again!");
                            }
                        });
            } catch (IllegalAccessException e) {
                progressDialog.dismiss();
                Utils.showMessage(layout,"Failed to add your Hotel and Try again!");
            }
        }
    }

    void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick(R.id.imageButton)
    void SelectImage()
    {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."),PICK_IMAGE_REQUEST);
    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode, data);
        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                img.setImageBitmap(bitmap);
                isAddedPhoto = true;
            }
            catch (IOException e) {
                isAddedPhoto = false;
            }
        }
    }

}


