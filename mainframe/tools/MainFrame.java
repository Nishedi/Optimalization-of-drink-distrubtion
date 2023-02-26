package tools;

import SocketPackage.Server;

import java.net.URL;
import java.util.ArrayList;

public class MainFrame {
    public static void main(String[] args) throws Exception {
        URL url = MainFrame.class.getResource("Complaintable.csv");
        System.out.println(url);
        MultiplePanels multiplePanels = new MultiplePanels(url);
    }
}
