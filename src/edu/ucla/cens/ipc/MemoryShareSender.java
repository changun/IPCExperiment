package edu.ucla.cens.ipc;




import java.io.FileDescriptor;
import java.io.IOException;

import edu.ucla.cens.ipc.IRemoteService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.MemoryFile;
import android.os.RemoteException;

public class MemoryShareSender extends Sender{
	private static native int native_get_size(FileDescriptor fd) throws IOException;
	public MemoryShareSender(){
        super("RemoteFunctionSender");
    }
		 public MemoryShareSender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	} 
		/** 
         * Class for interacting with the main interface of the service.
         */ 
		IMemoryShare mService = null;
		
		MemoryFile mf;
        private ServiceConnection mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className,
                    IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  We are communicating with our
                // service through an IDL interface, so get a client-side
                // representation of that from the raw service object.
                mService = IMemoryShare.Stub.asInterface(service);
                try {
					try {
						
						mf  = new MemoryFile("Ashmem", size);
						
						mService.get_file_descriptor(mf.getParcelFileDescriptor(), size, test_id, times, mProfileService);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

			@Override
			public void onServiceDisconnected(ComponentName arg0) {
				// TODO Auto-generated method stub
				
			}
        };
		@Override
		boolean oneShot(int packet_id, byte[] payload){

			try {
				stringFromJNI();
				pinAndWrite(mf.getFileDescriptor(), mf, payload, 0, 0, size);
				 
				//mf.writeBytes(payload, 0, 0, size);
				mService.write(packet_id);
			} catch (Exception e) {  
				// TODO Auto-generated catch block
				e.printStackTrace(); 
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, int[] payload) {

			return true;
		}
		@Override
		boolean oneShot(int packet_id, long[] payload) {

			return true;
		}
		@Override
		boolean oneShot(int packet_id, float[] payload) {

			return true;
		}
		@Override
		boolean oneShot(int packet_id, double[] payload) {

			return true;
		}
		
		@Override
		void doWarmUp(int test_id, int times) {
            bindService(new Intent(IMemoryShare.class.getName()),
                    mConnection, Context.BIND_AUTO_CREATE);
            try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           
		}
		@Override
		void doCleanUp() { 
			this.unbindService( mConnection);
		}

	    static {
	        System.loadLibrary("memfile-help-func");
	    }
	    
	    public native String  stringFromJNI();
	    public native int  pinAndWrite(FileDescriptor fd, MemoryFile mf, byte[] buffer, int srcOffset, int destOffset, int count);
}
