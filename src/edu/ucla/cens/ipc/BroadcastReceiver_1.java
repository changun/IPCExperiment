package edu.ucla.cens.ipc;

public class BroadcastReceiver_1 extends BroadcastReceiver {
    @Override
    public void onCreate(){
        service = this;
        receiver_id = 1;
    }
}
