package com.example.lab56;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static  final  int MY_CAMERA_PERMISSION_CODE=100;
    private static final int CAMERA_REQUEST = 1000;
    private static final int REQUEST_CODE_IMAGE = 1;
    private static final int REQUEST_IMAGE_OPEN = 99;
    EditText edtName;
    ImageView imghinh;
    product product;
    ArrayList<product> productArrayList;
    productAdapter productAdapter;
    RecyclerView recyclerView;
    Uri urlImg,full;
    Button btnLoad,btnSave;
    DatabaseReference mData;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Intent service;
    Button btnStar;
    Button btnStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        service=new Intent(this, MyService.class);

        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Khời tạo Service", Toast.LENGTH_SHORT).show();
                MainActivity.this.startService(service);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Tắt Service", Toast.LENGTH_SHORT).show();
                MainActivity.this.stopService(service);
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,REQUEST_IMAGE_OPEN);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID uuid=UUID.randomUUID();
                String name=edtName.getText().toString();
                StorageReference storageRef=storage.getReference();
                StorageReference mountainsRef=storageRef
                        .child((new Date()).getTime()+".jpg");
                UploadTask uploadTask=mountainsRef.putFile(full);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "khong up dc hinh anh", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urlImg=uri;
                                database=FirebaseDatabase
                                        .getInstance("https://fir-d0684-default-rtdb.asia-southeast1.firebasedatabase.app/");
                                mData=database.getReference("Images");
                                product product=new product(uuid.toString(),name,urlImg.toString());

                                mData.child(product.getId()).setValue(product);
                            }
                        });
                        Toast.makeText(MainActivity.this, "update file thanh cong", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        loadData();
        productArrayList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter=new productAdapter(this, productArrayList );

        recyclerView.setAdapter(productAdapter);
    }
    private void loadData(){
        mData=FirebaseDatabase.getInstance("https://fir-d0684-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Images");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                product mProduct=snapshot.getValue(product.class);

                if (mProduct!= null){
                    productArrayList
                            .add(new product(mProduct.getId()
                                    ,mProduct.getTenProduct()

                                    ,mProduct.getAnhProduct()));

                    productAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void anhXa(){
        storage = FirebaseStorage.getInstance("gs://fir-d0684.appspot.com/");
        edtName=findViewById(R.id.edtTenHinh);
        imghinh=findViewById(R.id.imgChoose);
        btnLoad=findViewById(R.id.btnLoad);
        btnSave=findViewById(R.id.btnSave);
        recyclerView=findViewById(R.id.lvha);
        btnStar=findViewById(R.id.btnStart);
        btnStop=findViewById(R.id.btnStop);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==MY_CAMERA_PERMISSION_CODE){
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_SHORT).show();
                Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Bundle bundle=new Bundle();
                startActivityForResult(cameraIntent,CAMERA_REQUEST,bundle);
            }else {
                Toast.makeText(this, "camera permisson denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==CAMERA_REQUEST&& requestCode== Activity.RESULT_OK){
//            photo = (Bitmap) data.getExtras().get("data");
////            Toast.makeText(this, photo + "", Toast.LENGTH_SHORT).show();
////            imgView.setImageBitmap(photo);
////            data.getData();
////            Toast.makeText(this,   data.getData()+ "", Toast.LENGTH_SHORT).show();
        }
        if (requestCode==REQUEST_IMAGE_OPEN
                && resultCode==RESULT_OK
                && data != null && data.getData() !=null){
            full=data.getData();
            Toast.makeText(this, full+"", Toast.LENGTH_SHORT).show();
            imghinh.setImageURI(full);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}