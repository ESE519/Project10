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
	/*
	 private static final String TAG = "bluetooth1";
	       
	 private BluetoothAdapter btAdapter = null;
	 private BluetoothSocket btSocket = null;
	 private OutputStream outStream = null;
	 
	private StringBuilder builder ;
	 
	Button btn1,btn2,btn3,btn4,btn5;
	 
	 // SPP UUID service
	 private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 
	// MAC-address of Bluetooth module (you must edit this line)
	 private static String address = "00:06:66:08:61:97";
	 final int SEND_BUTTON_ID = 1;
	 final int CLEAR_BUTTON_ID = 2;
	 
	 */
	 
	public void start()
	{
		Intent intent = new Intent(this, Bluetooth_fin.class);
		startActivity(intent);
	}
	
	public void start1()
	{
		Intent intent = new Intent(this, ExpressMode.class);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	   
		btn = (Button)findViewById(R.id.button);
		//btn1 = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				start();
			}
			
		});
		
		
		/*btn1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				start1();
			}
			
		});
		*/
		/*
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		builder = new StringBuilder();
		
		final TextView view3 = (TextView)findViewById(R.id.textView2);
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		btn3 = (Button)findViewById(R.id.button3);
		btn4 = (Button)findViewById(R.id.button4);
		btn5 = (Button)findViewById(R.id.button5);
		
		
		 btn1.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        builder.append("1");
		        
		        view3.setText(builder.toString());
		        Toast.makeText(getApplicationContext(), "Horizontal-Mill selected", Toast.LENGTH_SHORT).show();
		      }
		    });
		 btn2.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		        builder.append("2");
		        
		        view3.setText(builder.toString());
		        Toast.makeText(getApplicationContext(), "Vertical-Mill selected", Toast.LENGTH_SHORT).show();
		      }
		    });
		 btn3.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	builder.append("3");
		    	
		    	view3.setText(builder.toString());
		        Toast.makeText(getApplicationContext(), "Air chipper selected", Toast.LENGTH_SHORT).show();
		      }
		    });
		btn4 = (Button)findViewById(R.id.button4);
		
		checkBTState();
		
		 btn4.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	showDialog(SEND_BUTTON_ID);

		      }
		    });
		 
		 btn5.setOnClickListener(new OnClickListener() {
		      public void onClick(View v) {
		    	showDialog(CLEAR_BUTTON_ID);

		      }
		    });
		*/
		
	}
  
	/*
	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
		case SEND_BUTTON_ID:
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("confirm to send");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					send();
					
				}
			});
			
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			return builder.create();
		}
		
		case CLEAR_BUTTON_ID:
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("confirm to clear");
			builder.setCancelable(false);
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					clear();
					
				}
			});
			
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			
			return builder.create();
		}
	
		}
		return null;
	}
	
	public void clear()
	{
		TextView view3 = (TextView)findViewById(R.id.textView2);
		builder.delete(0, builder.length());
		view3.setText("");
	}
	
	public void send()
	{
		if(builder.length() > 7)
			{Toast.makeText(getBaseContext(), "Limit of 7 tasks", Toast.LENGTH_LONG).show();
			TextView view3 = (TextView)findViewById(R.id.textView2);
			view3.setText("");
			builder.delete(0, builder.length());
			return;
			}
		
		while(builder.length()  < 7)
		{
			builder.append("0");
		}
        sendData(builder.toString());
        TextView view3 = (TextView)findViewById(R.id.textView2);
		builder.delete(0, builder.length());
    	view3.setText("");
        Toast.makeText(getBaseContext(), "Sent Data", Toast.LENGTH_SHORT).show();
	}
	
	 private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
	      if(Build.VERSION.SDK_INT >= 10){
	          try {
	              final Method  m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
	              return (BluetoothSocket) m.invoke(device, MY_UUID);
	          } catch (Exception e) {
	              Log.e(TAG, "Could not create Insecure RFComm Connection",e);
	          }
	      }
	      return  device.createRfcommSocketToServiceRecord(MY_UUID);
	  }
	
    @Override
	public void onResume()
	{
		super.onResume();
		Log.d(TAG, "...onResume - try connect...");
	    
	    // Set up a pointer to the remote node using it's address.
	    BluetoothDevice device = btAdapter.getRemoteDevice(address);
	    try {
	        btSocket = createBluetoothSocket(device);
	    } catch (IOException e1) {
	        errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
	    }
		
	    //cancel the bt discovery as it drains power
	    btAdapter.cancelDiscovery();
	    
	    Log.d(TAG, "...Connecting...");
	    try {
	      btSocket.connect();
	      Log.d(TAG, "...Connection ok...");
	    } catch (IOException e) {
	      try {
	        btSocket.close();
	      } catch (IOException e2) {
	        errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
	      }
	    }
	    
	    // Create a data stream so we can talk to server.
	    Log.d(TAG, "...Create Socket...");
	  
	    try {
	      outStream = btSocket.getOutputStream();
	    } catch (IOException e) {
	      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
	    }
	}
    
    @Override
    public void onPause()
    {
    	  super.onPause();
    	  
    	    Log.d(TAG, "...In onPause()...");
    	  
    	    if (outStream != null) {
    	      try {
    	        outStream.flush();
    	      } catch (IOException e) {
    	        errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
    	      }
    	    }
    	  
    	    try     {
    	      btSocket.close();
    	    } catch (IOException e2) {
    	      errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
    	    }
    }
	
	 private void checkBTState() {
		    
		    if(btAdapter==null) { 
		      errorExit("Fatal Error", "Bluetooth not support");
		    } else {
		      if (btAdapter.isEnabled()) {
		        Log.d(TAG,"...Bluetooth ON...");
		      } else {
		       
		        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		        startActivityForResult(enableBtIntent, 1);
		      }
		    }
		  }
	 
	private void errorExit(String title, String message){
		    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
		    finish();
		  }
	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	 private void sendData(String message) {
		    byte[] msgBuffer = message.getBytes();
		  
		    Log.d(TAG, "...Send data: " + message + "...");
		  
		    try {
		      outStream.write(msgBuffer);
		    } catch (IOException e) {
		      String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
		      if (address.equals("00:00:00:00:00:00")) 
		        msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
		        msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
		        
		        errorExit("Fatal Error", msg);       
		    }
		  }

	



*/


}
