package edu.ucla.cens.ipc;

import java.io.IOException;
import java.util.Stack;


import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;
import edu.ucla.cens.systemlog.Log;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;



public class JobDispatcher extends Service {
	String curTestDesciption;
	PowerManager.WakeLock wl;
	Stack<TestJob> jobs = new Stack<TestJob>();
	public enum MECHANISM {
		StartService,RemoteFunction, ContentProvider, Broadcast, MultipleRemoteFunction, MultipleContentProvider, MemoryShare
	}
	public void onCreate(){
		
		super.onCreate();
		registerReceiver (this.mTestEnd, new IntentFilter(IntentUtil.TESTEND_INTENT));
		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
    	/** require power lock: we want cpu not stop when screen go off **/
    	PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    	wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "My Tag");
    	wl.acquire();
    	
    	//Different Period
    	
    	for(int k=0;k<2;k++){
    	int periods[] = {500,10000,60000};
    
    	 for(int period : periods){
	    	 
    		//jobs.push(new TestJob(MECHANISM.ContentProvider, period, 1024*256, 20, 10, DATA_TYPE.query));
    		 //jobs.push(new TestJob(MECHANISM.ContentProvider, period, 16, 20, 10, DATA_TYPE.query));
    		//jobs.push(new TestJob(MECHANISM.ContentProvider, period, 1024*2, 20, 10, DATA_TYPE.query));
    		 //jobs.push(new TestJob(MECHANISM.RemoteFunction, period, 16, 20, 10, DATA_TYPE.byte_array));
	    	 //jobs.push(new TestJob(MECHANISM.RemoteFunction, period, 1024*256, 20, 10, DATA_TYPE.byte_array));
	    	 //jobs.push(new TestJob(MECHANISM.RemoteFunction, period, 1024*2, 20, 10, DATA_TYPE.byte_array));
	    	 //jobs.push(new TestJob(MECHANISM.MemoryShare, period, 1024*256, 20, 10,  DATA_TYPE.byte_array));
	    	 //jobs.push(new TestJob(MECHANISM.MemoryShare, period, 1024*2, 20, 10,  DATA_TYPE.byte_array));
	    		
		    	 
		    	
		    	// jobs.push(new TestJob(MECHANISM.MemoryShare, period, 16, 20, 10,  DATA_TYPE.byte_array));
		    	 //jobs.push(new TestJob(MECHANISM.StartService, period, 1024*256, 20, 10, DATA_TYPE.byte_array));
		    	 //jobs.push(new TestJob(MECHANISM.StartService, period, 16, 20, 10, DATA_TYPE.byte_array));
			    	// jobs.push(new TestJob(MECHANISM.StartService, period, 1024*2, 20, 10, DATA_TYPE.byte_array));

			    
    	 }
    	
    	
    	//Large Size
    	
    	
    	 
    	 
    	 int sizes2[] =  {4, 8, 16, 32,  64, 128, 256, 512,  1024,2048, 4*1024, 8*1024, 16*1024, 32*1024,64*1024, 128*1024, 256*1024};
    	 //int sizes2[] =  { 256*1024};
    	 for(int s : sizes2){
	    	 
	    	 
	    	 //jobs.push(new TestJob(MECHANISM.ContentProvider, 1000, s, 50, 2, DATA_TYPE.query));
	    	 //jobs.push(new TestJob(MECHANISM.ContentProvider, 1000, s, 50, 2, DATA_TYPE.byte_array));
	     	 
	    	 //jobs.push(new TestJob(MECHANISM.RemoteFunction, 1000, s, 50, 2, DATA_TYPE.byte_array));
    		 //jobs.push(new TestJob(MECHANISM.MemoryShare, 1000, s, 50, 2, DATA_TYPE.byte_array));
    		 //if(s<= 256*1024)
    		jobs.push(new TestJob(MECHANISM.StartService, 1000, s, 50, 2, DATA_TYPE.byte_array));
    	 }
	 
	 
    	 
    	
    	/*
    	//Multiple Receiver
    	
    	 //jobs.push(new TestJob(MECHANISM.Broadcast, 1000, 1024, 20, 4, DATA_TYPE.byte_array));
	     jobs.push(new TestJob(MECHANISM.MultipleContentProvider, 1000, 1024, 20, 4, DATA_TYPE.byte_array));
	     jobs.push(new TestJob(MECHANISM.MultipleRemoteFunction, 1000, 1024, 20, 4, DATA_TYPE.byte_array));
	  

	    //jobs.push(new TestJob(MECHANISM.Broadcast, 1000, 4, 20, 4, DATA_TYPE.byte_array));
		jobs.push(new TestJob(MECHANISM.MultipleContentProvider, 1000, 4, 20, 4, DATA_TYPE.byte_array));
		jobs.push(new TestJob(MECHANISM.MultipleRemoteFunction, 1000, 4, 20, 4, DATA_TYPE.byte_array));
		*/
    	 
    	}
    	
