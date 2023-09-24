package com.example.classroom;

public class RegisterRequest {

    private String Fname, Lname, Email, Password, Type;

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getuserType() {
        return Type;
    }

    public void setuserType(String type) {
        Type = type;
    }
}