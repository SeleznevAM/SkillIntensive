package com.example.alex.skillintensive.data.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.alex.skillintensive.data.network.RestService;
import com.example.alex.skillintensive.data.network.ServiceGenerator;
import com.example.alex.skillintensive.data.network.req.UserLoginReq;
import com.example.alex.skillintensive.data.network.resp.UserListRes;
import com.example.alex.skillintensive.data.network.resp.UserModelResp;

import retrofit2.Call;

public class DataManager {
    private Context mContext;
    private static DataManager INSTANCE = null;
    private PreferenceManager mPreferenceManager;
    private RestService mRestService;

    public DataManager() {
        this.mPreferenceManager = new PreferenceManager();
        this.mRestService = ServiceGenerator.createService(RestService.class);
    }

    public static DataManager getInstance() {
        if(INSTANCE==null){
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public PreferenceManager getPreferenceManager() {
        return mPreferenceManager;
    }

    public Context getContext() {
        return mContext;
    }

    //region ============ NETWORK ==============
        public Call<UserModelResp> loginUser (UserLoginReq userLoginReq){
            return mRestService.loginUser(userLoginReq);
        }

    public Call<UserListRes> getUSerList(){
        return mRestService.getUserList();
    }
    //endregion
}
