package SocketPackage;

import tools.Complaints;
import tools.Menegerofusers;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class ClientHandler implements Runnable {
    public static Map<String, Complaints> mapofprovided=new HashMap<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public static Menegerofusers menegerofusers;
    long starttime=0;

    public ClientHandler(Socket socket, long starttime, Menegerofusers meneger) throws Exception {
        try{
            menegerofusers=meneger;
            this.starttime = starttime;
            this.socket=socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
            try{
                messageFromClient=bufferedReader.readLine();
                Complaints complain = new Complaints();
                complain.updateFromString(messageFromClient);

                String username = complain.username;
                String password = complain.password;
                String command = complain.command;
                long time=System.currentTimeMillis();
                Long diffrence = (time - starttime)/1000;
                String curentdate = diffrence.toString();

                if(1==1){

                    //check authorization
                    boolean authorization = menegerofusers.checkauthorization(username, password);

                    if(authorization==false) {
                        Complaints comp = new Complaints();
                        comp.updateFromString("login:server;username:;password:;");
                        comp.command="Wrong authorization!";
                        comp.CurrentDate=curentdate;
                        SendMessage(comp.toString());
                        continue;
                    }

                    if(command.compareTo("save")==0){
                        String key = complain.idofcomplaint;
                        if(!mapofprovided.containsKey(key)){
                            mapofprovided.put(key,complain);
                        }
                        if(complain.status.compareTo("tosend")==0) {
                            complain.status = "atseller";
                            if(complain.login.compareTo("Client")==0)
                                complain.RegistrationDate= curentdate;
                        }
                        mapofprovided.put(key, complain);
                        continue;
                    }

                    if(command.compareTo("getAllSeller")==0){
                        for(String s: mapofprovided.keySet()){
                            if((mapofprovided.get(s).status.compareTo("atseller")==0)||(mapofprovided.get(s).status.compareTo("confirmed")==0)) {
                                Complaints comp = mapofprovided.get(s);
                                comp.CurrentDate=curentdate;
                                SendMessage(comp.toString());
                            }
                        }
                        continue;
                    }

                    if(command.compareTo("getAllProducer")==0){
                        String producent = complain.username;
                        //System.out.println("producent "+producent);
                        for(String s: mapofprovided.keySet()){
                            if(mapofprovided.get(s).status.compareTo("atproducer")==0) {
                                Complaints comp = mapofprovided.get(s);
                                //System.out.println("tempproducer "+comp.Company);
                               if(producent.compareTo(comp.Company)==0) {
                                    comp.CurrentDate = curentdate;
                                    SendMessage(comp.toString());
                                }
                            }
                        }
                        continue;
                    }

                    if(command.compareTo("getAll")==0){
                        String client = complain.Client;
                        for(String s: mapofprovided.keySet()) {
                            Complaints comptemp = mapofprovided.get(s);
                            if(client.compareTo(comptemp.Client)==0){
                                String str = "login:Server;username:;password:;" + "CurrentDate:" + curentdate + ";";
                                comptemp.updateFromString(str);
                                SendMessage(comptemp.toString());
                                }
                            }
                        continue;
                    }
                    SendMessage(messageFromClient);
                }
            }catch (IOException e){
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void SendMessage(String messageTosent){
        try{
            bufferedWriter.write(messageTosent);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void removeClientHandler(){
        System.out.println("Remove client Handler");
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket!=null){
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
