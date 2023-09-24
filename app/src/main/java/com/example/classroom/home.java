package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home extends AppCompatActivity implements ClassAdapter.ClickListener{
    LoginResponse loginResponse;
    Toolbar toolbar;
    RecyclerView recycler;
    ClassAdapter classAdapter;
    CircularImageView profilePic;

    public static int teacherId, studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar=findViewById(R.id.toolbar);
        recycler=findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.addItemDecoration(new DividerItemDecoration(this,0));
        classAdapter=new ClassAdapter(this::onClicklis);

        profilePic=findViewById(R.id.profilePic);

        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            loginResponse=(LoginResponse) intent.getSerializableExtra("data");
            //name.setText(loginResponse.getFname()+loginResponse.getLname());
            Log.e("TAG","---->"+loginResponse.getEmail());
//            profilePic.setImageURI(Uri.parse("http://192.168.0.100:8000/media/"+loginResponse.getImage()));
            String photo_url_str="http://192.168.0.106:3000"+loginResponse.getImage();
            Picasso.get()
                    .load(photo_url_str)
                    .into(profilePic);

            if(loginResponse.getAccountType().toString().equalsIgnoreCase("Student")){

                studentClassrequest StudentClassrequest=new studentClassrequest();
                StudentClassrequest.setStudent_id(loginResponse.getId());
                Userclass(StudentClassrequest);

                studentId=loginResponse.getId();

                // ******** need to care about ***********
                FloatingActionButton fab = findViewById(R.id.add_btn);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Snackbar.make(view, "Join Class", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();


                        final Dialog dialog = new Dialog(home.this);   // Create custom dialog object
                        dialog.setContentView(R.layout.join_classview);       // Include dialog.xml file
                        dialog.show();                                         // Set dialog title dialog.setTitle("Custom Dialog");

                        EditText classCode =(EditText) dialog.findViewById(R.id.classcodeId);


                        Button btnSubmit = (Button) dialog.findViewById(R.id.submitbtn);

                        btnSubmit.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                //Getting the rating and displaying it in the toast

                                JoinClassRequest joinClassRequest = new JoinClassRequest();
                                joinClassRequest.setCode(classCode.getText().toString());
                                joinClassRequest.setStudent_id(loginResponse.getId());

                                joinClass(joinClassRequest);

                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());
                            }

                        });
                        Button btnClose = (Button) dialog.findViewById(R.id.cancelbtn);
                        // if the button is clicked, close the custom dialog box
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Close dialog
                                dialog.dismiss();
                            }
                        });

                    }
                });

            }
            else{
//            EditText name, sectionno, subject, room;

                ClassRequest classRequest=new ClassRequest();
                classRequest.setTeacher_id(loginResponse.getId());
                Userclass(classRequest);

                studentId =0;

                FloatingActionButton fab = findViewById(R.id.add_btn);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    Snackbar.make(view, "Create Class", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();


                        final Dialog dialog = new Dialog(home.this);   // Create custom dialog object
                        dialog.setContentView(R.layout.add_classview);       // Include dialog.xml file
                        dialog.show();                                         // Set dialog title dialog.setTitle("Custom Dialog");

                        EditText name =(EditText) dialog.findViewById(R.id.classname);
                        EditText sectionno =(EditText)  dialog.findViewById(R.id.sectionno);
                        EditText subject = (EditText) dialog.findViewById(R.id.subjectId);
                        EditText room = (EditText) dialog.findViewById(R.id.roomId);



                        Button btnSubmit = (Button) dialog.findViewById(R.id.submitbtn);
                        //Performing action on Button Click
                        btnSubmit.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                CreateClassRequest createClassRequest = new CreateClassRequest();

                                createClassRequest.setClass_name(name.getText().toString());
                                createClassRequest.setSec(sectionno.getText().toString());
                                createClassRequest.setSub(subject.getText().toString());
                                createClassRequest.setRoom(room.getText().toString());
                                createClassRequest.setTeacher_id(loginResponse.getId());

                                createClass(createClassRequest);


                                //Getting the rating and displaying it in the toast
//                            Toast.makeText(getApplicationContext(), "Class Created Successfully", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                                startActivity(getIntent());
                            }

                        });

                        Button btnClose = (Button) dialog.findViewById(R.id.cancelbtn);
                        // if the button is clicked, close the custom dialog box
                        btnClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Close dialog
                                dialog.dismiss();
                            }
                        });


                    }
                });
            }
        }
    }
    public void Userclass(ClassRequest classRequest){
        Call<List<ClassResponse>> classResponseCall=ApiClient.getService().Userclass(classRequest);
        classResponseCall.enqueue(new Callback<List<ClassResponse>>() {
            @Override
            public void onResponse(Call<List<ClassResponse>> call, Response<List<ClassResponse>>response) {
                if(response.isSuccessful()){
                    List<ClassResponse> classResponse=response.body();
                    classAdapter.setData(classResponse);
                    recycler.setAdapter(classAdapter);


                }else{
                    String message="An error occurd please try again later";
                    Toast.makeText(home.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClassResponse>>call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(home.this,message,Toast.LENGTH_LONG).show();
            }
        });



    }
    public void Userclass(studentClassrequest classRequest){
        Call<List<ClassResponse>> classResponseCall=ApiClient.getService().Userclass(classRequest);
        classResponseCall.enqueue(new Callback<List<ClassResponse>>() {
            @Override
            public void onResponse(Call<List<ClassResponse>> call, Response<List<ClassResponse>>response) {
                if(response.isSuccessful()){
                    List<ClassResponse> classResponse=response.body();
                    classAdapter.setData(classResponse);
                    recycler.setAdapter(classAdapter);


                }else{
                    String message="An error occured please try again later";
                    Toast.makeText(home.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClassResponse>>call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(home.this,message,Toast.LENGTH_LONG).show();
            }
        });

    }

    // For creating a class activity
    public void createClass(CreateClassRequest createClassRequest) {
        Call<CreateClassResponse> createClassResponseCall = ApiClient.getService().createClass(createClassRequest);
        createClassResponseCall.enqueue(new Callback<CreateClassResponse>() {
            @Override
            public void onResponse(Call<CreateClassResponse> call, Response<CreateClassResponse> response) {
                if(response.isSuccessful()) {
                    CreateClassResponse createClassResponse= response.body();
                    String message = "Class Created";
                    Toast.makeText(home.this, message, Toast.LENGTH_LONG).show();
                } else {
                    String message = "Can not Create Class";
                    Toast.makeText(home.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<CreateClassResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(home.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void joinClass(JoinClassRequest joinClassRequest) {
        Call<JoinClassResponse> joinClassResponseCall = ApiClient.getService().joinClass(joinClassRequest);
        joinClassResponseCall.enqueue(new Callback<JoinClassResponse>() {
            @Override
            public void onResponse(Call<JoinClassResponse> call, Response<JoinClassResponse> response) {
                if(response.isSuccessful()) {
                    JoinClassResponse joinClassResponse = response.body();
                    String message = "Joined In Class";
                    Toast.makeText(home.this, message, Toast.LENGTH_LONG).show();
                } else {
                    String message = "Can not Join in Class";
                    Toast.makeText(home.this, message, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<JoinClassResponse> call, Throwable t) {

                String message = "Class Not Found";
                Toast.makeText(home.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClicklis(ClassResponse classResponse) {
        startActivity(new Intent(this,class_room.class).putExtra("data",classResponse));
    }
}