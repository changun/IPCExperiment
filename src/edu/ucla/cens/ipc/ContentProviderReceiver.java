package edu.ucla.cens.ipc;


import dalvik.system.VMRuntime;
import edu.ucla.cens.ipc.ReceiverUtil.DATA_TYPE;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;
import java.util.HashMap;

import edu.ucla.cens.systemlog.ISystemLog;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;



public class ContentProviderReceiver extends ContentProvider {
	int receiver_id = 0;
    Long timestamps[];
    int test_id;
    int received = 0;
    int times;
    ReceiverUtil.DATA_TYPE data_type;
    int array_size;
    int sub_packet_id = 0;
    String index[]=new String[1024];
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
    	
        int packet_id = initialValues.getAsInteger("packet_id");
        
        Object payload;
        switch (data_type){
        case byte_array:
        	payload = initialValues.getAsByteArray("payload");break;
        case int_array:
        	for(int i=0;i<array_size;i++)
        		payload = initialValues.getAsInteger(index[i]);break;
        case float_array:
        	for(int i=0;i<array_size;i++)
        		payload = initialValues.getAsFloat(index[i]);break;
        case double_array:
        	for(int i=0;i<array_size;i++)
        		payload = initialValues.getAsDouble(index[i]);break;
        case long_array:
        	for(int i=0;i<array_size;i++)
        		payload = initialValues.getAsLong(index[i]);break;
        }
        timestamps[packet_id] = System.nanoTime();
        received++;
        
        if(packet_id == times-1){
        	ReceiverUtil.logTimestamp(this.getContext(), test_id, timestamps, receiver_id);
        	
        }
    	return null;
    }


	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	String payload;
	MatrixCursor mc;
	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
			
	        
	        return mc;
	    
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		times = arg1.getAsInteger("times");
		timestamps = new Long[times];
		test_id = arg1.getAsInteger("test_id");
		data_type = ReceiverUtil.DATA_TYPE.valueOf(arg1.getAsString("data_type"));
		array_size = arg1.getAsInteger("array_size");
		//if it is query test, we should initialize the return payload
		if(data_type == DATA_TYPE.query)
			payload = new String(new char[array_size/2]);
		for(Integer i=0;i<1024;i++)
			index[i]=i.toString();
		String column[] =  {"c"}; 
        mc = new MatrixCursor(column);
        Object row[] = {payload}; 
        mc.addRow(row);
		System.gc();
		return 0;
	}

    public void onDestroy(){
    	//this.getContext().unbindService(Log.SystemLogConnection);
    }
	@Override
	public boolean onCreate() {
		VMRuntime.getRuntime().setMinimumHeapSize(16*1024*1024);
        //Log.setAppName("IPCTest"); 
        //this.getContext().bindService(new Intent(ISystemLog.class.getName()), Log.SystemLogConnection, Context.BIND_AUTO_CREATE);
        return true;
	}

  
}
