package edu.ucla.cens.ipc;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import edu.ucla.cens.systemlog.ISystemLog;
import edu.ucla.cens.systemlog.Log;

public class ReceiverUtil {
	public enum DATA_TYPE{
		byte_array {
		    public String toString() {
		        return "byte[]";
		    }
		},
		int_array {
		    public String toString() {
		        return "int[]";
		    }
		},
		float_array {
		    public String toString() {
		        return "float[]";
		    }
		},
		double_array {
		    public String toString() {
		        return "double[]";
		    }
		},
		long_array {
		    public String toString() {
		        return "long[]";
		    }
		},
		query {
		    public String toString() {
		        return "query";
		    }
		}
	}
	public static void logTimestamp(Context context, int test_id, Long[] timestamps){

        for(int i = 0 ; i < timestamps.length; i++){
        	Log.i("Receiver", String.format("{test_id:%d, packet:%d, timestamp:%d}", test_id, i, timestamps[i]));
        }
		try {
			Log.myFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void logTimestamp(Context context, int test_id, Long[] timestamps, int receiver_id){

        for(int i = 0 ; i < timestamps.length; i++){
        	Log.i("Receiver_"+receiver_id, String.format("{test_id:%d, packet:%d, timestamp:%d}", test_id, i, timestamps[i]));
        }
		try {
			Log.myFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
