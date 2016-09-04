package com.qoobico.emergencybutton.adapter;

/**
 * Created by user on 03.09.2016.
 */
public class Contact {
    String title;
    String tel;
    String email;

    public Contact(String name, String tel, String email) {
        this.title = name;
        this.tel = tel;
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}