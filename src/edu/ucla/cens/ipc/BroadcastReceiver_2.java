package edu.ucla.cens.ipc;

public class BroadcastReceiver_2 extends BroadcastReceiver {
    @Override
    public void onCreate(){
        service = this;
        receiver_id = 2;
    }
}
