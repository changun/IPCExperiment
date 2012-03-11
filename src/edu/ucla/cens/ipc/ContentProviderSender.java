package edu.ucla.cens.ipc;




import java.util.Arrays;

import dalvik.system.VMRuntime;

import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;

import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

public class ContentProviderSender extends Sender{
	public ContentProviderSender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public ContentProviderSender(){
        super("ContentProviderSender");
    }
	byte sub_packet[] = new byte[1024*1024];
	String index[]=new String[1024];
	Uri uri = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider");
	ContentValues values;
	ContentResolver resolver ;
	ContentProviderClient cpr;
		@Override
		boolean oneShot(int packet_id, byte[] payload) {
			if(size <= 1024*1024){
				values = new ContentValues();
				values.put("packet_id", packet_id);
				values.put("payload", payload);
				
				try {
					cpr.insert(uri, values);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
			else{
				
				for(int i=0;i<size;i+=(1024*1024)){
					values = new ContentValues();
					values.put("packet_id", packet_id);
					System.arraycopy(payload, i, sub_packet, 0, 1024*1024);
					values.put("payload", sub_packet);
					try {
						cpr.insert(uri, values);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return true;
			}
		}
		@Override
		boolean oneShot(int packet_id, int[] payload) {
			values = new ContentValues();
			values.put("packet_id", packet_id);
			for(int i=0;i<payload.length;i++){
				values.put(index[i],  payload[i]);
			
			}

			try {
				cpr.insert(uri, values);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}

		@Override
		boolean oneShot(int packet_id, long[] payload) {
			values = new ContentValues();
			values.put("packet_id", packet_id);
			for(int i=0;i<payload.length;i++){
				values.put(index[i],  payload[i]);
			
			}
	
			try {
				cpr.insert(uri, values);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, float[] payload) {
			values = new ContentValues();
			values.put("packet_id", packet_id);
			for(int i=0;i<payload.length;i++){
				values.put(index[i],  payload[i]);
			}

			try {
				cpr.insert(uri, values);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, double[] payload) {
			values = new ContentValues();
			values.put("packet_id", packet_id);
			for(int i=0;i<payload.length;i++){
				values.put(index[i],  payload[i]);
			}

			
			try {
				cpr.insert(uri, values);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		
		Long receiverTimestamp[];
		String pointers[];
		//query operation test.
		@Override
		boolean oneShot(int packet_id) {
			
			try {
				String[] column = {"c"};
				Cursor c =cpr.query(uri, column, null, null, null);
				
				c.moveToFirst();
				
				pointers[packet_id] =c.getString(0);
				c.close();
				if(!IntentUtil.battery_test)
		        	receiverTimestamp[packet_id] = System.nanoTime();
		         
		        //System.gc();
		        if(packet_id == times-1){
		        	//mProfileService.profile_one();
		        	logTimestamps(receiverTimestamp,"Receiver");
		        	
		        }
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		
		@Override
		void doWarmUp(int test_id, int times) {
			VMRuntime.getRuntime().setMinimumHeapSize(16*1024*1024);
			for(Integer i=0;i<1024;i++)
				index[i]=i.toString();
			resolver =  getContentResolver();
			ContentValues cvalues = new ContentValues();  
	        cvalues.put("test_id", test_id);  
	        cvalues.put("times", times);
	        cvalues.put("data_type", data_type.name());
	        cvalues.put("array_size", size);
	        
	        Uri uri = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider");
	        String empty[] = {""};
	        cpr = resolver.acquireContentProviderClient(uri);
	        try {
				cpr.update(uri, cvalues, "", empty);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        if(data_type == DATA_TYPE.query){
	        	receiverTimestamp = new Long[times];
	        	pointers = new String[times];
	        }
		}

		@Override
		void doCleanUp() {
			// TODO Auto-generated method stub
			
		}


	
	
}
