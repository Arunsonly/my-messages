package com.arun.mymessages;
import android.content.*;import android.app.*;import android.os.*;import android.provider.Telephony;import android.telephony.SmsMessage;import android.graphics.Color;import android.database.Cursor;

public class SmsReceiver extends BroadcastReceiver{
 static final String MARK="com.arun.mymessages.MARK_READ",CLEAR="com.arun.mymessages.CLEAR_NOTIFICATION";
 public void onReceive(Context c,Intent i){
   NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
   if(MARK.equals(i.getAction())){String from=i.getStringExtra("number");if(from!=null)try{ContentValues v=new ContentValues();v.put("read",1);c.getContentResolver().update(Telephony.Sms.CONTENT_URI,v,"address=? AND read=0",new String[]{from});}catch(Exception ignored){}return;}
   if(CLEAR.equals(i.getAction())){nm.cancel(i.getIntExtra("nid",-1));return;}
   if(!Telephony.Sms.Intents.SMS_DELIVER_ACTION.equals(i.getAction()))return;
   SmsMessage[] m=Telephony.Sms.Intents.getMessagesFromIntent(i);if(m==null)return;String from=m.length>0?m[0].getDisplayOriginatingAddress():"SMS";String body="";for(SmsMessage x:m)body+=x.getMessageBody();
   boolean otp=body.matches("(?is).*\\b(otp|one[- ]?time|verification|verify|code)\\b.*\\b\\d{4,8}\\b.*");
   int nid=(int)(System.currentTimeMillis()%100000);if(Build.VERSION.SDK_INT>=26)nm.createNotificationChannel(new NotificationChannel("sms","SMS",NotificationManager.IMPORTANCE_HIGH));
   Notification.Builder n=Build.VERSION.SDK_INT>=26?new Notification.Builder(c,"sms"):new Notification.Builder(c);
   boolean hide=c.getSharedPreferences("settings",0).getBoolean("hidePreview",false);n.setSmallIcon(android.R.drawable.sym_action_email).setContentTitle(otp?"OTP • "+from:from).setContentText(hide?"New message":body).setAutoCancel(true);
   Intent open=new Intent(c,ConversationActivity.class);open.putExtra("number",from);open.putExtra("name",from);n.setContentIntent(PendingIntent.getActivity(c,nid,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE));
   Intent mark=new Intent(c,SmsReceiver.class).setAction(MARK).putExtra("number",from);PendingIntent mp=PendingIntent.getBroadcast(c,nid+1,mark,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
   Intent clear=new Intent(c,SmsReceiver.class).setAction(CLEAR).putExtra("nid",nid);PendingIntent cp=PendingIntent.getBroadcast(c,nid+2,clear,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
   n.addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(c,android.R.drawable.ic_menu_view),"Mark read",mp).build());
   n.addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(c,android.R.drawable.ic_menu_close_clear_cancel),"Clear",cp).build());
   if(Build.VERSION.SDK_INT>=24){android.app.RemoteInput ri=new android.app.RemoteInput.Builder("reply").setLabel("Reply").build();Intent rep=new Intent(c,SmsReplyReceiver.class).putExtra("number",from);PendingIntent rp=PendingIntent.getBroadcast(c,nid+3,rep,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);n.addAction(new Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource(c,android.R.drawable.ic_menu_send),"Reply",rp).addRemoteInput(ri).build());}
   nm.notify(nid,n.build());
 }
}