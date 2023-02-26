package SocketPackage;

import tools.Complaints;
import java.io.*;
import java.net.Socket;
import java.util.*;
public class Seller {
    public static void main(String[]args) throws IOException, InterruptedException, NoSuchFieldException, IllegalAccessException {
        String userlogin = "Seller";
        String username = "Kate";
        String password = "Kate123";
        Complaints comp = new Complaints();
        Socket socket = new Socket("localhost", 1234);
        SocketClient seller = new SocketClient(socket);
        seller.listenForMessage();


        while(1==1){
            String string = "login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";";
            comp.updateFromString(string);
            comp.command="getAllSeller";
            seller.sendMessage(comp.toString());
            Thread.sleep(1000);
            while(seller.numberofmessages()>0){
                String str=seller.getAndRemove();
                comp = new Complaints();
                comp.updateFromString(str);
                System.out.println(str);
                Random rand = new Random();
                if(comp.status.compareTo("atseller")==0) {
                    int d = rand.nextInt(1, 4);
                    Integer RegistrationDateInt = Integer.valueOf(comp.RegistrationDate);
                    Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                    Integer diffrence = CurrentDateInt - RegistrationDateInt;
                    if (diffrence > d) {
                        comp.updateFromString("login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";");
                        comp.status = "atproducer";
                        comp.ForwardDate = comp.CurrentDate;
                        seller.sendMessage(comp.toString());
                    }
                }
                if(comp.status.compareTo("confirmed")==0){
                    int d = rand.nextInt(1, 4);
                    Integer ResponseDateInt = Integer.valueOf(comp.ResponseDate);
                    Integer CurrentDateInt = Integer.valueOf(comp.CurrentDate);
                    Integer diffrence = CurrentDateInt - ResponseDateInt;
                    if (diffrence > d) {
                        comp.updateFromString("login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";");
                        comp.status = "pickup";
                        comp.PickupDate = comp.CurrentDate;
                        seller.sendMessage(comp.toString());
                    }
                }
            }
        }
    }
}
