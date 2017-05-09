package com.gallery;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.loginpack.LoginActivity;
import com.yeehan.yanboli.photoshare.MainActivity;
import com.yeehan.yanboli.photoshare.R;

public class GalleryActivity extends AppCompatActivity {

    //UI Elements
    private Button mLogoutButton, mUploadImageButton, mBrowseImageButton;
    private ImageView mImageView;
    private EditText mImageName;
    private Uri imgUri;

    //Authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Log
    private static final String TAG = "GalleryActivity";

    //Storage
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    public static final String FB_STORAGE_PATH = "image/";
    public static final String FB_DATABASE_PATH = "image";
    public static final int REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        //UI Elements Assign ID
        mLogoutButton = (Button) findViewById(R.id.logout_button);
        mUploadImageButton = (Button) findViewById(R.id.upload_image_button);
        mBrowseImageButton = (Button) findViewById(R.id.browse_image_button);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageName = (EditText) findViewById(R.id.image_name_field);


        //Storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PATH);


        //Authentication Assign Instances
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mLogoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(GalleryActivity.this, MainActivity.class));
            }

        });
    }

    public void btnBrowse_Click(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_CODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
//            imgUri = data.getData();
//
//            try{
//                Bitmap bm =
//            }
//
//        }
//    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
