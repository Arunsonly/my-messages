package com.arun.mymessages;
import android.app.*;import android.app.role.RoleManager;import android.os.*;import android.content.*;import android.provider.Settings;import android.view.*;import android.widget.*;import java.util.*;

public class SettingsActivity extends Activity{
 LinearLayout root;EditText pin;SharedPreferences p;int primary=0xff312c51;
 TextView tv(String s,int z){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(p.getBoolean("dark",false)?0xfff5f2fa:0xff312c51);v.setPadding(20,14,20,14);return v;}
 public void onCreate(Bundle b){super.onCreate(b);p=getSharedPreferences("settings",0);build();}
 void build(){boolean dark=p.getBoolean("dark",false);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(12,12,12,12);root.setBackgroundColor(dark?0xff18161f:0xfffaf8f4);
 TextView h=tv("My Messages • Settings",22);h.setTextColor(0xffffffff);h.setBackgroundColor(primary);root.addView(h,new LinearLayout.LayoutParams(-1,70));
 add("Messaging","Default SMS app: "+isDefaultSms());
 Switch delivery=new Switch(this);delivery.setText("Delivery reports");delivery.setChecked(p.getBoolean("deliveryReports",true));root.addView(delivery);delivery.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("deliveryReports",checked).apply());
 Switch enter=new Switch(this);enter.setText("Send with Enter");enter.setChecked(p.getBoolean("sendWithEnter",false));root.addView(enter);enter.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("sendWithEnter",checked).apply());
 Button sim=new Button(this);sim.setText("SIM preference: "+p.getString("simPreference","Ask"));root.addView(sim);sim.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("SIM preference").setSingleChoiceItems(new String[]{"Ask every time","SIM 1","SIM 2","System default"},simIndex(p.getString("simPreference","Ask")),(d,w)->{String[] x={"Ask","SIM 1","SIM 2","Default"};p.edit().putString("simPreference",x[w]).apply();d.dismiss();build();}).show());
 add("Notifications","Choose sound/vibration and whether previews are visible.");
 Switch sound=new Switch(this);sound.setText("Notification sound");sound.setChecked(p.getBoolean("sound",true));root.addView(sound);sound.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("sound",checked).apply());
 Switch vibrate=new Switch(this);vibrate.setText("Notification vibration");vibrate.setChecked(p.getBoolean("vibrate",true));root.addView(vibrate);vibrate.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("vibrate",checked).apply());
 Switch preview=new Switch(this);preview.setText("Hide notification message preview");preview.setChecked(p.getBoolean("hidePreview",false));root.addView(preview);preview.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("hidePreview",checked).apply());
 add("Privacy","App lock and biometric protection.");
 Switch lock=new Switch(this);lock.setText("App Lock (PIN)");lock.setChecked(p.getBoolean("lock",false));root.addView(lock);pin=new EditText(this);pin.setHint("4+ digit PIN");pin.setInputType(2);pin.setText(p.getString("pin",""));root.addView(pin);lock.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("lock",checked).apply());
 if(Build.VERSION.SDK_INT>=28){Switch bio=new Switch(this);bio.setText("Fingerprint / Biometric unlock");bio.setChecked(p.getBoolean("biometric",false));root.addView(bio);bio.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("biometric",checked).apply());}
 add("Appearance","Premium purple accent • light/dark mode.");
 Switch darkMode=new Switch(this);darkMode.setText("Dark mode");darkMode.setChecked(dark);root.addView(darkMode);darkMode.setOnCheckedChangeListener((button,checked)->{p.edit().putBoolean("dark",checked).apply();build();});
 add("Backup","Local automatic backup is stored inside the app; manual backup is available from Backup & Restore.");
 Switch auto=new Switch(this);auto.setText("Automatic daily backup");auto.setChecked(p.getBoolean("autoBackup",false));root.addView(auto);auto.setOnCheckedChangeListener((button,checked)->p.edit().putBoolean("autoBackup",checked).apply());
 Button backup=new Button(this);backup.setText("Open Backup & Restore");root.addView(backup);backup.setOnClickListener(v->startActivity(new Intent(this,BackupActivity.class)));
 Button def=new Button(this);def.setText("Set as default SMS app");root.addView(def);def.setOnClickListener(v->{if(Build.VERSION.SDK_INT>=29){RoleManager r=getSystemService(RoleManager.class);if(r!=null&&r.isRoleAvailable(RoleManager.ROLE_SMS))startActivityForResult(r.createRequestRoleIntent(RoleManager.ROLE_SMS),9);}else{try{startActivity(new Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS));}catch(Exception ignored){}}});
 Button save=new Button(this);save.setText("Save settings");root.addView(save);save.setOnClickListener(v->{p.edit().putString("pin",pin.getText().toString()).apply();Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show();});
 Button about=new Button(this);about.setText("About My Messages");root.addView(about);about.setOnClickListener(v->new AlertDialog.Builder(this).setTitle("My Messages").setMessage("Private SMS manager • offline first\nV1: Inbox, Conversation, Search, Contacts, Dual SIM, Notifications, Backup, Privacy and Organization").setPositiveButton("OK",null).show());
 setContentView(root);}
 int simIndex(String v){if("SIM 1".equals(v))return 1;if("SIM 2".equals(v))return 2;if("Default".equals(v))return 3;return 0;}
 boolean isDefaultSms(){if(Build.VERSION.SDK_INT>=29){RoleManager r=getSystemService(RoleManager.class);return r!=null&&r.isRoleHeld(RoleManager.ROLE_SMS);}return false;}
 void add(String a,String b){root.addView(tv(a+"\n"+b,15));}
}