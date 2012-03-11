package edu.ucla.cens.ipc;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class StartServiceSender extends Sender{

		byte sub_packet[] = new byte[1024*256];
		public StartServiceSender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
		public StartServiceSender(){
	        super("StartServiceSender");
	    }
		@Override
		boolean oneShot(int packet_id, byte[] payload) {
			if(size <= 1024*256){
				Intent intent = new Intent();
				intent.setClass(this, StartServiceReceiver.class);
				intent.putExtra("payload", payload);
				intent.putExtra("packet_id", packet_id);
				return startService(intent)== null ? false : true;
			}
			else{
				
				for(int i=0;i<size;i+=(1024*256)){
					
					System.arraycopy(payload, i, sub_packet, 0, 1024*256);
					Intent intent = new Intent();
					intent.setClass(this, StartServiceReceiver.class);
					intent.putExtra("payload", sub_packet);
					intent.putExtra("packet_id", packet_id);
					startService(intent);
				}
				return true;
			}
		}

		@Override
		boolean oneShot(int packet_id, int[] payload) {
			Intent intent = new Intent();
			intent.setClass(this, StartServiceReceiver.class);
			intent.putExtra("payload", payload);
			intent.putExtra("packet_id", packet_id);
			return startService(intent)== null ? false : true;
		}
		@Override
		boolean oneShot(int packet_id, long[] payload) {
			Intent intent = new Intent();
			intent.setClass(this, StartServiceReceiver.class);
			intent.putExtra("payload", payload);
			intent.putExtra("packet_id", packet_id);
			return startService(intent)== null ? false : true;
		}
		@Override
		boolean oneShot(int packet_id, float[] payload) {
			Intent intent = new Intent();
			intent.setClass(this, StartServiceReceiver.class);
			intent.putExtra("payload", payload);
			intent.putExtra("packet_id", packet_id);
			return startService(intent)== null ? false : true;
		}
		@Override
		boolean oneShot(int packet_id, double[] payload) {
			Intent intent = new Intent();
			intent.setClass(this, StartServiceReceiver.class);
			intent.putExtra("payload", payload);
			intent.putExtra("packet_id", packet_id);
			return startService(intent)== null ? false : true;
		}

		@Override
		void doWarmUp(int test_id, int times) {
			Intent intent = new Intent();
			intent.putExtra("test_id", test_id);
			intent.putExtra("times", times);
			intent.putExtra("data_type", data_type.name());
			intent.setClass(this, StartServiceReceiver.class);
			startService(intent);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		void doCleanUp() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent();
			intent.setClass(this, StartServiceReceiver.class);
			stopService(intent);
		}
	
}
