package tools;
import SocketPackage.SocketClient;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;

public class MultiplePanels implements ActionListener {
    ComplianceDB complianceDB = new ComplianceDB();
    TableDemo tableDemo = new TableDemo();
    JFrame windows = new JFrame("MultiplePanels");
    JPanel panel_01 = new JPanel();
    JPanel panel_02 = new JPanel();
    JButton click = new JButton("Login");
    JButton click2 = new JButton("Send");
    JButton click4 = new JButton("Close");
    JButton click3 = new JButton("CloseCompliance");
    JLabel text2= new JLabel("Login as "+"no logged");
    JTextArea textArea = new JTextArea();
    URL url=null;

    JLabel text = new JLabel("Button is clicked");
    public String username="Johny";
    public String password="Johny123";
    public String userlogin = "Client";
    Socket socket =null;
    SocketClient client = null;
    Timer timer = null;
    boolean authorization = false;

    public MultiplePanels(URL url) {
        this.url=url;
        timer = new Timer(1000, timerListener);//ustawienie wyzwalania (100ms)
        timer.setDelay(2000);
        timer.start();//uruchomienie timera
        textArea.setText("Johny: Johny123");
        textArea.setPreferredSize(new Dimension(200,23));

        panel_01.setBackground(Color.cyan);
        panel_02.setBackground(Color.darkGray);
        panel_02.add(click);
        panel_02.add(textArea);
        panel_02.add(click2);
        panel_02.add(click4);
        panel_02.add(click3);
        panel_02.add(text2);
        text.setForeground(Color.BLUE);

        text.setVisible(false);
        click.addActionListener(this);
        click2.addActionListener(this);
        click3.addActionListener(closeComplianceListener);
        click4.addActionListener(closeListener);
        windows.add(panel_02,BorderLayout.PAGE_START);
        windows.add(tableDemo,BorderLayout.CENTER);
        windows.setSize(1000,400);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setVisible(true);

    }
    ActionListener timerListener = new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {
            if(authorization==false)return;
            if(client!=null) {
                ArrayList<Complaints> listofcomplains = new ArrayList<>();
                while (client.numberofmessages() > 0) {
                    String str = client.getAndRemove();
                    System.out.println(str);
                    Complaints comp = new Complaints();
                    try {
                        comp.updateFromString(str);
                    } catch (NoSuchFieldException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(comp.command.compareTo("Wrong authorization!")==0) {
                        authorization = false;
                        JOptionPane.showMessageDialog(null, "Wrong login or password");
                    }
                    listofcomplains.add(comp);
                }
                complianceDB.update(listofcomplains);

                MyTableModel model = (MyTableModel) tableDemo.table.getModel();
                for (int row = 0; row < complianceDB.rowCount(); row++) {
                    Object[] rowObject = complianceDB.rowObject(row);
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        model.setValueAt(rowObject[i], row, i);
                    }
                }
                Complaints comp = null;
                try {
                    comp=new Complaints();
                    String string = "login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";"+"Client:"+username+";";
                    comp.updateFromString(string);
                } catch (NoSuchFieldException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                comp.command="getAll";
                client.sendMessage(comp.toString());
                tableDemo.repaint();
            }
        }
    };

    ActionListener closeListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            windows.dispose();
        }
    };
    ActionListener closeComplianceListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(Complaints comp: complianceDB.CompliencesList){
                if(comp.status.compareTo("pickup")==0) {
                    try {
                        String string = "login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";";
                        comp.updateFromString(string);
                        //comp.changeHead(userlogin,username,password);
                    } catch (NoSuchFieldException ex) {
                        throw new RuntimeException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                    comp.command="save";
                    comp.CloseDate= comp.CurrentDate;
                    comp.status="Closed";
                    client.sendMessage(comp.toString());
                }
                tableDemo.repaint();
            }
        }
    };


    @Override
    public void actionPerformed(ActionEvent actionEvent){
        ArrayList<Complaints> templist = new ArrayList<>();
        if(actionEvent.getActionCommand().compareTo("Send")==0) {

            MyTableModel model = (MyTableModel) tableDemo.table.getModel();
           for (int row = 0; row < 15; row++) {
                    String id = model.getValueAt(row, 0).toString();
                    String username2 = model.getValueAt(row, 1).toString();
                    String product = model.getValueAt(row, 2).toString();
                    String company = model.getValueAt(row, 3).toString();
                    String status = model.getValueAt(row, 4).toString();
                    String desription = model.getValueAt(row, 10).toString();
                    if(status.compareTo("tosend")!=0) continue;
                    if(username2.compareTo(username)!=0) continue;
                    if(id.compareTo("")==0)continue;

                    Complaints comp = new Complaints();
               try {
                   comp.updateFromString("idofcomplaint:"+id+";"+"Product:"+product+";"+"Client:"+username2+";"+"Company:"+company+";"+"status:"+status+";"+"description:"+desription+";"+"username:"+username+";"+"password:"+password+";");
                    System.out.println(comp);
               } catch (NoSuchFieldException e) {
                   throw new RuntimeException(e);
               } catch (IllegalAccessException e) {
                   throw new RuntimeException(e);
               }
               templist.add(comp);
               System.out.println(id+";"+username2+";"+product+";"+company+";"+status+";"+desription);

            }
            complianceDB.update(templist);

            for(Complaints comp: complianceDB.CompliencesList){
                    String string = "login:"+userlogin+";"+"username:"+username+";"+"password:"+password+";";
                try {
                    comp.updateFromString(string);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                comp.command="save";
                if(comp.status.compareTo("tosend")==0) {
                    comp.RegistrationDate= comp.CurrentDate;
                    client.sendMessage(comp.toString());
                }
                tableDemo.repaint();
            }
        }

        if(actionEvent.getActionCommand().compareTo("Login")==0) {
            authorization=true;
            String value = textArea.getText();
            String[] tab=value.split(":");
            username=tab[0].trim();
            password=tab[1].trim();
            try {
                socket = new Socket("localhost", 1234);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            client = new SocketClient(socket);
            client.listenForMessage();
            try {

                complianceDB.CreateAndLoadTable(username,url);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
            System.out.println("loaded");
        }
        text.setVisible(true);
        panel_01.setBackground(Color.yellow);
        panel_02.setBackground(Color.green);
    }
}
