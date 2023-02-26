package SocketPackage;

import tools.ListenerThread;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient {
    public static ArrayList<String> listofids = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    ListenerThread threadlistener=null;
    public int numberofmessages(){
        return listofids.size();
    }

    public String getAndRemove(){
        if(numberofmessages()==0) return null;
        String str = listofids.get(0);
        listofids.remove(0);
        return str;
    }

    public SocketClient(Socket socket){
        try{
            this.socket=socket;
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e){
            close();
        }
    }

    public void sendMessage(String messageToSend){
        try{
            if (socket.isConnected()){
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e){
            close();
        }
    }

    public void listenForMessage(){
        threadlistener = new ListenerThread(socket, bufferedReader, bufferedWriter, listofids);
        threadlistener.start();
    }
    public void close(){
        threadlistener.stop();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

