package com.example.tcp;

import com.example.utils.L;

public class TCPListener implements Runnable {
    //------------------------------------------------------------------------------------------------------------------
    // Constant
    //------------------------------------------------------------------------------------------------------------------
    private static final String TAG = TCPListener.class.getSimpleName();

    //------------------------------------------------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------------------------------------------------
    private TCPCommunication communication;
    private Thread thread;

    //------------------------------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {

        L.log(TAG, "Runnable started");
        while (true) {
            try {
                communication.receive();
                //communication.send("One short tcp response!");

            } catch (Exception e) {
                L.log(TAG, communication.getClass().getSimpleName() + " Listening exception ");
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Public
    //------------------------------------------------------------------------------------------------------------------
    public void start() {
        communication = new TCPCommunication("10.10.45.76", 8019);
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        communication.close();
    }
}
