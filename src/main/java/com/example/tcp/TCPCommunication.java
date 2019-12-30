package com.example.tcp;

import com.example.utils.Logger;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPCommunication {
    //------------------------------------------------------------------------------------------------------------------
    // Constants
    //------------------------------------------------------------------------------------------------------------------
    private static final String TAG = TCPCommunication.class.getSimpleName();
    private static final int BUFFER_RECEIVE_SIZE = 81920;
    private static final int TCP_MTU_SIZE = 1460;
    private static final int SO_TIMEOUT = 10000;

    //------------------------------------------------------------------------------------------------------------------
    // Fields
    //------------------------------------------------------------------------------------------------------------------
    private ServerSocket server;
    private Socket socket;
    private BufferedInputStream in;
    private DataOutputStream out;

    private String address;
    private int port;
    private byte[] receiveData = new byte[BUFFER_RECEIVE_SIZE];

    //------------------------------------------------------------------------------------------------------------------
    // Instance creation
    //------------------------------------------------------------------------------------------------------------------
    public TCPCommunication(String address, int port) {
        this.address = address;
        this.port = port;
        initSocket();
    }

    //------------------------------------------------------------------------------------------------------------------
    // Override
    //------------------------------------------------------------------------------------------------------------------
    public String receive() {
        if (in == null) {
            return null;
        }

        int bytesRead = 0;
        int allBytesRead = 0;

        try {
            do {
                bytesRead = in.read(receiveData, allBytesRead, TCP_MTU_SIZE);

                //if not end of stream
                if (bytesRead != -1) {
                    allBytesRead += bytesRead;
                }
            } while (bytesRead == TCP_MTU_SIZE);

            if (allBytesRead == 0) {
                return null;
            } else {
                String xmlRequest = new String(receiveData, 0, allBytesRead);
                Logger.log(TAG, address, "RECEIVED: " + xmlRequest);
                return xmlRequest;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void send(String xmlResponse) {
        try {
            if (out == null) {
                return;
            }
            Logger.log(TAG, address, "SEND:" + xmlResponse);

            out.write(xmlResponse.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (server != null) server.close();
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.log(TAG, address, "TCP close exception: ");
        }

    }

    public String getAddress() {
        return address;
    }

    //------------------------------------------------------------------------------------------------------------------
    // Private
    //------------------------------------------------------------------------------------------------------------------
    private void initSocket() {
        try {
            server = new ServerSocket(port, 10, InetAddress.getByName(address));

            server.setSoTimeout(SO_TIMEOUT);
            socket = server.accept();

            out = new DataOutputStream(socket.getOutputStream());
            in = new BufferedInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
