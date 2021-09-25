package com.example.proyek1.services;

import java.util.List;

import com.example.proyek1.models.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {

    @GET("/student/select.php")
    Call<List<Student>> getStudents();

    @FormUrlEncoded
    @POST("/student/insert.php")
    Call<Student> addStudent(
            @Field("nama") String nama,
            @Field("nilai") int nilai);

    @FormUrlEncoded
    @POST("/student/update.php")
    Call<Student> editStudent(
            @Field("id") int id,
            @Field("nama") String nama,
            @Field("nilai") int nilai);

    @FormUrlEncoded
    @POST("/student/delete.php")
    Call<Student> deleteStudent(@Field("id") int id);
}
