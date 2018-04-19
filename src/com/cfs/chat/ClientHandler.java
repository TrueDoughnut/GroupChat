package com.cfs.chat;

import com.cfs.bots.Bot;
import com.cfs.bots.jikan.JikanBot;
import com.cfs.bots.starwars.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    Server group;

    static int newPort = 1025;

    ArrayList<Bot> bots = new ArrayList<>();

    // constructor
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos, Server group) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
        this.group = group;

        //adding bots
        this.bots.add(new StarWarsBot());
        this.bots.add(new JikanBot());
    }

    private String options =
            "Enter logout to quit, " +
            "nickname for nickname options, " +
            "options for this message, " +
            "group to change groups, " +
            "bots for bot options, " +
            "or message to send a message.";;
    @Override
    public void run() {
        try {
            dos.writeUTF(options);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String received;

        infiniteLoop:
        while (true) {
            try {

                // receive the string
                received = dis.readUTF().toLowerCase().trim().trim();

                System.out.println(received);

                switch (received) {
                    case "logout":
                        logout();
                        break infiniteLoop;

                    //nicknames
                    case "nickname":
                        nickname();
                        break;

                    //re-print options
                    case "options":
                        dos.writeUTF(options);
                        break;

                    //groups
                    case "group":
                        group();
                        break;

                    //Messages
                    case "message":
                        message();
                        break;

                    //Bots
                    case "bots":
                        bots();
                        break;

                    //catch any other input
                    default:
                        defaultFunction(received);
                        break;
                }
            } catch (SocketException e) {
                try {
                    s.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    System.out.println("Could not close the connection properly");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            dis.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //switch statement functions
    private void logout() throws IOException {
        this.isloggedin = false;
        this.s.close();
    }

    private void nickname() throws IOException {
        String nicknameOptions;
        dos.writeUTF("Enter list for a list of users, " +
                "change to change your nickname, or exit to quit.");

        do {
            nicknameOptions = dis.readUTF().toLowerCase().trim();
            switch (nicknameOptions) {
                case "list":
                    dos.writeUTF(this.getNameClients());
                    break;

                case "change":
                    dos.writeUTF("Enter your new nickname.");
                    String tmp = dis.readUTF();

                    //check if name exists
                    for (ClientHandler ch : group.ar) {
                        if (ch.name.equals(tmp)) {
                            dos.writeUTF("That name was taken.");
                        }
                    }
                    this.name = tmp;
                    dos.writeUTF("Your new nickname is: " + this.name);
                    dos.writeUTF(options);
                    return;

                case "exit":
                    dos.writeUTF(options);
                    break;

                default:
                    dos.writeUTF("That's not an option.");
                    break;
            }
        } while (!nicknameOptions.equals("exit"));
    }
    private String getNameClients() {
        String output = "";
        for (ClientHandler ch : group.ar) {
            if (isloggedin) {
                output += ch.name + " (active), ";
            } else {
                output += ch.name + " (inactive), ";
            }
        }
        return output.substring(0, output.length()-2);
    }

    private void group() throws IOException {
        dos.writeUTF("Would you like to join a group (join) or create a new one (create)?");

        String received = dis.readUTF().toLowerCase().trim();

        if (received.equals("join")) {
            int port;
            do {
                dos.writeUTF("Enter the id of the group: ");
                try {
                    port = Integer.parseInt(dis.readUTF());
                    break;
                } catch (NumberFormatException e) {
                    dos.writeUTF("That isn't a valid id");
                    continue;
                }
            } while (true);


            for (Server server : GroupChat.groups) {
                if (server.port == port) {
                    for (Server s1 : GroupChat.groups) {
                        for (int i = 0; i < s1.ar.size(); i++) {
                            if (s1.ar.get(i).equals(this)) {
                                s1.ar.remove(i);
                            }
                        }
                    }
                    this.group = server;
                    server.ar.add(this);
                }
            }
            dos.writeUTF("You are now in a group with port " + this.group.port);
        } else if (received.equals("create")) {
            dos.writeUTF("Creating a new group...");
            //creating new server
            Server newServer;
            newServer = new Server(newPort++);
            new Thread(newServer).start();
            GroupChat.groups.add(newServer);

            //Moving user
            dos.writeUTF("Moving to different group...");
            for (Server server : GroupChat.groups) {
                for (int i = 0; i < server.ar.size(); i++) {
                    if (server.ar.get(i).equals(this)) {
                        server.ar.remove(i);
                    }
                }
            }
            this.group = newServer;
            newServer.ar.add(this);

            dos.writeUTF("You are now in a group with port " + this.group.port);
        } else {
            dos.writeUTF("That is not a valid input. Exiting...\n" + options);
        }
    }

    private void message() throws IOException {
        dos.writeUTF("Enter your messages: ");
        String received;
        do {
            received = dis.readUTF();
            for (ClientHandler mc : group.ar) {
                if (mc.isloggedin && !mc.name.equals(this.name)) {
                    mc.dos.writeUTF(received);
                }
            }
        } while (!received.equals("exit"));

        dos.writeUTF("Exiting...");
        dos.writeUTF(options);
    }

    private void bots() throws IOException {
        writeToAll("Enter list for a list of bots, " +
                "a bot's name for details, " +
                "or exit to quit.");
        String received;
        do {
            received = dis.readUTF().toLowerCase().trim();
            if (received.equals("list")) {
                writeToAll(bots.toString());
            } else if (received.equals("exit")) {
                writeToAll(options);
            } else {
                Bot bot = isBot(received);
                if(bot != null){
                    writeToAll(bot.getInfo());
                } else {
                    writeToAll("That isn't a bot.");
                    writeToAll(bots.toString());
                }
            }
        }while(!received.equals("exit"));
    }
    private Bot isBot(String received){
        for (Bot bot : bots) {
            if (bot.name.equalsIgnoreCase(received)) {
                return bot;
            }
        }
        return null;
    }

    private void defaultFunction(String received) throws IOException {
        if(received.charAt(0) == '!'){
            if(!runBots(received)){
                dos.writeUTF("That bot is not recognized.");
            }
        } else {
            dos.writeUTF("That is not one of your options.\n" + options);
        }
    }

    private boolean runBots(String received) throws IOException {
        for(Bot bot : bots){
            String delimiter = received.split(" ")[0]
                    .substring(1);
            if(bot.delimiter.equals(delimiter)){
                writeToAll(bot.run(received.split(" ")));
                return true;
            }
        }
        return false;
    }

    private void writeToAll(String received) throws IOException {
        for (ClientHandler mc : group.ar) {
            if (mc.isloggedin) {
                if(!mc.name.equals(this.name)) {
                    mc.dos.writeUTF(this.name + ": " + received);
                } else {
                    mc.dos.writeUTF(received);
                }
            }
        }
    }
}
