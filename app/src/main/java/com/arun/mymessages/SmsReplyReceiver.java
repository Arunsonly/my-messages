package com.arun.mymessages;
import android.content.*;import android.app.*;import android.telephony.SmsManager;import android.os.*;import android.widget.Toast;
public class SmsReplyReceiver extends BroadcastReceiver{
 public void onReceive(Context c,Intent i){String number=i.getStringExtra("number");android.os.Bundle results=android.app.RemoteInput.getResultsFromIntent(i);if(number==null||results==null)return;CharSequence msg=results.getCharSequence("reply");if(msg==null)return;try{SmsManager.getDefault().sendTextMessage(number,null,msg.toString(),null,null);Toast.makeText(c,"Reply sent",Toast.LENGTH_SHORT).show();}catch(Exception e){Toast.makeText(c,"Reply failed",Toast.LENGTH_SHORT).show();}}
}