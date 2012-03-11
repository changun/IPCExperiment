package edu.ucla.cens.ipc;




import edu.ucla.cens.ipc.IRemoteService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteFunctionSender extends Sender{
	byte sub_packet[] = new byte[1024*1024];
	public RemoteFunctionSender(){
        super("RemoteFunctionSender");
    }
		 public RemoteFunctionSender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
		/**
         * Class for interacting with the main interface of the service.
         */
		IRemoteService mService = null;
        private ServiceConnection mConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className,
                    IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  We are communicating with our
                // service through an IDL interface, so get a client-side
                // representation of that from the raw service object.
                mService = IRemoteService.Stub.asInterface(service);
                try {
					mService.warm_up(test_id, times, mProfileService);
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
			if(size <= 1024*1024){
				try {
					mService.ipc_test_byte_array(packet_id, payload);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;
				}
				return true;
			}
			else{
				
				for(int i=0;i<size;i+=(1024*1024)){
					
					System.arraycopy(payload, i, sub_packet, 0, 1024*1024);
					try {
						mService.ipc_test_byte_array(packet_id, sub_packet);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return false;
					}
					
				}
				return true;
			}
		}
		@Override
		boolean oneShot(int packet_id, int[] payload) {
			try {
				mService.ipc_test_int_array(packet_id, payload);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, long[] payload) {
			try {
				mService.ipc_test_long_array(packet_id, payload);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, float[] payload) {
			try {
				mService.ipc_test_float_array(packet_id, payload);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		@Override
		boolean oneShot(int packet_id, double[] payload) {
			try {
				mService.ipc_test_double_array(packet_id, payload);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return false;
			}
			return true;
		}
		
		@Override
		void doWarmUp(int test_id, int times) {
            bindService(new Intent(IRemoteService.class.getName()),
                    mConnection, Context.BIND_AUTO_CREATE);
            try {
            	while(mService == null)
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
	
	
}
