package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton userType;
    Button btnSignUp;
    TextView textViewLogin;
    EditText textInputEditTextPassword, textInputEditTextFname, textInputEditTextLname, textInputEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        textInputEditTextFname = findViewById(R.id.editTextTextPersonName);
        textInputEditTextLname = findViewById(R.id.editTextTextPersonName2);
        textInputEditTextEmail = findViewById(R.id.editTextTextEmailAddress);
        textInputEditTextPassword = findViewById(R.id.editTextTextPassword);
        btnSignUp = findViewById(R.id.signup);

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioId=radioGroup.getCheckedRadioButtonId();
                userType= (RadioButton) findViewById(radioId);

               /* if(TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ){

                    String message = "All Fields must be Filled !";
                    Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();

                } else {*/

                RegisterRequest registerRequest =  new RegisterRequest();
                registerRequest.setFname(textInputEditTextFname.getText().toString());
                registerRequest.setLname(textInputEditTextLname.getText().toString());
                registerRequest.setEmail(textInputEditTextEmail.getText().toString());
                registerRequest.setPassword(textInputEditTextPassword.getText().toString());
                registerRequest.setuserType(userType.getText().toString());



                registerUser(registerRequest);

                //}
            }
        });

    }

    public void registerUser(RegisterRequest registerRequest){
        Call<RegisterResponse> registerResponseCall = ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    startActivity(new Intent(register.this, MainActivity.class).putExtra("data", registerResponse));
                    finish();


                } else {
                    String message = "An Error Occurred!";
                    Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                String message = t.getLocalizedMessage();
                Toast.makeText(register.this, message, Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void goTologin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}