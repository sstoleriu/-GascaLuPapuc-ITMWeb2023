package com.app.ecoshare.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.app.ecoshare.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;

public class NewReportActivity extends AppCompatActivity {

    MaterialButton myReportsBtn, takePhotoBtn;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView photoView;
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

        takePhotoBtn.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoView.setImageBitmap(photo);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
