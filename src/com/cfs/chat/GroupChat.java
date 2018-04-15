package com.cfs.chat;


import java.util.*;

public class GroupChat {

    static Vector<Server> groups = new Vector<>();

    public static void main(String[] args){
        Server defaultServer;
        defaultServer = new Server(1024);
        Thread thread = new Thread(defaultServer);
        thread.start();
        groups.add(defaultServer);
    }

}