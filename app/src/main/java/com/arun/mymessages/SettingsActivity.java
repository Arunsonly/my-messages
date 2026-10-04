package com.arun.mymessages;
import android.app.*;import android.os.*;import android.content.*;import android.view.*;import android.widget.*;import java.io.*;import java.util.*;
public class SettingsActivity extends Activity{
 LinearLayout root; EditText pin; android.content.SharedPreferences p;
 TextView tv(String s,int z){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(0xff312c51);v.setPadding(20,18,20,18);return v;}
 public void onCreate(Bundle b){super.onCreate(b);p=getSharedPreferences("settings",0);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(16,16,16,16);root.setBackgroundColor(0xfffaf8f4);
 TextView h=tv("My Messages • Settings",22);h.setTextColor(0xffffffff);h.setBackgroundColor(0xff312c51);root.addView(h,new LinearLayout.LayoutParams(-1,70));
 add("Messaging", "Default SMS app: "+(android.os.Build.VERSION.SDK_INT>=29 && getSystemService(android.app.role.RoleManager.class)!=null && getSystemService(android.app.role.RoleManager.class).isRoleHeld(android.app.role.RoleManager.ROLE_SMS)?"Yes":"No"));
 Switch lock=new Switch(this);lock.setText("App Lock (PIN)");lock.setTextSize(17);lock.setChecked(p.getBoolean("lock",false));root.addView(lock);pin=new EditText(this);pin.setHint("4+ digit PIN");pin.setInputType(2);pin.setText(p.getString("pin",""));root.addView(pin);lock.setOnCheckedChangeListener((b,c)->p.edit().putBoolean("lock",c).apply());
 Switch preview=new Switch(this);preview.setText("Hide notification message preview");preview.setTextSize(17);preview.setChecked(p.getBoolean("hidePreview",false));root.addView(preview);preview.setOnCheckedChangeListener((b,c)->p.edit().putBoolean("hidePreview",c).apply());
 Button def=new Button(this);def.setText("Set as default SMS app");root.addView(def);def.setOnClickListener(v->{if(Build.VERSION.SDK_INT>=29){android.app.role.RoleManager r=getSystemService(android.app.role.RoleManager.class);if(r!=null&&r.isRoleAvailable(android.app.role.RoleManager.ROLE_SMS))startActivityForResult(r.createRequestRoleIntent(android.app.role.RoleManager.ROLE_SMS),9);}});
 Button save=new Button(this);save.setText("Save settings");root.addView(save);save.setOnClickListener(v->{p.edit().putString("pin",pin.getText().toString()).apply();Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();});
 Button about=new Button(this);about.setText("About My Messages");root.addView(about);about.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("My Messages").setMessage("Private SMS manager • offline first\nVersion 1.0").setPositiveButton("OK",null).show());setContentView(root);}
 void add(String a,String b){root.addView(tv(a+"\n"+b,16));}
}