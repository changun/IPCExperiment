package edu.ucla.cens.systemlog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;



public class Log
{
    private static final String DEFAULT_APP_NAME = "default";
    private static final String TAG = "CENS.SystemLog";

	private static ISystemLog sLogger;
	
	private static boolean sConnected = false;

    private static String sAppName = DEFAULT_APP_NAME;

    private static String sUserId;

    public static void setAppName(String name)
    {
        sAppName = name;
    }


		
    public static ServiceConnection SystemLogConnection 
        = new ServiceConnection() 
    {
        public void onServiceConnected(ComponentName className, 
                IBinder service) 
        {
            sLogger = ISystemLog.Stub.asInterface(service);
            sConnected = true;
        }

        public void onServiceDisconnected(ComponentName className) 
        {
            sLogger = null;
            sConnected = false;
        }
    };
    
    public static void register(String tag) 
    {
    	if (sConnected)
    	{
	    	try
	    	{
	    		sLogger.registerLogger(tag, sAppName);
	    	} 
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, 
                        "Remote Exception when trying to register tag"
                        + tag, re);
	    	}
    	}
		else
		{
			android.util.Log.i(TAG, 
                    "Not connected to SystemLog. Could not register "
                    + tag);
		}    	
    }


    public static boolean isConnected()
    {
        return sConnected;
    }

    public static boolean isRegistered(String tag)
    {
    	boolean res = false;
    	if (sConnected)
    	{
	    	try
	    	{
	    		res =  sLogger.isRegistered(tag);
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    		res =  false;
	    	}
    	}
    	else
    	{
    		android.util.Log.e(tag, "Not connected");
    		res = false;
    	}
    	return res;
    }

    static public FileWriter myFile;
    public static void i (String tag, String message)
    {
    	File SDCardpath = Environment.getExternalStorageDirectory();
    	 //判斷SD卡是否存在
        if((myFile == null  && !Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED) )){
            try {
             //取得SD卡路徑
                
                //將資料寫入到SD卡
                myFile = new FileWriter( SDCardpath.getAbsolutePath() + "/" + tag, true  );
                
                
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
        }
        try {
			myFile.append(message+"\n");
		} catch (IOException e) {
			try {
				myFile = new FileWriter( SDCardpath.getAbsolutePath() + "/" + tag, true  );
				
				myFile.append(message+"\n");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
        return;
    }
    
    public static void d (String tag, String message)
    {
    	if (sConnected)
    	{
	    	try
	    	{
                if (!sLogger.isRegistered(tag))
                    register(tag);

	    		sLogger.debug(tag, message);
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    	}
    	}
		else
		{
			android.util.Log.d(tag, message);
		}
    }
    

    
    public static void e (String tag, String message, Exception e)
    {
    	if (sConnected)
    	{
	    	try
	    	{
                if (!sLogger.isRegistered(tag))
                    register(tag);

	    		sLogger.error(tag, message + e.getMessage());
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    	}
    	}
    	else
    	{
    		android.util.Log.e(tag, message, e);
    	}
    }


    public static void e (String tag, String message)
    {
    	if (sConnected)
    	{
	    	try
	    	{
                if (!sLogger.isRegistered(tag))
                    register(tag);

	    		sLogger.error(tag, message);
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    	}
    	}
    	else
    	{
    		android.util.Log.e(tag, message);
    	}
    }



    public static void v (String tag, String message)
    {
    	if (sConnected)
    	{
	    	try
	    	{
                if (!sLogger.isRegistered(tag))
                    register(tag);

	    		sLogger.verbose(tag, message);
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    	}
    	}
    	else
    	{
    		android.util.Log.v(tag, message);
    	}
    }


    public static void w (String tag, String message)
    {
    	if (sConnected)
    	{
	    	try
	    	{
                if (!sLogger.isRegistered(tag))
                    register(tag);

	    		sLogger.warning(tag, message);
	    	}
	    	catch (RemoteException re)
	    	{
	    		android.util.Log.e(TAG, "Remote Exception", re);
	    	}
    	}
    	else
    	{
    		android.util.Log.w(tag, message);
    	}
    }


}