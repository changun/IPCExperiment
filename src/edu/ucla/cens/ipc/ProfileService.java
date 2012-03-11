package edu.ucla.cens.ipc;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Debug;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

public class ProfileService extends Service{
	 
		 ArrayList<Profile> profiles = new ArrayList<Profile>(1000);
		 //ArrayList<MemoryInfo> systemMemInfo = new ArrayList<MemoryInfo>();
		 int test_id;
		 int sampleRate;
		 String TAG;
		 int battery_level;
		 int senderPID, receiverPID;
		 int pids[];

		    boolean startServiceReply = false;		
		    

		 Profile profileOne(int pids[]){
			 //System.gc();
			 Profile profile = new Profile();

    		 //profile.timestamp = System.nanoTime();
    		 
			 //get memory usage
    		 //ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    		 //android.os.Debug.MemoryInfo[] mem_usage = activityManager.getProcessMemoryInfo(pids);
			 /*profile.sender_memory_usage = mem_usage[0].getTotalPss() + mem_usage[0].getTotalPrivateDirty();
			 profile.receiver_memory_usage = mem_usage[1].getTotalPss() + mem_usage[1].getTotalPrivateDirty();
			 profile.manager_memory_usage =  mem_usage[2].getTotalPss()+ mem_usage[2].getTotalPrivateDirty();
			 //get GC information
			 GC_info gc_info[] = gc_information(pids);
			 profile.sender_gcs = gc_info[0].times;
			 profile.sender_gc_bytes = gc_info[0].total_byte;
			 profile.sender_gc_time = gc_info[0].latency;
			 

			 profile.receiver_gcs = gc_info[1].times;
			 profile.receiver_gc_bytes = gc_info[1].total_byte;
			 profile.receiver_gc_time = gc_info[1].latency;
			 
			 profile.manager_gcs = gc_info[2].times;
			 profile.manager_gc_bytes = gc_info[2].total_byte;
			 profile.manager_gc_time = gc_info[2].latency;
			 */
			 
			 //clearLog();
			 
			 //get system free memory
			//container for system memory info
			 /*ActivityManager.MemoryInfo outInfo;
			 outInfo = new ActivityManager.MemoryInfo(); 
			 activityManager.getMemoryInfo(outInfo);
			 profile.system_free_memory = outInfo.availMem;
			 */
			 
			 
			 
			 //get cpu usage. the first element is sender's usage, the other is receiver
			 /*
			 Long[] cpu_usages;
			 cpu_usages = cpu_usage(senderPID);
			 profile.sender_user_cpu_jiffy = cpu_usages[0];
			 profile.sender_system_cpu_jiffy = cpu_usages[1];
			 cpu_usages = cpu_usage(receiverPID);
			 profile.receiver_user_cpu_jiffy = cpu_usages[0];
			 profile.receiver_system_cpu_jiffy = cpu_usages[1];
			 cpu_usages = cpu_usage(pids[2]);
			 profile.manager_user_cpu_jiffy= cpu_usages[0];
			 profile.manager_system_cpu_jiffy = cpu_usages[1];
			 */
			 if(IntentUtil.battery_test){
				 Intent batteryIntent = this.getApplicationContext().registerReceiver(null,
		                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
				int rawlevel = batteryIntent.getIntExtra("level", -1);
				double scale = batteryIntent.getIntExtra("scale", -1);
				double level = -1;
				if (rawlevel >= 0 && scale > 0) {
				    level = rawlevel / scale;
				}
				profile.battery_state = (float) level;
			 }
			 return profile;
		 }
		 private final IProfileService.Stub mBinder = new IProfileService.Stub(){

			@Override
			public void init(int t_id) throws RemoteException {
				 pids =  getPids();
				 senderPID = pids[0];
				 receiverPID = pids[1];
				 test_id = t_id;
				 //clearLog();

				 final Runtime runtime = Runtime.getRuntime();
				 try {
					 Process proc = Runtime.getRuntime().exec("su");
					 DataOutputStream standard_in = new DataOutputStream(proc.getOutputStream());
					 DataInputStream standard_out = new DataInputStream(proc.getInputStream());
				     proc=runtime.exec("su"); //or whatever command.
				     //standard_in.writeBytes("rmmod kretprobe_example\n");
				     
				      //standard_in.writeBytes("/mnt/sdcard/busybox insmod /mnt/sdcard/probe.ko func=\"binder_flush,binder_free_buf,binder_update_page_range,binder_transaction,binder_ioctl,binder_thread_write,binder_thread_read\" pids=\""+ pids[0]+","+pids[1]+","+pids[2]+"\"\n");
				     standard_in.writeBytes("dmesg -c\n");
				     //standard_in.writeBytes("echo \""+pids[0]+" "+pids[1]+" n\" > /proc/binder/exp\n");
				     
				     standard_in.writeBytes("echo \"0 0 "+ (IntentUtil.optimized_binder? "y":"n")+"\" > /proc/binder/exp\n");
				     
				 }

				 catch (IOException e) { 
				     e.printStackTrace(); 
				 }					

				
			}
			float last_state=-1;
			@Override
			public void profile_one() throws RemoteException {
				Profile p = profileOne(pids);
				if(p.battery_state != last_state || ! IntentUtil.battery_test){
				 Log.i("Profile",p.toString());
				 last_state = p.battery_state;
				}

				
			}

			@Override
			public void end() throws RemoteException {
				 try {
					Log.myFile.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 /*
				 final Runtime runtime = Runtime.getRuntime();
				 try {
					 Process proc = Runtime.getRuntime().exec("su");
					 DataOutputStream standard_in = new DataOutputStream(proc.getOutputStream());
					 DataInputStream standard_out = new DataInputStream(proc.getInputStream());
				     proc=runtime.exec("su"); //or whatever command.
				     //standard_in.writeBytes("rmmod kretprobe_example\n");
				     
				      //standard_in.writeBytes("/mnt/sdcard/busybox insmod /mnt/sdcard/probe.ko func=\"binder_flush,binder_free_buf,binder_update_page_range,binder_transaction,binder_ioctl,binder_thread_write,binder_thread_read\" pids=\""+ pids[0]+","+pids[1]+","+pids[2]+"\"\n");
				     standard_in.writeBytes("dmesg > /sdcard/"+test_id+".log\n");
				     //standard_in.writeBytes("echo \""+pids[0]+" "+pids[1]+" n\" > /proc/binder/exp\n");
				     
				 }
				 catch (IOException e) { 
				     e.printStackTrace(); 
				 }	*/		
				
			}

			@Override
			public void reply() throws RemoteException {
				startServiceReply = true;
				
			}
			 
		 };
		public int onStartCommand (Intent intent, int flags, int startId) {
			 /*if(intent.getAction().equals("init") || intent.getAction().equals("profile_one")){
				
			 }
			 else if(intent.getAction().equals("profile_one"))
					 profiles.add(profileOne(pids));
			 else if(intent.getAction().equals("start"))
				 profilingThread.start();
			 else if(intent.getAction().equals("end")){
	
				
				 for(Profile p: profiles){
					 Log.i("Profile", p.toString());
				}
				try {
					Log.myFile.flush();
					Log.myFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        //stopSelf();
	        }
			 */
			 return START_STICKY;
		}
	    /**
	     * cpu_usage
	     * 
	     * read the /proc/PID/stat file to retreive the current cpu usage of corresponding process
	     * 
	     * 
	     * @param pid
	     * @return a pair of long numbers which indicate the process's user mode and system mode cpu usages
	     */
	    public Long[] cpu_usage(int pid){
	    	 Long cpu_usages[] = new Long[2];
	    	 cpu_usages[0] = (long) -1;
	    	 cpu_usages[1] = (long) -1;
			try {
				File file = new File("/proc/" + pid,  
	                    "stat");  
				FileInputStream fis = new FileInputStream(file);
	    	    // if file the available for reading
	    	    if (fis != null) {
	    	      // prepare the file for reading
	    	      InputStreamReader inputreader = new InputStreamReader(fis);
	    	      BufferedReader buffreader = new BufferedReader(inputreader,200);
	    	      String items[] = buffreader.readLine().split(" ");
	    	      cpu_usages[0] = Long.parseLong(items[13]);
	    	      cpu_usages[1] = Long.parseLong(items[14]);
	    	      return cpu_usages;
	    	    }
			} catch (IOException e) {
				//it could happen because the process is dead, so don't worry.....
			}
			return cpu_usages;
	    	    
	    }
	    public GC_info[] gc_information(int pids[]){

    	    Process process;
    	    BufferedReader bufferedReader;

			GC_info infos[] = new GC_info[3];
			for(int i=0 ; i<3 ;i++){
				infos[0] = new GC_info();
				infos[1] = new GC_info();
				infos[2] = new GC_info();
				
			}
			try {
				process = Runtime.getRuntime().exec(String.format("logcat -d  dalvikvm:D *:S"));
				bufferedReader = new BufferedReader(
	    	    new InputStreamReader(process.getInputStream()), 2048);
				String pattern0 = String.format("D/dalvikvm(%1$5d): GC", pids[0]);
				String pattern1 = String.format("D/dalvikvm(%1$5d): GC", pids[1]);
				String pattern2 = String.format("D/dalvikvm(%1$5d): GC", pids[2]);
				
				while(true){
					String line = bufferedReader.readLine();
					if(line == null)
						break;
					if(line.startsWith("D/dalvikvm")){
						if(line.startsWith(pattern0)){
							infos[0].total_byte += Integer.parseInt(line.substring(19).split(" ")[5]);
							infos[0].latency  += Integer.parseInt(line.substring(19).split(" ")[8].split("m")[0]);
							infos[0].times ++;
						}
						if(line.startsWith(pattern1)){
							infos[1].total_byte += Integer.parseInt(line.substring(19).split(" ")[5]);
							infos[1].latency  += Integer.parseInt(line.substring(19).split(" ")[8].split("m")[0]);
							infos[1].times ++;
						}
						if(line.startsWith(pattern2)){
							infos[2].total_byte += Integer.parseInt(line.substring(19).split(" ")[5]);
							infos[2].latency  += Integer.parseInt(line.substring(19).split(" ")[8].split("m")[0]);
							infos[2].times ++;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return infos;
	    	    
	    }

	    public void clearLog(){

    	    
			try {
				Process process = Runtime.getRuntime().exec("logcat -c");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	    
	    }
	    public int[] getPids(){
	    	  int pids[] = new int[]{0,0,0};
			 /** retreive process PID **/
			 while(true){
		    	  ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
				 for(RunningAppProcessInfo info:activityManager.getRunningAppProcesses()){
					 if(info.processName.startsWith(this.getPackageName() + ".sender"))
						pids[0]= info.pid;
					 else if(info.processName.startsWith(this.getPackageName() + ".receiver"))
						 pids[1] = info.pid;
					 else if(info.processName.startsWith("system_server"))
						 pids[2] = info.pid;
				 }
				 if(pids[0]!=0 && pids[1]!=0)
					 break;
					 
			 }
			 pids[2] = 2507;
			 return pids;
			 
	    }
		@Override
		public IBinder onBind(Intent arg0) {

			return this.mBinder;
		}
		class GC_info{
			int total_byte=0;
			int latency=0;
			int times=0;
		}
		public class Profile{
			int sender_memory_usage;
			long sender_system_cpu_jiffy;
			long sender_user_cpu_jiffy;
			
			int receiver_memory_usage;
			long receiver_system_cpu_jiffy;
			long receiver_user_cpu_jiffy;
			
			int manager_memory_usage;
			long manager_system_cpu_jiffy;
			long manager_user_cpu_jiffy;
			
			float battery_state;
			
			
			int receiver_gc_bytes;
			int receiver_gc_time;
			int receiver_gcs;
			
			
			int sender_gc_bytes;
			int sender_gc_time;
			int sender_gcs;
			
			
			int manager_gc_bytes;
			int manager_gc_time;
			int manager_gcs;
			
			
			long timestamp;
			long system_free_memory;

			public Profile() {

			}
			public String toString(){
				if(IntentUtil.battery_test){
					return String.format("{test_id:%d, timestamp:%d,  battery:%f}",
						 test_id, this.timestamp,  this.battery_state);
				}
			 
				return String.format("{test_id:%d, timestamp:%d, sender_user_cpu:%d, sender_system_cpu:%d, sender_mem:%d, receiver_user_cpu:%d, receiver_system_cpu:%d, receiver_mem:%d, manager_user_cpu:%d, manager_system_cpu:%d, manager_mem:%d, system_free_mem:%d, receiver_gc_bytes:%d, receiver_gc_time:%d, receiver_gcs:%d, sender_gc_bytes:%d, sender_gc_time:%d, sender_gcs:%d, manager_gc_bytes:%d, manager_gc_time:%d, manager_gcs:%d, battery:%f}",
						 test_id, this.timestamp, this.sender_user_cpu_jiffy, this.sender_system_cpu_jiffy, this.sender_memory_usage, this.receiver_user_cpu_jiffy, this.receiver_system_cpu_jiffy, this.receiver_memory_usage, this.manager_user_cpu_jiffy, this.manager_system_cpu_jiffy, this.manager_memory_usage,this.system_free_memory, this.receiver_gc_bytes, this.receiver_gc_time, this.receiver_gcs,  this.sender_gc_bytes, this.sender_gc_time, this.sender_gcs, this.manager_gc_bytes, this.manager_gc_time, this.manager_gcs, this.battery_state);
			 
			}
			
		
		}
}