package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class class_room extends AppCompatActivity implements Materialadapter.ClickListener1{
TextView classname,classsection, classcode;

ClassResponse classResponse;
    RecyclerView recycler;
    Materialadapter materialadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
        classname=findViewById(R.id.classname);
        classsection=findViewById(R.id.classsection);
        classcode=findViewById(R.id.clasCode);
        LinearLayout postLayout = (LinearLayout)findViewById(R.id.postLayout);
        if(home.studentId>0) {
            classcode.setVisibility(View.GONE);
            postLayout.setVisibility(View.GONE);
        }


        Intent intent=getIntent();
        if(intent.getExtras()!=null){
            classResponse=(ClassResponse) intent.getSerializableExtra("data");
            classname.setText(classResponse.getName());
            classsection.setText(classResponse.getSection());




            classcode.setText("Class Code: "+ classResponse.getClass_code());
            recycler=findViewById(R.id.recycler);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            recycler.addItemDecoration(new DividerItemDecoration(this,0));
            materialadapter=new Materialadapter(this::onClicklis);
            MaterialRequest materialRequest=new MaterialRequest();
            materialRequest.setId(classResponse.getId());
            Getmaterial(materialRequest);
            //name.setText(loginResponse.getFname()+loginResponse.getLname());

            postLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Snackbar.make(view, "Join Class", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();


                    final Dialog dialog = new Dialog(class_room.this);   // Create custom dialog object
                    dialog.setContentView(R.layout.post_material);       // Include dialog.xml file
                    dialog.show();                                         // Set dialog title dialog.setTitle("Custom Dialog");

                    EditText title =(EditText) dialog.findViewById(R.id.titleId);
                    EditText description =(EditText) dialog.findViewById(R.id.descriptionId);



                    Button btnSubmit = (Button) dialog.findViewById(R.id.submitbtn);

                    btnSubmit.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View arg0) {
                            //Getting the rating and displaying it in the toast

                            PostMaterialRequest postMaterialRequest = new PostMaterialRequest();
                            postMaterialRequest.setTitle(title.getText().toString());
                            postMaterialRequest.setDescription(description.getText().toString());
                            postMaterialRequest.setCid(classResponse.getId());


                            postMaterial(postMaterialRequest);

                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        }

                    });

                }
            });

            Log.e("TAG","---->"+classResponse.getName());
        }
    }

    public void Getmaterial(MaterialRequest materialRequest){
        Call<List<MaterialResponse>> materialResponseCall=ApiClient.getService().Getmaterial(materialRequest);
        materialResponseCall.enqueue(new Callback<List<MaterialResponse>>() {
            @Override
            public void onResponse(Call<List<MaterialResponse>> call, Response<List<MaterialResponse>> response) {
                if(response.isSuccessful()){
                    List<MaterialResponse> materialResponse=response.body();
                    materialadapter.setData(materialResponse);
                    recycler.setAdapter(materialadapter);


                }else{
                    String message="An error occurd please try again later";
                    Toast.makeText(class_room.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<MaterialResponse>>call, Throwable t) {
                String message=t.getLocalizedMessage();
                Toast.makeText(class_room.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }


    public void postMaterial(PostMaterialRequest postMaterialRequest) {
        Call<PostMaterialResponse> postMaterialResponseCall = ApiClient.getService().postMaterial(postMaterialRequest);
        postMaterialResponseCall.enqueue(new Callback<PostMaterialResponse>() {
            @Override
            public void onResponse(Call<PostMaterialResponse> call, Response<PostMaterialResponse> response) {
                if(response.isSuccessful()) {
                    PostMaterialResponse postMaterialResponse = response.body();
                    String message = "Material posted ";
                    Toast.makeText(class_room.this,message, Toast.LENGTH_LONG).show();
                } else {
                    String message = "Can not post Material ";
                    Toast.makeText(class_room.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PostMaterialResponse> call, Throwable t) {
                String message = "Can not Post Material";
                Toast.makeText(class_room.this, message, Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClicklis(MaterialResponse materialResponse) {
        startActivity(new Intent(this,materialView.class).putExtra("data1",materialResponse));
    }
}