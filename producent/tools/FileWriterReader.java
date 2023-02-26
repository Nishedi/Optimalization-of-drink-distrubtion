package tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FileWriterReader {

    public static ArrayList<String> loadFromCSV(String filename)  {
        ArrayList<String> stringlist = new ArrayList<>();
        URL url= null;
        try {
            url = new URL(filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        FileWriterReader reader = new FileWriterReader();
        ArrayList<String>lstr=new ArrayList<>();
        try {
            lstr=reader.read(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String columnnames[] = lstr.get(0).split(";");
        lstr.remove(0);
        for(String str:lstr){
            String split[] = str.split(";");
            String string = "";
            for(int i =0; i<=columnnames.length-1;i++){
                String value="";
                if(split.length>i)
                    value=split[i];
                string=string + columnnames[i] + ":" + value+";";
            }
            stringlist.add(string);
        }
        return stringlist;
    }

    public static ArrayList<String> loadFromCSV(URL url)  {
        ArrayList<String> stringlist = new ArrayList<>();

        FileWriterReader reader = new FileWriterReader();
        ArrayList<String>lstr=new ArrayList<>();
        try {
            lstr=reader.read(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String columnnames[] = lstr.get(0).split(";");
        lstr.remove(0);
        for(String str:lstr){
            String split[] = str.split(";");
            String string = "";
            for(int i =0; i<=columnnames.length-1;i++){
                String value="";
                if(split.length>i)
                    value=split[i];
                string=string + columnnames[i] + ":" + value+";";
            }
            stringlist.add(string);
        }
        return stringlist;
    }
    public ArrayList<String> read(URL url) {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader ReadF = null;
            ReadF = new BufferedReader(new InputStreamReader(url.openStream()));
            String numstring = ReadF.readLine();
            try {
                while (numstring != null) {
                    list.add(numstring);
                    numstring = ReadF.readLine();
                }
            } catch (Exception er) {
                return null;
            }
            ReadF.close();
        }catch (Exception x){
            return null;
        }

        return list;
    }
}
