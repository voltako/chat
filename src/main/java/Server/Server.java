package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {
    private List<MyChannel> all = new ArrayList<MyChannel>();

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(5050);
        while(true) {
            Socket client = server.accept();
            MyChannel channel = new MyChannel(client);
            all.add(channel);
            new Thread(channel).start();
        }
    }

    private  class MyChannel implements Runnable {

        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean flag = true;
        private String name;

        public MyChannel(Socket client) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                flag = false;

                try {
                    dis.close();
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        private String receive() {
            String msg = "";
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                all.remove(this);
                try {
                    dis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            return msg;
        }


        private void send(String msg) {
            if (null == msg || msg.equals("")) {
                return;
            }
            try {
                dos.writeUTF(time());
                dos.writeUTF(msg);
            } catch (IOException e) {
                flag = false;
                e.printStackTrace();
                all.remove(this);
                try {
                    dos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }



        private String time () {
            Date now = new Date(System.currentTimeMillis());
            String time = new SimpleDateFormat("hh:mm:ss").format(now);
            return time;
        }

        @Override
        public void run () {
            send("?????????? ???????????????????? ?? ?????????????????? ??????");
            name = receive();
        }
    }
}
