package com.focuslabs.mekuanent.backgroundjobs;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;


public class IntentServiceDemo extends IntentService {

public static final String PARAM = "what";


    public IntentServiceDemo() {
        super("IntentServiceDemo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
//            final String param = intent.getStringExtra(PARAM);
//            Toast.makeText(this,param,Toast.LENGTH_LONG).show();
//        }
//        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
    }

}
