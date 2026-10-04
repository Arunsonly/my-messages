package com.arun.mymessages;
import android.app.*;import android.os.*;import android.content.*;import android.graphics.Color;import android.text.InputType;import android.view.*;import android.widget.*;
public class LockActivity extends Activity{
 public void onCreate(Bundle b){super.onCreate(b);final EditText e=new EditText(this);e.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_VARIATION_PASSWORD);e.setHint("Enter PIN");e.setTextSize(22);e.setPadding(30,30,30,30);
 final AlertDialog a=new AlertDialog.Builder(this).setTitle("My Messages locked").setMessage("Enter your PIN to continue").setView(e).setCancelable(false).setPositiveButton("Unlock",null).setNegativeButton("Exit",(d,w)->finishAffinity()).create();
 a.setOnShowListener(d->{a.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v->{String p=getSharedPreferences("settings",0).getString("pin","");if(e.getText().toString().equals(p)){a.dismiss();finish();}else e.setError("Wrong PIN");});});a.show();}
}