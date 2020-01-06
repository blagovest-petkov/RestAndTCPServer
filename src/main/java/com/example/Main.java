package com.example;

import com.example.tcp.TCPServer;
import com.example.utils.L;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    private static final String TAG = Main.class.getSimpleName();

    public static void main(String[] args) {
        L.log(TAG, "App started");
        SpringApplication.run(Main.class, args);
        TCPServer tcpServer = new TCPServer(4444);
        tcpServer.start();

        // Stop tcp communication on exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            L.log(TAG, "App stopped");
            tcpServer.stop();
        }));
    }
}
