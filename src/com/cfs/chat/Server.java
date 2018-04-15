package com.cfs.chat;

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server implements Runnable
{

    // Vector to store active clients
    Vector<ClientHandler> ar = new Vector<>();

    // counter for clients
    int i = 1;

    public final int port;

    public Server(int port)
    {
        this.port = port;
    }

    @Override
    public void run(){
        try {
            // server is listening on given port
            ServerSocket ss = new ServerSocket(port);

            Socket s;

            System.out.println("Accepting client requests...");
            // running infinite loop for getting
            // client request
            while (true) {
                // Accept the incoming request
                s = ss.accept();

                System.out.println("New client request received : " + s);

                // obtain input and output streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                System.out.println("Creating a new handler for this client...");

                // Create a new handler object for handling this request.
                ClientHandler mtch = new ClientHandler(s, "client " + i++, dis, dos, this);

                // Create a new Thread with this object.
                Thread t = new Thread(mtch);

                System.out.println("Adding this client to active client list");

                // add this client to active clients list
                ar.add(mtch);

                // start the thread.
                t.start();

            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}