package edu.ucla.cens.ipc;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;


public class MultipleContentProviderSender extends Sender {
	int packet_id;
	byte[] payload;
	public MultipleContentProviderSender (String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public MultipleContentProviderSender (){
        super("MultipleContentProviderSender");
    }
	int MultipleContentProviderSender;

	String index[]=new String[1024];
	ContentResolver resolver;
	ContentValues values;
	Uri uri_0 = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider");
	Uri uri_1 = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider_1");
	Uri uri_2 = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider_2");
	Uri uri_3 = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider_3");
	
	Thread t0 = new Thread(){
		public void run(){
			ContentValues values = new ContentValues();
			values.put("packet_id", packet_id);
			values.put("payload", payload);
			resolver.insert(uri_0, values);
		}
	};
	Thread t1 = new Thread(){
		public void run(){
			ContentValues values = new ContentValues();
			values.put("packet_id", packet_id);
			values.put("payload", payload);
			resolver.insert(uri_1, values);
		}
	};
	Thread t2 = new Thread(){
		public void run(){
			ContentValues values = new ContentValues();
			values.put("packet_id", packet_id);
			values.put("payload", payload);
			resolver.insert(uri_2, values);
		}
	};
	Thread t3 = new Thread(){
		public void run(){
			ContentValues values = new ContentValues();
			values.put("packet_id", packet_id);
			values.put("payload", payload);
			resolver.insert(uri_3, values);
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

	@Override
	void doWarmUp(int test_id, int times) {
		resolver =  getContentResolver();
		for(Integer i=0;i<1024;i++)
			index[i]=i.toString();
		ContentValues cvalues = new ContentValues();  
        cvalues.put("test_id", test_id);  
        cvalues.put("times", times);
        cvalues.put("data_type", data_type.name());
        cvalues.put("array_size", size);

        Uri uri = Uri.parse("content://edu.ucla.cens.ipc.ContentProvider");
        String empty[] = {""};
        resolver.update(uri, cvalues, "", empty);  
        resolver.update(uri_1, cvalues, "", empty);  
        resolver.update(uri_2, cvalues, "", empty);  
        resolver.update(uri_3, cvalues, "", empty);  
        
	}

	@Override
	void doCleanUp() {
		// TODO Auto-generated method stub
		
	}

}
