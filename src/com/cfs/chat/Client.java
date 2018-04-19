package com.cfs.chat;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.*;
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


    JEditorPane jEditorPane;
    JTextField tx;
    SimpleAttributeSet attributeSet;
    StyledDocument doc;
    EditorKit editorKit;

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

        jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        jEditorPane.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));
        editorKit = jEditorPane.getEditorKit();

        jEditorPane.addHyperlinkListener(e -> {
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
                if(Desktop.isDesktopSupported()){
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch(URISyntaxException|IOException exception){
                        exception.printStackTrace();
                    }
                }
            }
        });

        doc = (StyledDocument) jEditorPane.getDocument();
        attributeSet = new SimpleAttributeSet();
        StyleConstants.setFontFamily(attributeSet, "Segoe UI");
        StyleConstants.setFontSize(attributeSet, 14);

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        p2.add(jScrollPane, BorderLayout.CENTER);
        p2.add(p1, BorderLayout.SOUTH);

        b1.addActionListener(this);
        tx.addActionListener(this);

        f.setContentPane(p2);

        Thread receiveMessage = new Thread(() -> {
            while (true) {
                try {
                    String received = dis.readUTF();
                    urlParsing(received);
                } catch (IOException|BadLocationException exception) {
                    exception.printStackTrace();
                    System.exit(1);
                }
            }
        });
        receiveMessage.start();

        f.setVisible(true);

        //run on close
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                receiveMessage.interrupt(), "Shutdown thread"));
    }

    @Override
    public void run(){
        createAndShowGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try{
            dos.writeUTF(tx.getText());
        } catch(IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
        //push out input
        try {
            urlParsing(tx.getText());
        } catch(BadLocationException|IOException exception){
            exception.printStackTrace();
        }
        //scroll text area
        jEditorPane.setCaretPosition(jEditorPane.getDocument().getLength());
        //clear text field
        tx.setText("");
    }

    private void urlParsing(String received) throws BadLocationException, IOException {
        String output = "";
        if(received.indexOf("http") >= 0){
            String[] arr = received.split(" ");
            for(int i = 0; i < arr.length; i++){
                if(arr[i].substring(0, 4).equalsIgnoreCase("http")){
                    for(int j = 0; j < i; j++){
                        output += arr[j] + " ";
                    }
                    output += "<a href=\"" + arr[i] + "\">" +  arr[i] + "</a>" + " ";
                    for(int j = i+1; j < arr.length; j++){
                        output += arr[j] + " ";
                    }
                }
            }
            editorKit.read(new StringReader(output), doc, doc.getLength());
        } else {
            doc.insertString(doc.getLength(), received + "\n", attributeSet);
        }
    }
}
