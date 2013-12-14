package com.example.brownie_points;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;







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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Bluetooth_fin extends Activity {

	private int NormalMode = 1;
	
	 private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	 private static String address = "00:06:66:08:61:97";
	 final int SEND_BUTTON_ID = 1;
	 final int CLEAR_BUTTON_ID = 2;
	 private static final String TAG = "bluetooth1";
	 private static final int RECIEVE_MESSAGE =1;
     Handler h;
	 private BluetoothAdapter btAdapter = null;
	 private BluetoothSocket btSocket = null;
	 //TextView rcv; // Tracking the Real time status through a Handler
	 private ConnectedThread t;
	 
	 /***TODO*****/
	 private StringBuilder builder ; //The string builder currently used to send data
	 private int thing1,thing2,thing3,thing4;
	 Button btn4,btn5,btn6;
	 Switch s1,s2,s3,s4; //High Low Sliders
	 Spinner o1,o2,o3,o4; //my Custom Dropdown Implementation
	 private String sendarr[] = new String[5]; //new implementation String array to send data
	 Integer[] image = { R.drawable.imgbt3, R.drawable.imgbt2,R.drawable.imgbt4, R.drawable.imgbt1 ,R.drawable.black};
	 private String[] name = { "chocolate_syrup",  "chocolate_chips","chocolate_sprinkles","sprinkles", "No topping"  };
	
	 
	 
	 /***Display function *****/
	 String display(String inp)
	 {
		 StringBuilder b = new StringBuilder();
		 for(int i = 0 ; i< inp.length(); i++)
		 {
			 char c = inp.charAt(i);
			 
			 switch(c)
			 {
			 case '1':
			 { b.append("sprinkles,");
			   break;
			 } 
			 case '3':
				 {b.append("Chocolate chips,");
				 break;
				 }
			 case '5':
				 {b.append("Chocolate Syrup,");
				 break;
				 }
			 case '7':
			 {
				 b.append("Chocolate Sprinkles,");
				 break; 
			 }
				 
			default:
				{b.append("_");
				  break;
				}
			 }
			 
		
		 }
		 
		 return b.toString();
	 }
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
	
	
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth_fin);
		
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		
		builder = new StringBuilder();
		
		//rcv = (TextView)findViewById(R.id.rcv); //Track Order Status
		
		btn4 = (Button)findViewById(R.id.button4);
		btn5 = (Button)findViewById(R.id.button5);
		btn6 = (Button)findViewById(R.id.button6);
	
		s1 = (Switch)findViewById(R.id.s1);
		s2 = (Switch)findViewById(R.id.s2);
		s3 = (Switch)findViewById(R.id.s3);
		s4 = (Switch)findViewById(R.id.s4);
		
       o1 = (Spinner)findViewById(R.id.p1);
       o2 = (Spinner)findViewById(R.id.p2);
       o3 = (Spinner)findViewById(R.id.p3);
       o4 = (Spinner)findViewById(R.id.p4);
		
   
		
	   /*My Implementation of Custom Adapter for Spinner*/
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
 
 o1.setAdapter(new MyAdapter(Bluetooth_fin.this, R.layout.row, name));
 o2.setAdapter(new MyAdapter(Bluetooth_fin.this, R.layout.row, name));
 o3.setAdapter(new MyAdapter(Bluetooth_fin.this, R.layout.row, name));
 o4.setAdapter(new MyAdapter(Bluetooth_fin.this, R.layout.row, name));
 
 o1.setSelection(4);
 o2.setSelection(4);
 o3.setSelection(4);
 o4.setSelection(4);
 
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
			
			//Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
		
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			thing1 = 0;
		}
		
	});
	
	o2.setOnItemSelectedListener(new OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(!s2.isChecked())
			{
			switch(arg2)
			{
			case 0:
				thing2 = 1;
				break;
			case 1:
				thing2 = 3;
				break;
			case 2:
				thing2 = 5;
				break;
			case 3:
				thing2 = 7;
				break;
			case 4:
				thing2 = 0;
				break;
			default:
				thing2 = 0;
				break;
			}
			}
			else
			{
				switch(arg2)
				{
				case 0:
					thing2 = 2;
					break;
				case 1:
					thing2 = 4;
					break;
				case 2:
					thing2 = 6;
					break;
				case 3:
					thing2 = 8;
					break;
				case 4:
					thing2 = 0;
					break;
				default:
					thing2 = 0;
					break;
				}
			}
			
			//Toast.makeText(getBaseContext(), "Thing2" + thing2, Toast.LENGTH_SHORT).show();
		
		
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			thing2 = 0;
		}
		
	});
	
	o3.setOnItemSelectedListener(new OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(!s3.isChecked())
			{
			switch(arg2)
			{
			case 0:
				thing3 = 1;
				break;
			case 1:
				thing3 = 3;
				break;
			case 2:
				thing3 = 5;
				break;
			case 3:
				thing3 = 7;
				break;
			case 4:
				thing3 = 0;
				break;
			default:
				thing3 = 0;
				break;
			}
			}
			else
			{
				switch(arg2)
				{
				case 0:
					thing3 = 2;
					break;
				case 1:
					thing3 = 4;
					break;
				case 2:
					thing3 = 6;
					break;
				case 3:
					thing3 = 8;
					break;
				case 4:
					thing3 = 0;
					break;
				default:
					thing3 = 0;
					break;
				}	
			}
			
			//Toast.makeText(getBaseContext(), "Thing3" + thing3, Toast.LENGTH_SHORT).show();
			
			
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			thing3 = 0;
		}
		
	});
	

	o4.setOnItemSelectedListener(new OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			// TODO Auto-generated method stub
			if(!s4.isChecked())
			{
			switch(arg2)
			{
			case 0:
				thing4 = 1;
				break;
			case 1:
				thing4 = 3;
				break;
			case 2:
				thing4 = 5;
				break;
			case 3:
				thing4 = 7;
				break;
			case 4:
				thing4 = 0;
				break;
			default:
				thing4 = 0;
				break;
			}
			}
			else
			{
				switch(arg2)
				{
				case 0:
					thing4 = 2;
					break;
				case 1:
					thing4 = 4;
					break;
				case 2:
					thing4 = 6;
					break;
				case 3:
					thing4 = 8;
					break;
				case 4:
					thing4 = 0;
					break;
				default:
					thing4 = 0;
					break;
				}	
			}
			
			//Toast.makeText(getBaseContext(), "Thing4" + thing4, Toast.LENGTH_SHORT).show();
			
			
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			thing4 = 0;
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
		       //  Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
		      
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
			    	 //  Toast.makeText(getBaseContext(), "Thing1" + thing1, Toast.LENGTH_SHORT).show();
			    }
		 }
	 });

	 	 s2.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 {
		 @Override
		   public void onCheckedChanged(CompoundButton buttonView,
		     boolean isChecked) {
			 if(isChecked){
			     
				  switch(thing2)
			         {
			         case 1:
			        	 thing2 = 2;
			        	 break;
			         case 3:
			        	 thing2 = 4;
			        	 break;
			         case 5:
			        	 thing2 = 6;
			        	 break;
			         case 7:
			        	 thing2 = 8;
			        	 break;
			        default:
			        	 thing2 = 0;
			        	 break;
			         }
				  // Toast.makeText(getBaseContext(), "Thing2" + thing2, Toast.LENGTH_SHORT).show();
			    }else{

			    	switch(thing2)
			         {
			         case 2:
			        	 thing2 = 1;
			        	 break;
			         case 4:
			        	 thing2 = 3;
			        	 break;
			         case 6:
			        	 thing2 = 5;
			        	 break;
			         case 8:
			        	 thing2 = 7;
			        	 break;
			        default:
			        	thing2 = 0;
			        	break;
			         }
			    	 //  Toast.makeText(getBaseContext(), "Thing2" + thing2, Toast.LENGTH_SHORT).show();
			    }
		 }
	 });

	 s3.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 {
		 @Override
		   public void onCheckedChanged(CompoundButton buttonView,
		     boolean isChecked) {
			 if(isChecked){
			    
				  switch(thing3)
			         {
			         case 1:
			        	 thing3 = 2;
			        	 break;
			         case 3:
			        	 thing3 = 4;
			        	 break;
			         case 5:
			        	 thing3 = 6;
			        	 break;
			         case 7:
			        	 thing3 = 8;
			        	 break;
			        default:
			        	thing3 = 0;
			        	break;
			         }
				  
				  // Toast.makeText(getBaseContext(), "Thing3" + thing3, Toast.LENGTH_SHORT).show();
			    }else{

			    	switch(thing3)
			         {
			         case 2:
			        	 thing3 = 1;
			        	 break;
			         case 4:
			        	 thing3 = 3;
			        	 break;
			         case 6:
			        	 thing3 = 5;
			        	 break;
			         case 8:
			        	 thing3 = 7;
			        	 break;
			        default:
			        	thing3 = 0;
			        	break;
			         }
			    	  // Toast.makeText(getBaseContext(), "Thing3" + thing3, Toast.LENGTH_SHORT).show();

			    }
		 }
	 });
	 
	 s4.setOnCheckedChangeListener(new OnCheckedChangeListener()
	 {
		 @Override
		   public void onCheckedChanged(CompoundButton buttonView,
		     boolean isChecked) {
			 if(isChecked){
			    
				  switch(thing4)
			         {
			         case 1:
			        	 thing4 = 2;
			        	 break;
			         case 3:
			        	 thing4 = 4;
			        	 break;
			         case 5:
			        	 thing4 = 6;
			        	 break;
			         case 7:
			        	 thing4 = 8;
			        	 break;
			        default:
			        	thing4 = 0;
			        	break;
			         }
				  
				  // Toast.makeText(getBaseContext(), "Thing4" + thing4, Toast.LENGTH_SHORT).show();
			    }else{

			    	switch(thing4)
			         {
			         case 2:
			        	 thing4 = 1;
			        	 break;
			         case 4:
			        	 thing4 = 3;
			        	 break;
			         case 6:
			        	 thing4 = 5;
			        	 break;
			         case 8:
			        	 thing4 = 7;
			        	 break;
			        default:
			        	thing4 = 0;
			        	break;
			         }
			    	 //  Toast.makeText(getBaseContext(), "Thing4" + thing4, Toast.LENGTH_SHORT).show();

			    }
		 }
	 });
	 
	 
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
	 
		btn6.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
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
		               
		               case 'P':
		            	   //rcv.setText("Track your Order:Preparing your Order" );
		            	   Toast.makeText(getBaseContext(), "Preparing your Order", Toast.LENGTH_SHORT).show();
		            	   break;
		               case 'S':
		            	   //rcv.setText("Track your Order:Adding Sprinkles" );
		            	   Toast.makeText(getBaseContext(), "Adding Sprinkles", Toast.LENGTH_SHORT).show();
		            	   break;
		               case 'H':
		            	   //rcv.setText("Track your Order:Adding Chocolate Sprinkles" );
		            	   Toast.makeText(getBaseContext(), "Adding Chocolate Sprinkles", Toast.LENGTH_SHORT).show();
		            	   break;
		               case 'C':
		            	   //rcv.setText("Track your Order:Adding Chocolate Syrup" );
		            	   Toast.makeText(getBaseContext(), "Adding Chocolate Syrup", Toast.LENGTH_SHORT).show();
		            	   break;
		               case'R':
		            	   //rcv.setText("Track your Order:Your Order is Ready" );
		            	   Toast.makeText(getBaseContext(), "Order Ready", Toast.LENGTH_SHORT).show();
		            	   break;
		               case'D':
		            	   //rcv.setText("Track your Order: Adding Chocolate Chips");
		            	   Toast.makeText(getBaseContext(), "Adding Chocolate Chips", Toast.LENGTH_SHORT).show();
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
	
	

	
	protected Dialog onCreateDialog(int id)
	{
		switch(id)
		{
		case SEND_BUTTON_ID:
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("confirm to order");
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
			builder.setMessage("confirm to cancel the order");
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
	o1.setSelection(4);
	o2.setSelection(4);
	o3.setSelection(4);
	o4.setSelection(4);
		
	thing1 = 0;
	thing2 = 0;
	thing3 = 0;
	thing4 = 0;
	
	 s1.setChecked(false);
	   s2.setChecked(false);
	   s3.setChecked(false);
	   s4.setChecked(false);
	
		builder.delete(0, builder.length());
		
		Toast.makeText(getBaseContext(), "Re-select your toppings", Toast.LENGTH_SHORT).show();
		
	}
	
	public void send()
	{
		//builder.append(NormalMode);
	    builder.append(thing1);
	    builder.append(thing2);
	    builder.append(thing3);
	    builder.append(thing4);
	
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
	 
/*	 private void sendData(String message) {
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
	 */
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bluetooth_fin, menu);
		return true;
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
