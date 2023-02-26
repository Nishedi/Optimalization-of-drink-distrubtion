package SocketPackage;

import tools.Menegerofusers;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;


public class Server {

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) throws Exception {
        this.serverSocket = serverSocket;
    }

    public void startServer(Menegerofusers menegerofusers){
        long starttime=System.currentTimeMillis();
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();

                ClientHandler clientHandler = new ClientHandler(socket,starttime, menegerofusers);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }catch (Exception e){
        closeServerSocket();
        }
    }
    public void closeServerSocket(){
        try{
            if(serverSocket!=null)
                serverSocket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        URL url = Server.class.getResource("usernames.csv");
        Menegerofusers meneger = new Menegerofusers();
        meneger.Load(url);
        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer(meneger);
    }
}
