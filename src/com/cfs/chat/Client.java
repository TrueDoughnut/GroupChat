package com.cfs.chat;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Client
{
    static int ServerPort = 1024;

    public static void main(String args[]) throws IOException
    {
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("DESKTOP-ORMD9OR");

        // establish the connection
        Socket s = new Socket(ip, ServerPort);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        (new Thread(new GUI(dis, dos))).start();
    }
}

class GUI implements Runnable, ActionListener{

    DataInputStream dis;
    DataOutputStream dos;

    public GUI(DataInputStream dis, DataOutputStream dos){
        this.dis = dis;
        this.dos = dos;
    }


    JTextArea ta;
    JTextField tx;

    public void createAndShowGUI(){
        JFrame f = new JFrame("my chat");
        f.setSize(600,600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setResizable(false);

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());

        tx = new JTextField();
        p1.add(tx, BorderLayout.CENTER);

        JButton b1 = new JButton("Send");
        p1.add(b1, BorderLayout.EAST);

        ta = new JTextArea();
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);

        JScrollPane jScrollPane = new JScrollPane(ta);
        p2.add(jScrollPane, BorderLayout.CENTER);
        p2.add(p1, BorderLayout.SOUTH);

        b1.addActionListener(this);
        tx.addActionListener(this);

        f.setContentPane(p2);

        Thread receiveMessage = new Thread(() -> {
            while (true) {
                try {
                    String received = dis.readUTF();
                    ta.append(received + "\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                }
            }
        });
        receiveMessage.start();

        f.setVisible(true);

        //run on close
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            @Override
            public void run(){
                receiveMessage.interrupt();
            }
        }, "Shutdown thread"));
    }

    @Override
    public void run(){
        createAndShowGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            dos.writeUTF(tx.getText());
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
        //push out input
        ta.append(tx.getText() + "\n");
        //scroll text area
        ta.setCaretPosition(ta.getDocument().getLength());
        //clear text field
        tx.setText("");
    }
}
