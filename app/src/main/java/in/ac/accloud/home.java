package in.ac.accloud;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class home extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button choose, upload;
    ImageView t;
    EditText File_Name;
    TextView Show_upload;
    ProgressBar pb4;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        choose = (Button) findViewById(R.id.choose);
        upload = (Button) findViewById(R.id.upload);
        File_Name = (EditText) findViewById(R.id.File_Name);
        Show_upload = (TextView) findViewById(R.id.Show_upload);
        pb4=(ProgressBar)findViewById(R.id.pb4);
        t = (ImageView) findViewById(R.id.t);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFile();
            }
        });
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Images");

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUploadTask!=null&&mUploadTask.isInProgress()){
                    Toast.makeText(home.this,"Upload In Progress",Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadfile();
                }
            }
        });
        Show_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImg();
            }
        });

    }

    private void showFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            Picasso.with(this).load(filePath).into(t);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadfile(){
        if(filePath!=null){
            StorageReference fileRef=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(filePath));

            mUploadTask=fileRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    pb4.setProgress(0);
                                }
                            },500);
                            Toast.makeText(home.this,"Upload Successful",Toast.LENGTH_LONG).show();
                          /*  Upload u=new Upload(File_Name.getText().toString().trim(),
                                    mStorageRef.getDownloadUrl().toString());
                            String uploadId=mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(u);

                           */
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(home.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            pb4.setVisibility(View.VISIBLE);
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            pb4.setProgress((int)progress);
                        }
                    });
        }
        else{
            Toast.makeText(this,"No File Selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openImg(){
        Intent intent = new Intent(this,ImagesActivity.class);
        startActivity(intent);
    }
}


