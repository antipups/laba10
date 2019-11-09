package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Flow;

public class Main extends JFrame implements Runnable {

    static private Socket connection;
    static private ObjectOutputStream output;
    static private ObjectInputStream input;

    public static void main(String[] args){
        System.out.println("Привет мир");
        new Thread(new Main("test")).start();
        new Thread(new Server()).start();
    }

    public Main(String name){
        super(name);
        setLayout(new FlowLayout());
        setSize(600, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
        final JLabel ltitle_of_file = new JLabel("Название файла - ");
        final JTextField title_of_file = new JTextField(10);
        final JLabel ldata_in_file = new JLabel("Содержимое файла - ");
        final JTextField data_in_file = new JTextField(10);
        final JButton b1 = new JButton("Отправить");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (actionEvent.getSource() == b1){
                    try {
                        if (title_of_file.getText().length() == 0 || data_in_file.getText().length() == 0) return;
                        send_data(title_of_file.getText() + "//" + data_in_file.getText()); // объединаяем в одну строку, т.к. write object принимает один аргумент
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        add(ltitle_of_file);
        add(title_of_file);
        add(ldata_in_file);
        add(data_in_file);
        add(b1);
    }

    @Override
    public void run() {
        try {
            while(true){
                connection = new Socket(InetAddress.getByName("127.0.0.1"), 1234);
                output = new ObjectOutputStream(connection.getOutputStream());
                input = new ObjectInputStream(connection.getInputStream());
                JOptionPane.showMessageDialog(null, (String)input.readObject());
            }
        }
        catch (UnknownHostException e) {}
        catch (IOException e) {}
        catch (ClassNotFoundException e) {}
    }

    private static void send_data(Object obj) throws IOException {
        output.flush();
        output.writeObject(obj);
    }
}
