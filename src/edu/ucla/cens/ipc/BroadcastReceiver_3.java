package edu.ucla.cens.ipc;

public class BroadcastReceiver_3 extends BroadcastReceiver {
    @Override
    public void onCreate(){
        service = this;
        receiver_id = 3;
    }
}
