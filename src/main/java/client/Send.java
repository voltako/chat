package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Send implements Runnable {
    private BufferedReader console;
    private DataOutputStream dos;
    private boolean flag = true;
    private String name;


    public Send(){
        console = new BufferedReader(new InputStreamReader(System.in));
    }
    public Send(Socket client, String name){
        this();
        this.name = name;
        try{
            dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
            flag = false;
        }

    }
    private String getMsgFromConsole(){
        try{
            return console.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public void send(String msg){
        if (null != msg && !msg.equals("")){
            try{
                dos.writeUTF(name +": "+msg);
                Date now = new Date(System.currentTimeMillis());
                String time = new SimpleDateFormat("hh:mm:ss   yyyy/MM/dd  ").format(now);
                System.out.println(time);
                System.out.println(name +": "+msg);
                dos.flush();
            } catch (IOException e){
                e.printStackTrace();
                try{
                    flag = false;
                    dos.close();
                    console.close();
                } catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }else{
            try{
                dos.writeUTF(name);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        send("");
        while(flag){
            send(getMsgFromConsole());
        }
    }
}
