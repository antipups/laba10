package com.company;

import javax.swing.*;
import java.io.*;
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
                System.out.println("Зашел");
                String title_of_file = workString.substring(0, workString.indexOf("//"));  // получаем индекс разделителя, и получаем из общей строки имя файла
                File f = new File(title_of_file);
                if (f.exists()) f.delete();
                f.createNewFile();
                FileWriter fw = new FileWriter(title_of_file);
                fw.write(workString.substring(workString.indexOf("//") + 2, workString.length()));
                fw.close();
            }
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}
        catch (ClassNotFoundException e) {}
    }
}
