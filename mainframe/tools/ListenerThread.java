package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ListenerThread extends Thread{
    Socket socket = null;
    BufferedReader bufferedReader = null;
    BufferedWriter bufferedWriter = null;
    ArrayList<String> listofids = null;
    public ListenerThread(Socket socket,BufferedReader bufferedReader,BufferedWriter bufferedWriter, ArrayList<String> listofids){
        this.socket=socket;
        this.bufferedReader=bufferedReader;
        this.bufferedWriter=bufferedWriter;
        this.listofids = listofids;
    }
    @Override
    public void run() {
        String msgFromGroupChat;
        while (socket.isConnected()) {
            try {
                msgFromGroupChat = bufferedReader.readLine();
                listofids.add(msgFromGroupChat);
            } catch (IOException e) {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                    if(socket!= null){
                        socket.close();
                    }
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
        }
    }
}
