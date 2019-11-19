package com.company;

import org.w3c.dom.ls.LSOutput;

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
            server = new ServerSocket(1234, 2); // конектимся к порту 1234 , кол-во коннектов максимум 2
            connection = server.accept();   // получение сигнала от юзера
            input = new ObjectInputStream(connection.getInputStream()); // потом приема данных на серве
            while(true){
                String workString = (String)input.readObject(); // считываем данные отправленные от клиента
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
