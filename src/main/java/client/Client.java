package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client{
    public static void main(String[] args) throws IOException{
        System.out.println("Введите ник:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String name = br.readLine();

        Socket client = new Socket("localhost",5050);
        new Thread(new Send(client,name)).start();
        new Thread(new Receive(client)).start();
    }
}