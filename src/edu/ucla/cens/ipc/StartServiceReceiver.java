package edu.ucla.cens.ipc;

import dalvik.system.VMRuntime;
import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

public class StartServiceReceiver extends Service{



	    Long timestamps[];
	    byte[] pointers[];
	    int test_id;
	    int times;
	    int received = 0;
	    ReceiverUtil.DATA_TYPE data_type;
	    boolean initialized = false;


		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
	    public void onDestroy(){
	    	
	    }
	    @Override
	    public void onCreate(){

	    }
		

		public int onStartCommand (Intent intent, int flags, int startId){
			
			if(!initialized){
				

				VMRuntime.getRuntime().setMinimumHeapSize(16*1024*1024);
				times = intent.getIntExtra("times", 0);
				timestamps = new Long[times];
				pointers = new byte[times][];
				test_id = intent.getIntExtra("test_id", 0);
				data_type = DATA_TYPE.valueOf(DATA_TYPE.class,intent.getStringExtra("data_type"));
				initialized = true;
				
				System.gc();
				return START_STICKY;
			}
			int packet_id = intent.getIntExtra("packet_id", 0);
			
			switch(data_type){
			case byte_array:
				byte byte_payload[]  = intent.getByteArrayExtra("payload");
				if(!IntentUtil.battery_test)
					pointers[packet_id] = byte_payload;
				break;
				
			case int_array:
				int int_payload[]  = intent.getIntArrayExtra("payload");
				break;
			case float_array:
				float float_payload[] = intent.getFloatArrayExtra("payload");
				break;
			case double_array:
				double double_payload[] = intent.getDoubleArrayExtra("payload");
				break;
			}
			if(!IntentUtil.battery_test)
				timestamps[packet_id] = System.nanoTime();

        	//System.gc();
        	/*
        	try {
				this.mProfileService.profile_one();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        	received++;
	        if(packet_id == times-1){
	        	if(!IntentUtil.battery_test)
	        		ReceiverUtil.logTimestamp(this, test_id, timestamps);
	            return Service.START_NOT_STICKY;
	        }
			return START_STICKY;
		}
}