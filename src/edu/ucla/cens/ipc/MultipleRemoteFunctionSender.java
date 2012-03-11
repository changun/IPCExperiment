package edu.ucla.cens.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class MultipleRemoteFunctionSender extends Sender {
	int packet_id;
	byte[] payload;
	public MultipleRemoteFunctionSender(){
        super("MultipleRemoteFunctionSender");
    }
	IRemoteService s0;
	IRemoteService s1;
	IRemoteService s2;
	IRemoteService s3;
	Thread t0 = new Thread(){
		public void run(){
			try {

				s0.ipc_test_byte_array(packet_id, payload);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	Thread t1 = new Thread(){
		public void run(){
			try {
				s1.ipc_test_byte_array(packet_id, payload);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	Thread t2 = new Thread(){
		public void run(){
			try {
				s2.ipc_test_byte_array(packet_id, payload);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	Thread t3 = new Thread(){
		public void run(){
			try {
				s3.ipc_test_byte_array(packet_id, payload);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	@Override
	boolean oneShot(int packet_id, byte[] payload) {
		this.packet_id = packet_id;
		this.payload = payload;
		

			t0.run();
			t1.run();
			t2.run();
			t3.run();
			try {
				t0.join();
				t1.join();
				t2.join();
				t3.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}

	@Override
	boolean oneShot(int packet_id, int[] payload) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean oneShot(int packet_id, long[] payload) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean oneShot(int packet_id, float[] payload) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	boolean oneShot(int packet_id, double[] payload) {
		// TODO Auto-generated method stub
		return false;
	}
    private ServiceConnection mConnection_0 = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            s0 = IRemoteService.Stub.asInterface(service);
            try {
            	s0.warm_up(test_id, times, mProfileService);
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
    private ServiceConnection mConnection_1 = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            s1= IRemoteService.Stub.asInterface(service);
            try {
            	s1.warm_up(test_id, times, mProfileService);
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
    private ServiceConnection mConnection_2 = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            s2 = IRemoteService.Stub.asInterface(service);
            try {
            	s2.warm_up(test_id, times, mProfileService);
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
    private ServiceConnection mConnection_3 = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  We are communicating with our
            // service through an IDL interface, so get a client-side
            // representation of that from the raw service object.
            s3 = IRemoteService.Stub.asInterface(service);
            try {
            	s3.warm_up(test_id, times, mProfileService);
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
	void doWarmUp(int test_id, int times) {
		Intent intent = new Intent();
		intent.setClass(this, RemoteFunctionReceiver.class);
        bindService(intent, mConnection_0, Context.BIND_AUTO_CREATE);
        intent.setClass(this, RemoteFunctionReceiver_1.class);
        bindService(intent, mConnection_1, Context.BIND_AUTO_CREATE);
        intent.setClass(this, RemoteFunctionReceiver_2.class);
        bindService(intent, mConnection_2, Context.BIND_AUTO_CREATE);
        intent.setClass(this, RemoteFunctionReceiver_3.class);
        bindService(intent, mConnection_3, Context.BIND_AUTO_CREATE);
        while(s0== null || s1==null || s2==null || s3==null)
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	void doCleanUp() {
		// TODO Auto-generated method stub

	}

}
