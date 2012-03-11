package edu.ucla.cens.ipc;

import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import android.app.IntentService;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;

public class BroadcastReceiver extends Service{



	    Long timestamps[];
	    int test_id;
	    int times;
	    int received = 0;
	    int receiver_id ;
	    ReceiverUtil.DATA_TYPE data_type;
	    Service service;

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
	    public void onDestroy(){
	    	
	    }
	    @Override
	    public void onCreate(){
	        service = this;
	        receiver_id = 0;
	    }
		public int onStartCommand (Intent intent, int flags, int startId){
			
			registerReceiver (this.mReceiver, new IntentFilter(IntentUtil.BROADCAST_TEST_INTENT ));


			times = intent.getIntExtra("times", 0);
			timestamps = new Long[times];
			test_id = intent.getIntExtra("test_id", 0);
			data_type = DATA_TYPE.valueOf(DATA_TYPE.class,intent.getStringExtra("data_type"));
			System.gc();
			return START_STICKY;
		}
		  public android.content.BroadcastReceiver mReceiver = new android.content.BroadcastReceiver(){
				@Override
				public void onReceive(final Context arg0, Intent intent) {
					switch(data_type){
					case byte_array:
						byte byte_payload[]  = intent.getByteArrayExtra("payload");
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
					int packet_id = intent.getIntExtra("packet_id", 0);
		        	timestamps[packet_id] = System.nanoTime();
			        received++;
			        if(received == times){
			        	unregisterReceiver (this);
			            ReceiverUtil.logTimestamp(service, test_id, timestamps, receiver_id);
			            service.stopSelf();
			        }
				}
		  };
}