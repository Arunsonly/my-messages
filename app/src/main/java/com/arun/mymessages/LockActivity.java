package com.arun.mymessages;
import android.app.*;import android.os.*;import android.content.*;import android.hardware.biometrics.BiometricPrompt;import android.view.*;import android.widget.*;import java.util.concurrent.*;

public class LockActivity extends Activity{
 public void onCreate(Bundle b){super.onCreate(b);SharedPreferences p=getSharedPreferences("settings",0);if(Build.VERSION.SDK_INT>=28&&p.getBoolean("biometric",false)){showBiometric();}else showPin();}
 void showBiometric(){BiometricPrompt prompt=new BiometricPrompt.Builder(this).setTitle("My Messages").setSubtitle("Unlock your private messages").setDescription("Use fingerprint or device biometric").setNegativeButton("Use PIN",getMainExecutor(),(d,w)->showPin()).build();prompt.authenticate(new BiometricPrompt.CryptoObjectDummy());}
 void showPin(){final EditText e=new EditText(this);e.setInputType(2|0x10);e.setHint("Enter PIN");e.setTextSize(22);e.setPadding(30,30,30,30);
 final AlertDialog a=new AlertDialog.Builder(this).setTitle("My Messages locked").setMessage("Enter your PIN to continue").setView(e).setCancelable(false).setPositiveButton("Unlock",null).setNegativeButton("Exit",(d,w)->finishAffinity()).create();
 a.setOnShowListener(d->{a.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v->{String p=getSharedPreferences("settings",0).getString("pin","");if(e.getText().toString().equals(p)){a.dismiss();finish();}else e.setError("Wrong PIN");});});a.show();}
}