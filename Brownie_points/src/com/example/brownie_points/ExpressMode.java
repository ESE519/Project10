package com.example.brownie_points;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import com.example.brownie_points.Bluetooth_fin.ConnectedThread;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ExpressMode extends Activity {

	 private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 private static String address = "00:06:66:08:61:97";
	 final int SEND_BUTTON_ID = 1;

	 private int ExpressMode = 2;
	 
	 private static final String TAG = "bluetooth1";
	 private static final int RECIEVE_MESSAGE =1;
     Handler h;
	 private BluetoothAdapter btAdapter = null;
	 private BluetoothSocket btSocket = null;
     private ConnectedThread t;
	 Integer[] image = { R.drawable.imgbt3, R.drawable.imgbt2,R.drawable.imgbt4, R.drawable.imgbt1 ,R.drawable.black};
	 private String[] name = { "chocolate_syrup",  "chocolate_chips","chocolate_sprinkles","sprinkles", "No topping"  };
	 private int thing1;
	 
	 Switch s1;
	 Spinner o1;
	 Button s,b;
	 TextView rcv; // Tracking the Real time status through a Handler
	 StringBuilder builder;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_mode);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		s1 = (Switch)findViewById(R.id.s1);
		o1 = (Spinner)findViewById(R.id.p1);
		s = (Button)findViewById(R.id.sbutton);
		b = (Button)findViewById(R.id.buttonb);
		rcv = (TextView)findViewById(R.id.rcv);
		builder = new StringBuilder();
		
 class MyAdapter extends ArrayAdapter<String>{
			 
			 public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
		            super(context, textViewResourceId, objects);
		        } 
			 
			 public View getDropDownView(int position, View convertView,ViewGroup parent) {
		            return getCustomView(position, convertView, parent);
		        }
			 
			 public View getView(int position, View convertView, ViewGroup parent) {
		            return getCustomView(position, convertView, parent);
		        }
			 
			  public View getCustomView(int position, View convertView, ViewGroup parent) {
				  
		            LayoutInflater inflater=getLayoutInflater();
		            View row=inflater.inflate(R.layout.row, (ViewGroup) parent, false);
		            TextView label=(TextView)row.findViewById(R.id.company);
		            label.setText(name[position]);
		 
		           
		 
		            ImageView icon=(ImageView)row.findViewById(R.id.image);
		            icon.setImageResource(image[position]);
		 
		            return row;
		            }
		        }
 
 o1.setAdapter(new MyAdapter(ExpressMode.this, R.layout.row, name));
 o1.setSelection(4);
 
 o1.setOnItemSelectedListener(new OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
		
			if(!s1.isChecked())
			{
			switch(arg2)
			{
			case 0:
				thing1 = 1;
				break;
			case 1:
				thing1 = 3;
				break;
			case 2:
				thing1 = 5;
				break;
			case 3:
				thing1 = 7;
				break;
			case 4:
				thing1 = 0;
				break;
			default:
				thing1 = 0;
				break;
			}
			}
			else
			{
				switch(arg2)
				{
				case 0:
					thing1 = 2;
					break;
				case 1:
					thing1 = 4;
					break;
				case 2:
					thing1 = 6;
					break;
				case 3:
					thing1 = 8;
					break;
				case 4:
					thing1 = 0;
					break;
				default:
					thing1 = 0;
					break;
				}	
			}
			
			Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
		
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			thing1 = 0;
		}
		
	});
 
 s1.setOnCheckedChangeListener(new OnCheckedChangeListener()
 {
	 @Override
	   public void onCheckedChanged(CompoundButton buttonView,
	     boolean isChecked) {
		 if(isChecked){
		    
	         switch(thing1)
	         {
	         case 1:
	        	 thing1 = 2;
	        	 break;
	         case 3:
	        	 thing1 = 4;
	        	 break;
	         case 5:
	        	 thing1 = 6;
	        	 break;
	         case 7 :
	        	 thing1 = 8;
	        	 break;
	         default:
	        	 thing1 = 0;
	        	 break;
	         }
	         Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
	      
		    }else{

		    	   switch(thing1)
			         {
			         case 2:
			        	 thing1 = 1;
			        	 break;
			         case 4:
			        	 thing1 = 3;
			        	 break;
			         case 6:
			        	 thing1 = 5;
			        	 break;
			         case 8:
			        	 thing1 = 7;
			        	 break;
			        default:
			        	thing1 = 0;
			        	break;
			         }
		    	   Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
		    }
	 }
 });
 
 s.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
   	 
   	showDialog(SEND_BUTTON_ID);

     }
   });
 
 b.setOnClickListener(new OnClickListener() {
     public void onClick(View v) {
   	 
   	finish();

     }
   });
 
 
 
 h = new Handler() {
     public void handleMessage(android.os.Message msg) {
      
     	switch(msg.what)
     	{
     	case RECIEVE_MESSAGE:
     	{
     		byte[] readBuf = (byte[]) msg.obj;
     		
            String strIncom = new String(readBuf, 0, msg.arg1);
            
            switch(strIncom.charAt(0))
            {
            
          
            case 'S':
         	   rcv.setText("sprinkles added" );
         	   break;
            case 'H':
         	   rcv.setText("chocolate sprinkles added" );
         	   break;
            case 'C':
         	   rcv.setText("Chocolate Syrup added" );
            case 'D':
            	rcv.setText("Chocolate chips added");
         	   break;
            
         	   
            
            
            }
            //rcv.setText("Track your Order:" + strIncom);
     		
            /*
             recvr.append(strIncom);
     		int endOfLineIndex = recvr.indexOf("\r\n");
     		 if (endOfLineIndex > 0) {                                            // if end-of-line,
                  String sbprint = recvr.substring(0, endOfLineIndex);               // extract string
                  recvr.delete(0, recvr.length());                                      // and clear
                  rcv.setText("Data from mbed: " + sbprint);            // update TextView
                 
              }
              */
     		break;
     	}
     	
     	}
     };
 };
 
 checkBTState();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.express_mode, menu);
		return true;
	}
	
	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
		case SEND_BUTTON_ID:
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Is this your topping choice?");
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
		default:
			return null;
		}
		
	}
	
	public void send()
	{
		builder.append(ExpressMode);
	    builder.append(thing1);
	 
	
	    /*
		if(builder.length() > 5)
			{Toast.makeText(getBaseContext(), "Limit of 5 tasks", Toast.LENGTH_LONG).show();
			TextView view3 = (TextView)findViewById(R.id.textView2);
			view3.setText("");
			builder.delete(0, builder.length());
			return;
			}
		
		while(builder.length()  < 7)
		{
			builder.append("0");
		}
		
		*/
	    
	  
       t.sendData(builder.toString());
      
	   builder.delete(0, builder.length());
	   
      
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
	 
	    @Override
		public void onResume()
		{
			super.onResume();
			Log.d(TAG, "...onResume - try connect...");
		    
	
		    BluetoothDevice device = btAdapter.getRemoteDevice(address);
		    try {
		        btSocket = createBluetoothSocket(device);
		    } catch (IOException e1) {
		        errorExit("Fatal Error", "In onResume() and socket create failed: " + e1.getMessage() + ".");
		    }
			
		   
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
		    
		    t = new ConnectedThread(btSocket);
		    t.start();
		  /*
		    try {
		      outStream = btSocket.getOutputStream();
		    } catch (IOException e) {
		      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
		    }
		    */
		}
	    

	    @Override
	    public void onPause()
	    {
	    	  super.onPause();
	    	  
	    	    Log.d(TAG, "...In onPause()...");
	    	  /*
	    	    if (outStream != null) {
	    	      try {
	    	        outStream.flush();
	    	      } catch (IOException e) {
	    	        errorExit("Fatal Error", "In onPause() and failed to flush output stream: " + e.getMessage() + ".");
	    	      }
	    	    }
	    	    */
	    	  
	    	    try     {
	    	      btSocket.close();
	    	    } catch (IOException e2) {
	    	      errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
	    	    }
	    }
	    
	 private void errorExit(String title, String message){
		    Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
		    finish();
		  }
	 
	 public class ConnectedThread extends Thread
		{
			private OutputStream outStream = null;
			private InputStream inStream = null;
			
			public ConnectedThread(BluetoothSocket socket)
			{
			try {
			      outStream = socket.getOutputStream();
			      inStream = socket.getInputStream();
			    } catch (IOException e) {
			      errorExit("Fatal Error", "In onResume() and output stream creation failed:" + e.getMessage() + ".");
			    }
			}
			
			public void run()
			{
				byte[] buffer = new byte[256];  // buffer store for the stream
	            int bytes; // bytes returned from read()
	            while(true)
	            {
	            	try
	            	{
	            		bytes = inStream.read(buffer);
	            		h.obtainMessage(RECIEVE_MESSAGE,bytes,-1,buffer).sendToTarget();
	            	}
	            	catch(Exception e)
	            	{
	            		break;
	            	}
	            }
				
			}
			
			 private void sendData(String message) {
				    byte[] msgBuffer = message.getBytes();
				  
				    Log.d(TAG, "...Send data: " + message + "...");
				  
				    try {
				      outStream.write(msgBuffer);
				    } catch (IOException e) {
				     // String msg = "In onResume() and an exception occurred during write: " + e.getMessage();
				      //if (address.equals("00:00:00:00:00:00")) 
				       // msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
				       // msg = msg +  ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + " exists on server.\n\n";
				        
				        Log.d(TAG,"Error data send :" + e.getMessage())   ;   
				    }
				  }
		}

}
