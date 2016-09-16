package com.example.alex.skillintensive.data.network.req;

/**
 * Created by POLOUSOV on 15.09.2016.
 */
public class UserLoginReq {
    private String email;
    private String password;

    public UserLoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
