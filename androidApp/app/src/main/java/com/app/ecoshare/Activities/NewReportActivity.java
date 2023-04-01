package com.app.ecoshare.Activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecoshare.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;

public class NewReportActivity extends AppCompatActivity {

    MaterialButton myReportsBtn, takePhotoBtn;
    private ImageView photoView;
    Uri imageUri;
    private static final int PERMISSION_CODE = 1234;
    private static final int CAPTURE_CODE = 1001;

    RadioButton anonBtn;
    String isAnon;
    TextView categoriesSelected;
    boolean[] selectedCategory;
    ArrayList<Integer> categoriesList = new ArrayList<>();
    String[] categoriesArray = {"Potentially dangerous object", "Potentially lost object",
    "Garbage"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        myReportsBtn = findViewById(R.id.myReports);
        anonBtn = findViewById(R.id.anonymous);
        photoView = findViewById(R.id.photoView);
        takePhotoBtn = findViewById(R.id.takePhoto);
        categoriesSelected = findViewById(R.id.categoriesSelected);
        selectedCategory = new boolean[categoriesArray.length];

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }

                    else{
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });



        categoriesSelected.setOnClickListener(view -> {
            // Initialize alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(NewReportActivity.this);

            // set title
            builder.setTitle("Select Category");

            // set dialog non cancelable
            builder.setCancelable(false);

            builder.setMultiChoiceItems(categoriesArray, selectedCategory, (dialogInterface, i, b) -> {
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    categoriesList.add(i);
                    // Sort array list
                    Collections.sort(categoriesList);
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    categoriesList.remove(Integer.valueOf(i));
                }
        });

            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                // Initialize string builder
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < categoriesList.size(); j++) {
                    // concat array value
                    stringBuilder.append(categoriesArray[categoriesList.get(j)]);
                    // check condition
                    if (j != categoriesList.size() - 1) {
                        stringBuilder.append(", ");
                    }
                }
                // set text on textView
                categoriesSelected.setText(stringBuilder.toString());
            });

            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                // dismiss dialog
                dialogInterface.dismiss();
            });
            builder.setNeutralButton("Clear All", (dialogInterface, i) -> {
                for (int j = 0; j < selectedCategory.length; j++) {
                    // remove all selection
                    selectedCategory[j] = false;
                    // clear language list
                    categoriesList.clear();
                    // clear text view value
                    categoriesSelected.setText("");
                }
            });

            builder.show();
        }

   );


        if (anonBtn.isChecked()) {
            isAnon = "true";
        } else {
            isAnon = "false";
        }
        myReportsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(NewReportActivity.this, UserReportsActivity.class);
            startActivity(intent);
        });
    }

    private void openCamera(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "new image");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(camera, CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();

                }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            photoView.setImageURI(imageUri);

        }
    }
}
