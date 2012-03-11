package edu.ucla.cens.ipc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import edu.ucla.cens.ipc.R;
import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class IPCExperiementActivity extends Activity {
	
	EditText periodText;
	EditText sizeText;
	EditText timesText;
	EditText sampleRateText;
	Button startServiceButton;
	Button remoteFunctionButton;
	Button contentProviderButton;
	
	Activity activity;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        /** Initialize the layout **/
        setContentView(R.layout.main);
        periodText = (EditText) findViewById(R.id.Period_Text);
        sizeText = (EditText) findViewById(R.id.Size_Text);
        timesText = (EditText) findViewById(R.id.Times_Text);
        sampleRateText = (EditText) findViewById(R.id.SampleRate_Text);
    	startServiceButton = (Button) findViewById(R.id.StartService_Button);
    	remoteFunctionButton = (Button) findViewById(R.id.RemoteFunction_Button);
    	contentProviderButton = (Button) findViewById(R.id.ContentProvider_Button);
    	
    	/** set listener to the buttons **/
    	startServiceButton.setOnClickListener(buttonOnClickListener);
    	remoteFunctionButton.setOnClickListener(buttonOnClickListener);
    	contentProviderButton.setOnClickListener(buttonOnClickListener);
    	contentProviderButton.setOnClickListener(buttonOnClickListener);
    	
    	/** set litsener to run stack job button **/
    	findViewById(R.id.RunStackJobs_Button).setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        	startService(new Intent(activity, JobDispatcher.class));
        }});
    	/** set JobInfo Receiver to receive current info**/
    	registerReceiver (this.mJobInfo, new IntentFilter(IntentUtil.JOBINFO_INTENT ));

     }
	  public BroadcastReceiver mJobInfo = new BroadcastReceiver(){
			@Override
			public void onReceive(final Context arg0, Intent arg1) {
				activity.setTitle(arg1.getStringExtra("info"));
			}
	  };
	  
    	


    View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
        	
        	/** read setting from UI **/
        	int period = Integer.parseInt(periodText.getText().toString());
        	int size = Integer.parseInt(sizeText.getText().toString());
        	int times = Integer.parseInt(timesText.getText().toString());
        	int sampleRate = Integer.parseInt(sampleRateText.getText().toString());
        	
        	runOnUiThread(new Runnable() { 
		        public void run() 
		        {
		        	startServiceButton.setEnabled(false);
		        	remoteFunctionButton.setEnabled(false);
		        	contentProviderButton.setEnabled(false);
		         } 
		    }); 
        	
        	//dispatchJob(new TestJob(v.getId(), period, size, times, sampleRate, DATA_TYPE.byte_array));
        	
        }
    };


}
