package com.arun.mymessages;

import android.content.*;
import android.database.Cursor;
import android.provider.Telephony;
import org.json.*;
import java.io.*;
import java.text.*;
import java.util.*;

public final class BackupUtil {
  private BackupUtil(){}
  public static JSONArray readSms(Context c) throws Exception {
    JSONArray a=new JSONArray();
    Cursor cur=c.getContentResolver().query(Telephony.Sms.CONTENT_URI,
      new String[]{"address","body","date","type","read"},null,null,"date ASC");
    if(cur!=null){while(cur.moveToNext()){
      JSONObject o=new JSONObject();
      o.put("address",cur.getString(0)); o.put("body",cur.getString(1));
      o.put("date",cur.getLong(2)); o.put("type",cur.getInt(3)); o.put("read",cur.getInt(4));
      a.put(o);
    }cur.close();}
    return a;
  }
  public static File autoBackup(Context c) throws Exception {
    File dir=new File(c.getFilesDir(),"backups"); if(!dir.exists())dir.mkdirs();
    String day=new SimpleDateFormat("yyyy-MM-dd",Locale.US).format(new Date());
    File f=new File(dir,"backup_"+day+".json");
    FileOutputStream out=new FileOutputStream(f,false);
    out.write(readSms(c).toString(2).getBytes("UTF-8")); out.close(); return f;
  }
  public static String backupFolder(Context c){
    return new File(c.getFilesDir(),"backups").getAbsolutePath();
  }
}
