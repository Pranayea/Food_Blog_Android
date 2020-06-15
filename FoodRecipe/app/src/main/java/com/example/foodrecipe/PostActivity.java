package com.example.foodrecipe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class PostActivity extends AppCompatActivity {

    //Declaring variables
    private static final int Pick_Image_Request = 1;
    private EditText title,ingredients,procedure;
    private ImageView image;
    private RadioGroup radioGroup;
    private RadioButton radiobtn;
    private Button choose, post;
    private ProgressBar progressBar;
    private Uri imageUri;
    private StorageReference firebaseStorage;
    private DatabaseReference nodePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        //Initializing variables
        title =findViewById(R.id.title);
        ingredients = findViewById(R.id.Ingredients);
        procedure = findViewById(R.id.Procedure);
        radioGroup = findViewById(R.id.radio);
        image =findViewById(R.id.image);
        post = findViewById(R.id.post);
        choose = findViewById(R.id.choose);
        progressBar = findViewById(R.id.progressBar);
        firebaseStorage = FirebaseStorage.getInstance().getReference("posts");
        nodePath = FirebaseDatabase.getInstance().getReference("posts");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postUpload();
            }
        });
    }


    //This method returns the radio button which is being clicked
    public void checked(View v){
        int RadioId = radioGroup.getCheckedRadioButtonId();
        radiobtn = findViewById(RadioId);
    }

    //This sees the images in the device
    private void openImageChooser(){
        Intent chooseImage = new Intent();
        chooseImage.setType("image/*");
        chooseImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(chooseImage,Pick_Image_Request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Gets image from device and sets it in Image view with help of Picasso library
        if(requestCode == Pick_Image_Request && resultCode == RESULT_OK && data !=null
                && data.getData()!=null){
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(image);
        }
    }

    //This method is used to get the file extension
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //this method is used after pst button is clicked
    private void postUpload(){
        //Getting all values from the form
        final String rTitle = title.getText().toString();
        final String rIngredients = ingredients.getText().toString();
        final String rProcedure = procedure.getText().toString();
        final String rCategory = radiobtn.getText().toString();

        if(imageUri != null){
                //Setting image to Firebase Storage with name as the time at which it was uploaded
                StorageReference filePath = firebaseStorage.
                        child(System.currentTimeMillis()+"."+getFileExtension(imageUri));

                filePath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.P)
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //Setting value of progress bar to show process
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setProgress(0);
                            }
                        },4000);

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();//Getting url of image in storage
                        while (!uriTask.isSuccessful());
                            Uri downloadUrl = uriTask.getResult();
                        Post post = new Post(rTitle,rIngredients,rProcedure,rCategory,downloadUrl.toString());//Passing the values as an object
                        String postId = nodePath.push().getKey();
                        nodePath.child(postId).setValue(post);// values set in the realtime database

                        startActivity(new Intent(PostActivity.this,AdminActivity.class));
                        Toast.makeText(PostActivity.this,"Upload Success",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);
                    }
                });

        }else{
            Toast.makeText(this,"Please Choose a Picture",Toast.LENGTH_SHORT).show();
        }
    }
}
