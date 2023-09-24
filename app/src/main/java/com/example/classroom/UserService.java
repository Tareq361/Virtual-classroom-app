package com.example.classroom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
@POST("api/signIn/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

@POST("api/register/")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

@POST("api/classList/")
Call<List<ClassResponse>> Userclass(@Body ClassRequest classRequest);

@POST("api/classList/")
    Call<List<ClassResponse>> Userclass(@Body studentClassrequest StudentClassrequest);

@POST("api/material-list/")
Call<List<MaterialResponse>> Getmaterial(@Body MaterialRequest materialRequest);

@POST("api/createClass/")
Call<CreateClassResponse> createClass(@Body CreateClassRequest createClassRequest);

@POST("api/join/")
Call<JoinClassResponse> joinClass(@Body JoinClassRequest joinClassRequest);

@POST("api/material-post/")
Call<PostMaterialResponse> postMaterial(@Body PostMaterialRequest postMaterialRequest);
}
