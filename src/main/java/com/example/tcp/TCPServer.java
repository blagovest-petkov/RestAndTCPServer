package com.example.tcp;

import com.example.utils.L;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {

    private static final String TAG = TCPServer.class.getSimpleName();

    //------------------------------------------------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------------------------------------------------
    private int serverPort;

    private PrintWriter out;
    private BufferedReader in;

    private boolean isStarted = false;

    //------------------------------------------------------------------------------------------------------------------
    // Instance creation
    //------------------------------------------------------------------------------------------------------------------
    public TCPServer(int serverPort) {
        this.serverPort = serverPort;
    }


    //------------------------------------------------------------------------------------------------------------------
    // Lifecycle
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void run() {
        L.log(TAG, "Connecting...");
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Socket socket = serverSocket.accept();
            L.log(TAG, "Receiving...");

            try {
                // Sends the message to the client
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                // Read the message received from client
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                while (isStarted) {
                    String message = in.readLine();

                    if (message != null) {
                        System.out.println(message);
                        sendMessage("One short tcp response!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                // The socket must be closed. It is not possible to reconnect to this socket
                // after it is closed, which means a new socket instance has to be created.
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    // Public
    //------------------------------------------------------------------------------------------------------------------
    public void start() {
        isStarted = true;
        new Thread(this).start();
    }

    public void stop() {
        isStarted = false;
    }

    public void sendMessage(final String message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (out != null && !out.checkError() && !StringUtils.isEmpty(message)) {
                    out.println(message);
                    out.flush();
                    L.log(TAG, "SENT: " + message);
                }
            }
        }).start();
    }
}