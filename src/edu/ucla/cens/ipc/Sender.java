package edu.ucla.cens.ipc;

import java.io.IOException;

import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;


import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import edu.ucla.cens.systemlog.Log;
import edu.ucla.cens.systemlog.ISystemLog;
public abstract class Sender extends  IntentService {
	

	public Sender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	int period;
	int size;
	int times;
	int test_id;
	int sampleRate;
	Intent intent;
	DATA_TYPE data_type;
	String TAG;
	Service sender;
	IntentService me =this;
	/** Called when the service is created. */
	@Override
    public void onCreate(){
		super.onCreate();
		sender = this;
        /** initialize the System Log **/
        //Log.setAppName("IPCTest"); 
        //bindService(new Intent(ISystemLog.class.getName()), Log.SystemLogConnection, Context.BIND_AUTO_CREATE);
        
    }

    abstract boolean oneShot(int packet_id, byte[] payload);
    abstract boolean oneShot(int packet_id, int[] payload);
    abstract boolean oneShot(int packet_id, long[] payload);
    abstract boolean oneShot(int packet_id, float[] payload);
    abstract boolean oneShot(int packet_id, double[] payload);
    boolean oneShot(int packet_id){
    	
    	 throw new UnsupportedOperationException("Only ContentProvider support it");
    };
    
    abstract void doWarmUp(int test_id, int times);
    abstract void doCleanUp();
    
	IProfileService mProfileService = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            mProfileService = IProfileService.Stub.asInterface(service);
        }

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			
		}
    };

    void initProfile(){
        bindService(new Intent(IProfileService.class.getName()),
                mConnection, Context.BIND_AUTO_CREATE);
        while(mProfileService == null)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    void profileOne(){
		try {
			mProfileService.profile_one();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    void endProfile(){
		try {
			mProfileService.end();
			unbindService(mConnection);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    /**
     * readIntent
     * read the parameters passed by caller activity and save them into global variables
     * 
     * @param intent: the Intent going to read
     */
    void readIntent(Intent intent){
    	Bundle bundle = intent.getExtras();
    	period = bundle.getInt("period");
    	size = bundle.getInt("size");
    	times =bundle.getInt("times");
    	test_id =bundle.getInt("test_id");
    	sampleRate =bundle.getInt("sampleRate");
    	data_type = DATA_TYPE.valueOf(DATA_TYPE.class, bundle.getString("data_type"));
    	//this TAG is for identification in SystemLog
    	TAG = "Test ID:" + test_id + " Sender";
    }
    void notifyCallerTestEnd(){
		Intent i = new Intent(IntentUtil.TESTEND_INTENT);
        i.putExtra("test_id", test_id);
        sendBroadcast(i);
    }
    void logTimestamps(Long timestamps[], String filename){
		for(int i = 0 ; i < timestamps.length; i++){
        	Log.i(filename, String.format("{test_id:%d, packet:%d, timestamp:%d}", test_id, i, timestamps[i] ));
        }
		try {
			Log.myFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    byte[] byte_payload = null;
    int[] int_payload = null;
    long[] long_payload = null;
    float[] float_payload = null;
    double[] double_payload = null;
    
    protected synchronized void onHandleIntent (Intent intent){
    	/** read data from Intent **/
    	readIntent(intent);
    	/** do initialization **/

    	Long timestamps[] = new Long[times];
    	initProfile();
    	doWarmUp(test_id, times);
    	try {
			mProfileService.init(test_id);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	/** prepare payload for different type of data**/
    	System.gc();
    	switch (data_type){
        case byte_array:
        	byte_payload = new byte[size]; byte_payload[0] = 2;break;
        case int_array:
        	int_payload = new int[size]; break;
        case float_array:
        	float_payload = new float[size]; break;
        case double_array:
        	double_payload = new double[size]; break;
        case long_array:
        	long_payload = new long[size]; break;
        }
    	
    	/** do continuous profiling or one time **/
    	
    	
    	
    	
    	
	    /** do the work **/

    	
    	profileOne();
		for(int i=0; i<times || IntentUtil.battery_test; i++){
			timestamps[i] = System.nanoTime();
			if(!IntentUtil.battery_test)timestamps[i] = System.nanoTime(); 
	    	switch (data_type){
	    	
	        case byte_array:
	        	oneShot(i,byte_payload); break;
	        case int_array:
	        	oneShot(i,int_payload); break;
	        case float_array:
	        	oneShot(i,float_payload); break;
	        case double_array:
	        	oneShot(i,double_payload); break;
	        case long_array:
	        	oneShot(i,long_payload); break;
	        case query:
	        	oneShot(i); break;
	        }
	    	/** trigger profile **/
			//if((i+1) % sampleRate == 0)
	    	
	    	
	    	
	    	//else
	    		
	    	/** do sleep **/
			if(period > 0)
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			profileOne();
		}
		/**end the work **/
		
		/** end profile  **/
		endProfile();/*
		if(this.getClass() == BroadcastSender.class){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		logTimestamps(timestamps,"Sender");
		/** work end **/
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/** clean up and stop itself **/
		doCleanUp();
		Intent i = new Intent();
		i.setClass(sender , ProfileService.class);
		stopService(i);
		sender.stopSelf();
		notifyCallerTestEnd();
		/** set BroadCast Receiver to be notified when profile done saving log**/

    
    }

	  
    public void onDestroy(){
    	
    }
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
    
}
