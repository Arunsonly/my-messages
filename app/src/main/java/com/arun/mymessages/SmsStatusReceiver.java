package com.arun.mymessages;
import android.content.*;import android.widget.Toast;
public class SmsStatusReceiver extends BroadcastReceiver{
 public static final String SENT="com.arun.mymessages.SMS_SENT",DELIVERED="com.arun.mymessages.SMS_DELIVERED";
 public void onReceive(Context c,Intent i){String n=i.getStringExtra("number");if(SENT.equals(i.getAction()))Toast.makeText(c,getResultCode()==-1?"SMS sent":"SMS failed",Toast.LENGTH_SHORT).show();else if(DELIVERED.equals(i.getAction()))Toast.makeText(c,"SMS delivered"+(n==null?"":" to "+n),Toast.LENGTH_SHORT).show();}
}
