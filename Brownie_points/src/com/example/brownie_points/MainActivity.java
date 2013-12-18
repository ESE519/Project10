package com.example.brownie_points;




import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
   
	Button btn, btn1;
	
	 
	 /*******Launches the Create a Cupcake Main activity ********/
	public void start()
	{
		Intent intent = new Intent(this, Bluetooth_fin.class);
		startActivity(intent);
	}

	/*******Launches the Express Main activity ********/
	
	public void start1()
	{
		Intent intent = new Intent(this, ExpressMode.class);
		startActivity(intent);
	}
	
	/******* UI Main Activity ********/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	   
		btn = (Button)findViewById(R.id.button);

		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				start();
			}
			
		});
		
		
		
		
	}
  
	


}
