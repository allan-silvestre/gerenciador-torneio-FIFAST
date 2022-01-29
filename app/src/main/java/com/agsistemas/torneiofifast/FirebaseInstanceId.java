package com.agsistemas.torneiofifast;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;


public class FirebaseInstanceId extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
       // String rToken = String.valueOf(FirebaseMessaging.getInstance().getToken());
        Log.d("TAG", "Refreshed token: " + token);

        storeToken(token);

    }

    private void storeToken(String token){
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
    }

}
