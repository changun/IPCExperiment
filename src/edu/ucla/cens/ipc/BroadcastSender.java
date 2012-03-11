package edu.ucla.cens.ipc;

import android.content.Intent;
import edu.ucla.cens.ipc.IntentUtil;
public class BroadcastSender extends Sender {

	public BroadcastSender(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public BroadcastSender(){
        super("BroadcastSender");
    }
	@Override
	boolean oneShot(int packet_id, byte[] payload) {
		Intent intent = new Intent(IntentUtil.BROADCAST_TEST_INTENT);

		intent.putExtra("payload", payload);
		intent.putExtra("packet_id", packet_id);
		sendBroadcast(intent);
		return true;
		
	}

	@Override
	boolean oneShot(int packet_id, int[] payload) {
		Intent intent = new Intent(IntentUtil.BROADCAST_TEST_INTENT);

		intent.putExtra("payload", payload);
		intent.putExtra("packet_id", packet_id);
		sendBroadcast(intent);
		return true;
	}

	@Override
	boolean oneShot(int packet_id, long[] payload) {
		Intent intent = new Intent(IntentUtil.BROADCAST_TEST_INTENT);

		intent.putExtra("payload", payload);
		intent.putExtra("packet_id", packet_id);
		sendBroadcast(intent);
		return true;
	}

	@Override
	boolean oneShot(int packet_id, float[] payload) {
		Intent intent = new Intent(IntentUtil.BROADCAST_TEST_INTENT);

		intent.putExtra("payload", payload);
		intent.putExtra("packet_id", packet_id);
		sendBroadcast(intent);
		return true;
	}

	@Override
	boolean oneShot(int packet_id, double[] payload) {
		Intent intent = new Intent(IntentUtil.BROADCAST_TEST_INTENT);

		intent.putExtra("payload", payload);
		intent.putExtra("packet_id", packet_id);
		sendBroadcast(intent);
		return true;
	}

	@Override
	void doWarmUp(int test_id, int times) {
		Intent intent = new Intent();
		intent.putExtra("test_id", test_id);
		intent.putExtra("times", times);
		intent.putExtra("data_type", data_type.name());
		intent.setClass(this, BroadcastReceiver.class);
		startService(intent);
		intent.setClass(this, BroadcastReceiver_1.class);
		startService(intent);
		intent.setClass(this, BroadcastReceiver_2.class);
		startService(intent);
		intent.setClass(this, BroadcastReceiver_3.class);
		startService(intent);


	}

	@Override
	void doCleanUp() {
		Intent intent = new Intent();

		intent.setClass(this, BroadcastReceiver.class);
		stopService(intent);
		intent.setClass(this, BroadcastReceiver_1.class);
		stopService(intent);
		intent.setClass(this, BroadcastReceiver_2.class);
		stopService(intent);
		intent.setClass(this, BroadcastReceiver_3.class);
		stopService(intent);

	}

}
