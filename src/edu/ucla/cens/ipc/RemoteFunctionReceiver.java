/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.ucla.cens.ipc;


import dalvik.system.VMRuntime;
import edu.ucla.cens.ipc.IRemoteService;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;





public class RemoteFunctionReceiver extends Service {



    

	int receiver_id = 0;
    RemoteFunctionReceiver me;
    @Override
    public IBinder onBind(Intent intent) {
        // Select the interface to return.  If your service only implements
        // a single interface, you can just return it here without checking
        // the Intent.
        
            return mBinder;
        

        
    }
    @Override
    public void onCreate(){
    	me = this;
    	 receiver_id = 0;
        //Log.setAppName("IPCTest"); 
        //bindService(new Intent(ISystemLog.class.getName()), Log.SystemLogConnection, Context.BIND_AUTO_CREATE);
        
    }
    /**
     * The IRemoteInterface is defined through IDL
     */
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        Long timestamps[];
        byte[] pointers[];
        int test_id;
        int times;
        int received ;
		public void received(int packet_id, byte[] payload) throws RemoteException{
			
			pointers[packet_id] = payload;
			if(!IntentUtil.battery_test)
				timestamps[packet_id] = System.nanoTime();
	        received++;
	        if(packet_id == times-1){
	        	//this.mService.profile_one();
	        	ReceiverUtil.logTimestamp(me, test_id, timestamps, receiver_id);
	        	
	        }
		}
		IProfileService mService = null;
	    @Override
		public void warm_up(int test_id, int times, IProfileService mService) throws RemoteException {
			/** init SystemLog **/
	    	VMRuntime.getRuntime().setMinimumHeapSize(16*1024*1024);
			this.times = times;
			timestamps = new Long[times];
			pointers = new byte[times][];
			this.test_id = test_id;
			this.mService = mService;
			System.gc();
			
		}

		@Override
		public void ipc_test_byte_array(int packet_id, byte[] payload)
				throws RemoteException {
			received(packet_id, payload);
			//System.gc();
			
			//mService.profile_one();
			
			
		}

		@Override
		public void ipc_test_int_array(int packet_id, int[] payload)
				throws RemoteException {
			//received(packet_id);
			
		}

		@Override
		public void ipc_test_long_array(int packet_id, long[] payload)
				throws RemoteException {
			//received(packet_id);
			
		}

		@Override
		public void ipc_test_float_array(int packet_id, float[] payload)
				throws RemoteException {
			//received(packet_id);
			
		}

		@Override
		public void ipc_test_double_array(int packet_id, double[] payload)
				throws RemoteException {
			//received(packet_id);
			
		}
		
    };

   
}
