package com.example.fernandowonder.walale;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class InstanceId extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
       String token = FirebaseInstanceId.getInstance().getToken();
    }
}