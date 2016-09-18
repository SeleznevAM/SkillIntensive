package com.example.alex.skillintensive.data.network.interceptors;

import com.example.alex.skillintensive.data.managers.DataManager;
import com.example.alex.skillintensive.data.managers.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Alex on 17.09.2016.
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        PreferenceManager pm = DataManager.getInstance().getPreferenceManager();

        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder().header("X-Access-Token", pm.getAuthToken())
                .header("Request-User-Id", pm.getUserId())
                .header("User-Agent", "SkillIntensiceApp");
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
