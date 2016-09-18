package com.example.alex.skillintensive.data.network.resp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 18.09.2016.
 */
public class UserListRes {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("data")
    @Expose
    public List<UserData> data = new ArrayList<UserData>();

    public List<UserData> getData() {
        return data;
    }

    public class UserData {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("second_name")
        @Expose
        private String secondName;
        @SerializedName("__v")
        @Expose
        private int v;
        @SerializedName("repositories")
        @Expose
        private UserModelResp.Repositories repositories;
        @SerializedName("profileValues")
        @Expose
        private UserModelResp.ProfileValues profileValues;
        @SerializedName("publicInfo")
        @Expose
        private UserModelResp.PublicInfo publicInfo;

        public UserModelResp.Repositories getRepositories() {
            return repositories;
        }

        public UserModelResp.ProfileValues getProfileValues() {
            return profileValues;
        }

        public UserModelResp.PublicInfo getPublicInfo() {
            return publicInfo;
        }

        public String getFullName() {
            return firstName + " " + secondName;
        }
    }
}
