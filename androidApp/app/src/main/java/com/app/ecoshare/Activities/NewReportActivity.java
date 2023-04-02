package com.app.ecoshare.Activities;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ecoshare.Models.Characteristics;
import com.app.ecoshare.Models.Impact;
import com.app.ecoshare.Models.Report;
import com.app.ecoshare.MyApplication;
import com.app.ecoshare.R;
import com.app.ecoshare.Retrofit.Api;
import com.app.ecoshare.Retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewReportActivity extends AppCompatActivity  {

    MaterialButton myReportsBtn, takePhotoBtn, addReportBtn;
    EditText weight;
    MyApplication application = new MyApplication();
    String token = "Bearer " + application.getUserToken();
    TextView txtLat;
    Integer userId = application.getUserId();
    RadioButton high, low, moderate;
    TextView descriptionText;
    Characteristics characteristics;
    private ImageView photoView;
    Uri imageUri;
    String base64BitMap;
    static Bitmap bitmap;
    private static final int PERMISSION_CODE = 1234;
    private static final int CAPTURE_CODE = 1001;

    RadioButton anonBtn;
    Boolean isAnon;
    TextView categoriesSelected;
    boolean[] selectedCategory;
    ArrayList<Integer> categoriesList = new ArrayList<>();
    List<String> categories = new ArrayList<>();
    String[] categoriesArray = {"Potentially dangerous object", "Potentially lost object",
            "Garbage"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);
        myReportsBtn = findViewById(R.id.myReports);
        anonBtn = findViewById(R.id.anonymous);
        photoView = findViewById(R.id.photoView);
        takePhotoBtn = findViewById(R.id.takePhoto);
        addReportBtn = findViewById(R.id.addReportBtn);
        descriptionText = findViewById(R.id.descriptionText);
        high = findViewById(R.id.high);
        low = findViewById(R.id.low);
        moderate = findViewById(R.id.moderate);
        weight = findViewById(R.id.weight);
        categoriesSelected = findViewById(R.id.categoriesSelected);
        selectedCategory = new boolean[categoriesArray.length];


        takePhotoBtn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
                    requestPermissions(permission, PERMISSION_CODE);
                } else {
                    try {
                        openCamera();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    openCamera();


                } catch (IOException e) {
                    e.printStackTrace();
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


        myReportsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(NewReportActivity.this, UserReportsActivity.class);
            startActivity(intent);
        });

        addReportBtn.setOnClickListener(v -> {
            Api api = RetrofitClient.getRetrofitEcoInstance().create(Api.class);
            for (Integer i : categoriesList) {
                categories.add(categoriesArray[i]);
            }

            if (high.isChecked()) {
                low.setChecked(false);
                moderate.setChecked(false);
                characteristics = new Characteristics(
                        Integer.valueOf(String.valueOf(weight.getText())), Impact.HIGH);

            } else if (moderate.isChecked()) {
                low.setChecked(false);
                high.setChecked(false);
                characteristics = new Characteristics(
                        Integer.valueOf(String.valueOf(weight.getText())), Impact.MODERATE);

            } else if (low.isChecked()) {
                high.setChecked(false);
                moderate.setChecked(false);
                characteristics = new Characteristics(
                        Integer.valueOf(String.valueOf(weight.getText())), Impact.LOW);

            }


            bitmap = decodeUriAsBitmap(NewReportActivity.this, imageUri);
            base64BitMap = encodeImage(bitmap);


            if (anonBtn.isChecked()) {
                isAnon = true;
            } else if (!anonBtn.isChecked()) {
                isAnon = false;
            }

            Report report = new Report(categories, descriptionText.getText().toString(),
                    characteristics, false, isAnon, base64BitMap);
            Log.i("Report", report.getCategory().toString() + report.getDescription() +
                    report.getCharacteristic() + report.getResolve().toString() + report.getAnon().toString()
                    + report.getBitmap());
            Call<Report> call = api.addNewReport(report, token, userId);
            call.enqueue(new Callback<Report>() {
                @Override
                public void onResponse(Call<Report> call, Response<Report> response) {
                    Log.i("RASPUNS", response.toString());
                    Toast.makeText(NewReportActivity.this, "SUCCES!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewReportActivity.this, UserReportsActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Report> call, Throwable t) {
                    Toast.makeText(NewReportActivity.this, "FAILURE!", Toast.LENGTH_SHORT).show();
                }
            });
        });


    }

    private void openCamera() throws IOException {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "new image");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(String.valueOf(imageUri)));
        } catch (Exception e) {
            //handle exception
        }
        Log.i("URI", imageUri.toString());
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(camera, CAPTURE_CODE);

    }

    public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context
                    .getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        openCamera();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_LONG).show();

                }
        }
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        byte[] b = bytes.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            photoView.setImageURI(imageUri);
        }
    }



}
