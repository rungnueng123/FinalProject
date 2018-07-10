package com.android.orc.ocrapplication.camera;


import android.Manifest;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.orc.cloudvision.CVRequest;
import com.android.orc.cloudvision.CVResponse;
import com.android.orc.cloudvision.CloudVision;
import com.android.orc.ocrapplication.BuildConfig;
import com.android.orc.ocrapplication.R;
import com.android.orc.ocrapplication.result.menuocr.ResultOcrActivity;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Headers;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener, CloudVision.Callback {

    private final static String apiKey = "AIzaSyA7NoRiu-JttOEg2pJVGuw2jEnalNHRDKY";

    public static final int REQUEST_PERMISSION = 200;
    public static final int REQUEST_IMAGE = 100;

    CVRequest.ImageContext.LatLongRect latLongRect;


    CircleProgressBar circleProgressBar;
    Button btnTakePhoto;
    Button btnProcessPhoto;
    ImageView ivPreview;
    Bitmap bitmap;
    String imageFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initInstances();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFileClone();
            }
            catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Uri photoUri = FileProvider.getUriForFile(this, getPackageName() +".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, REQUEST_IMAGE);
        }
    }

    private void initInstances() {

        circleProgressBar = findViewById(R.id.progress_bar);
        circleProgressBar.setColorSchemeResources(android.R.color.holo_orange_light);
        btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnProcessPhoto = findViewById(R.id.btn_process_photo);
        ivPreview = findViewById(R.id.ivPreview);

        btnTakePhoto.setOnClickListener(this);
        btnProcessPhoto.setOnClickListener(this);
    }

    /////////////////////
    // OnClickListener //
    /////////////////////

    @Override
    public void onClick(View view) {
        if (view == btnTakePhoto) {
            try {
                dispatchTakePictureIntent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (view == btnProcessPhoto) {
            circleProgressBar.setVisibility(View.VISIBLE);
            startDetect();
//            Intent intent = new Intent(CameraActivity.this,
//                    ResultActivity.class);


//            Toast.makeText(this, data, Toast.LENGTH_LONG).show();
//            intent.putExtra("BitmapImage", data);
//            startActivity(intent);
        }
    }

    private void startDetect() {
        String data = CloudVision.convertBitmapToBase64String(bitmap);


        CVRequest request = createCVRequest(data);
        CloudVision.runImageDetection(apiKey, request, this);

    }

    ////////////
    // Camera //
    ////////////




        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK
                ) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            getContentResolver().notifyChange(Uri.parse(imageFilePath), null);
            ContentResolver cr = getContentResolver();
            // Show the thumbnail on ImageView
            Uri imageUri = result.getUri();
            File file = new File(imageUri.getPath());

            try {
                InputStream ims = new FileInputStream(file);
                ivPreview.setImageBitmap(BitmapFactory.decodeStream(ims));

                bitmap = MediaStore.Images.Media.getBitmap(cr, imageUri);

            } catch (FileNotFoundException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }

//             ScanFile so it will be appeared on Gallery
            MediaScannerConnection.scanFile(com.android.orc.ocrapplication.camera.CameraActivity.this,
                    new String[]{imageUri.getPath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        CameraActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFileClone();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //Uri photoURI = Uri.fromFile(createImageFile());
                Uri photoURI = FileProvider.getUriForFile(com.android.orc.ocrapplication.camera.CameraActivity.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFileClone());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                CropImage.activity(photoURI).start(this);


                startActivityForResult(takePictureIntent, REQUEST_IMAGE);

            }
        }
    }

    private File createImageFileClone() throws IOException{

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getPath();

        return image;
    }


    @Override
    public void onImageDetectionSuccess(boolean isSuccess, int statusCode, Headers headers, CVResponse cvResponse) {
        setCVResponse(cvResponse);

    }

    @Override
    public void onImageDetectionFailure(Throwable t) {

    }

    private CVRequest createCVRequest(String data) {

        List<String> languageHints = new ArrayList<>();
        languageHints.add("th");

        CVRequest.Image image = new CVRequest.Image(data);
        CVRequest.ImageContext imageContext = new CVRequest.ImageContext(languageHints, latLongRect);
        CVRequest.Feature feature = new CVRequest.Feature(CVRequest.FeatureType.TEXT_DETECTION, 1);
        List<CVRequest.Feature> featureList = new ArrayList<>();
        featureList.add(feature);
        List<CVRequest.Request> requestList = new ArrayList<>();
        requestList.add(new CVRequest.Request(image, imageContext, featureList));
        return new CVRequest(requestList);


    }

    private void setCVResponse(CVResponse cvResponse) {
        if (cvResponse != null && cvResponse.isResponsesAvailable()) {
            CVResponse.Response response = cvResponse.getResponse(0);
            if (response.isTextAvailable()) {
                List<CVResponse.EntityAnnotation> testDao = response.getTexts();
                String data = testDao.get(0).getDescription();
                Intent intent = new Intent(this, ResultOcrActivity.class);
                intent.putExtra("stringRequest", data);
                startActivity(intent);
                circleProgressBar.setVisibility(View.INVISIBLE);

//                textView.setText(testDao.get(0).getDescription());
//                LabelAdapter adapter = new LabelAdapter(response.getTexts());
//                lvLabel.setAdapter(adapter);
//                hideLoading();
            }
        }
        else  {
            Toast.makeText(this, "not found menu", Toast.LENGTH_LONG).show();
            circleProgressBar.setVisibility(View.INVISIBLE);
            finish();
        }
    }
}
