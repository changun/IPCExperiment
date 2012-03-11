package edu.ucla.cens.ipc;

import java.io.FileDescriptor;
import java.io.IOException;

import dalvik.system.VMRuntime;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;

public class MemoryShareReceiver extends Service{


	private MemoryFile file;
	Service me;
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}
	public void onCreate(){
		super.onCreate();
		me = this;
	}
	 private final IMemoryShare.Stub mBinder = new IMemoryShare.Stub() {
		 int size;
		 int test_id;
	    int received = 0;
	    int times;
	    byte receive_bytes[];
	    Long timestamps[];
	    byte[] pointer[];

		@Override
		public void write(int packet_id) throws RemoteException {
			try {
				receive_bytes = new byte[size];
				readAndUnpin(file.getFileDescriptor(), file, receive_bytes, 0, 0, size);
				if(!IntentUtil.battery_test)
					pointer[packet_id] = receive_bytes;
				if(!IntentUtil.battery_test)
					timestamps[packet_id] = System.nanoTime();
		        //
		        
		        received++;
		         
		        if(packet_id == times-1){
		        	//mService.profile_one();
		        	if(!IntentUtil.battery_test)
		        		ReceiverUtil.logTimestamp(me, test_id, timestamps);
		        	
		        }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		IProfileService mService = null;
		@Override
		public void  get_file_descriptor(ParcelFileDescriptor fd, int size, int test_id, int times, IProfileService mService)
				throws RemoteException {
			try {
				VMRuntime.getRuntime().setMinimumHeapSize(16*1024*1024);
				file  = new MemoryFile(fd.getFileDescriptor(), size, "r");
				this.size = size;
				this.test_id = test_id;
				this.times = times;
				timestamps = new Long[times];
				pointer = new byte[times][];
				this.mService = mService;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

		} 
	 
	 };
	 public native int  readAndUnpin(FileDescriptor fd, MemoryFile mf, byte[] buffer, int srcOffset, int destOffset, int count);
	    static {
	        System.loadLibrary("memfile-help-func");
	    }
	    
}