	     dispatchJob(jobs.pop());
		return Service.START_STICKY;
    	
		
	}
	
	Intent jobIntent;
	void dispatchJob(TestJob job){
    	/** create Intent send to TesterActivities **/
    	jobIntent=new Intent();
    	Bundle bundle=new Bundle();
    	int test_id = getTest_id();
    	bundle.putInt("test_id", test_id);
    	bundle.putInt("period", job.period);
    	bundle.putInt("size", job.size);
    	bundle.putInt("times", job.times);
    	bundle.putInt("sampleRate", job.sampleRate);
    	bundle.putString("data_type", job.data_type.name());
    	jobIntent.putExtras(bundle);
    	String mechanism;
    	/** use View.id to tell which button is clicked **/
    	switch(job.mech){
	    	case StartService:
	    		mechanism = "Intent";
	    		jobIntent.setClass(this, StartServiceSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case RemoteFunction:
	    		mechanism = "FixedBinder";
	    		jobIntent.setClass(this, RemoteFunctionSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case MultipleRemoteFunction:
	    		mechanism = "MultipleRemoteFunction";
	    		jobIntent.setClass(this, MultipleRemoteFunctionSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case ContentProvider:
	    		mechanism = "ContentProvider";
	    		jobIntent.setClass(this, ContentProviderSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case MultipleContentProvider:
	    		mechanism = "MultipleContentProvider";
	    		jobIntent.setClass(this, MultipleContentProviderSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case Broadcast:
	    		mechanism = "Broadcast";
	    		jobIntent.setClass(this, BroadcastSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	case MemoryShare:
	    		mechanism = "MemoryShare";
	    		jobIntent.setClass(this, MemoryShareSender.class);
	    		this.startService(jobIntent);
	    		break;
	    	default: 
	    		return;
    	}
    	curTestDesciption = String.format("{test_id:%d, mechanism:%s, sample_rate:%d, period:%d, times:%d, packet_size:%d, packet_type:\"%s\"}",test_id,  mechanism, job.sampleRate, job.period, job.times, job.size, job.data_type);
    	
    }
	  public BroadcastReceiver mTestEnd = new BroadcastReceiver(){

			@Override
			public void onReceive(final Context arg0, Intent arg1) {
				final int end_test_id = arg1.getIntExtra("test_id", -1);
				if(end_test_id != -1){


				     /** write the log **/
		        	Log.i("Test", curTestDesciption);
		    		try {
		    			Log.myFile.flush();
		    		} catch (IOException e) {
		    			// TODO Auto-generated catch block
		    			e.printStackTrace();
		    		}
					 Intent i = new Intent(IntentUtil.JOBINFO_INTENT);
					 i.putExtra("info", String.format("Test_ID:%1$d done. %2$d Jobs left in the stack", end_test_id, jobs.size()));
				     sendBroadcast(i);
				     stopService(jobIntent);
				     
				     
				}
				ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				 for(RunningAppProcessInfo info:activityManager.getRunningAppProcesses()){
					 if(info.processName.startsWith("edu.ucla.cens.ipc.sender") || 
						info.processName.startsWith("edu.ucla.cens.ipc.receiver") ||
						info.processName.startsWith("edu.ucla.cens.ipc.profile"))
						 android.os.Process.killProcess(info.pid);
				 }	
				 

				if(jobs.size() > 0){

					dispatchJob(jobs.pop());
				}
				else{

			    	 wl.release();
			    	 
				}
					
				
			}
		  };
		   @Override
		    public void onDestroy() {
		        super.onDestroy();

		        // Do not forget to unregister the receiver!!!
		        unregisterReceiver(this.mTestEnd);
		    }
		    public static class TestJob {
				public MECHANISM mech;
				public int period;
				public int size;
				public int times;
				public int sampleRate;
				public ReceiverUtil.DATA_TYPE data_type;
				public TestJob(MECHANISM mech, int period, int size, int times, int sampleRate, ReceiverUtil.DATA_TYPE data_type) {
					this.mech = mech;
					this.period = period;
					this.size = size;
					this.times = times;
					this.sampleRate = sampleRate;
					this.data_type = data_type;
				}
			}
		    int getTest_id(){
		        SharedPreferences settings = getSharedPreferences("IPC_TEST", 0);
		        int test_id = settings.getInt("TEST_ID", -1) + 1;
		        settings.edit()
		        .putInt("TEST_ID", test_id)
		        .commit();
		        return test_id;
		    }
			@Override
			public IBinder onBind(Intent arg0) {
				// TODO Auto-generated method stub
				return null;
			}

		    
}
