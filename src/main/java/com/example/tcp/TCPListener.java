package com.example.tcp;

import com.example.utils.Logger;

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
    // Instance creation
    //------------------------------------------------------------------------------------------------------------------
    public TCPListener() {
        // TODO: 30.12.19 address and port
        communication = new TCPCommunication("", 1234);
        thread = new Thread(this);
    }

    //------------------------------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        try {
            Logger.log(TAG, "TCP RECEIVED: " + communication.receive());

        } catch (Exception e) {
            Logger.log(TAG, communication.getAddress(), communication.getClass().getSimpleName() + " Listening exception ");
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Public
    //------------------------------------------------------------------------------------------------------------------
    public void start() {
        communication = new TCPCommunication("", 1234);
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        communication.close();
    }
}
