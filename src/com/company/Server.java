package com.company;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server implements Runnable {

    static private ServerSocket server;
    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;

    @Override
    public void run() {
        try {
            server = new ServerSocket(1234, 2);
            while(true){
                connection = server.accept();
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                String workString = (String)input.readObject();
                String title_of_file = workString.substring(0, workString.indexOf("//"));   // получаем индекс разделителя, и получаем из общей строки имя
//                output.writeObject(" " + (String)input.readObject());
            }
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}
        catch (ClassNotFoundException e) {}
    }
}
