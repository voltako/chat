package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable {
    private DataInputStream dis;
    private boolean flag = true;

    public Receive(Socket client){
        try{
            dis = new DataInputStream(client.getInputStream());

        } catch (IOException e){
            e.printStackTrace();
            flag = false;
            try {
                dis.close();
            } catch (IOException e1){
                e1.printStackTrace();
            }
        }
    }
    public String receive() {
        String msg = "";
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            try {
                dis.close();
            } catch (IOException e1){
                e1.printStackTrace();
            }
        }
        return msg;
    }

    @Override
    public void run(){
        while(flag){
            System.out.println(receive());
        }
    }

}
