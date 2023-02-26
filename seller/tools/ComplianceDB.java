package tools;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ComplianceDB {
    public ArrayList<Complaints> CompliencesList = new ArrayList<>();
    public void CreateAndLoadTable(String username, URL url) throws NoSuchFieldException, IllegalAccessException, MalformedURLException {

        ArrayList<String> lstr = FileWriterReader.loadFromCSV(url);
        CompliencesList.clear();
        for(String str:lstr){
            Complaints complaints = new Complaints();
            complaints.updateFromString(str);
            if(complaints.Client.compareTo(username)==0){
                CompliencesList.add(complaints);
            }
        }
    }
    public int rowCount(){
        return CompliencesList.size();
    }
    public Object[] rowObject(int nr){
        Complaints complaints = CompliencesList.get(nr);
        ArrayList<Object> listofobj = new ArrayList<>();

        listofobj.add(complaints.idofcomplaint);
        listofobj.add(complaints.Client);
        listofobj.add(complaints.Product);
        listofobj.add(complaints.Company);
        listofobj.add(complaints.status);

        Integer RegistrationDateINT=0;
        Integer ForwardDateINT=0;
        Integer ResponseDateINT=0;
        Integer PickupDateINT=0;
        Integer CloseDateINT=0;


        if(complaints.RegistrationDate.trim().compareTo("")!=0) {
            RegistrationDateINT = Integer.valueOf(complaints.RegistrationDate.trim());
            LocalDate ldRegistrationDateINT = LocalDate.of(2022, Month.OCTOBER, 1).plusDays(RegistrationDateINT);
            listofobj.add(ldRegistrationDateINT.toString());
        }else
            listofobj.add("");

        if(complaints.ForwardDate.trim().compareTo("")!=0) {
            ForwardDateINT = Integer.valueOf(complaints.ForwardDate.trim());
            LocalDate ldForwardDateINT = LocalDate.of(2022, Month.OCTOBER, 1).plusDays(ForwardDateINT);
            listofobj.add(ldForwardDateINT);
        }else
            listofobj.add("");

        if(complaints.ResponseDate.trim().compareTo("")!=0) {
            ResponseDateINT = Integer.valueOf(complaints.ResponseDate.trim());
            LocalDate ldResponseDateINT = LocalDate.of(2022, Month.OCTOBER, 1).plusDays(ResponseDateINT);
            listofobj.add(ldResponseDateINT);
        }else
            listofobj.add("");

        if(complaints.PickupDate.trim().compareTo("")!=0) {
            PickupDateINT = Integer.valueOf(complaints.PickupDate.trim());
            LocalDate ldPickupDateINT = LocalDate.of(2022, Month.OCTOBER, 1).plusDays(PickupDateINT);
            listofobj.add(ldPickupDateINT);
        }else
            listofobj.add("");

        if(complaints.CloseDate.trim().compareTo("")!=0) {
            CloseDateINT = Integer.valueOf(complaints.CloseDate.trim());
            LocalDate ldCloseDateINT = LocalDate.of(2022, Month.OCTOBER, 1).plusDays(CloseDateINT);
            listofobj.add(ldCloseDateINT);
        }else
            listofobj.add("");

        listofobj.add(complaints.description);
        return listofobj.toArray();
    }
    public void update(ArrayList<Complaints> complaints){
        Map<String, Complaints> map = new HashMap<>();// klucz numer reklamacji
        for(Complaints comp: complaints){
            map.put(comp.idofcomplaint, comp);
        }
        for(int i = 0 ; i<=CompliencesList.size()-1;i++){
            String key = CompliencesList.get(i).idofcomplaint;
            if(map.containsKey(key)){
               CompliencesList.set(i,map.get(key));
            }
        }

        map = new HashMap<>();// klucz numer reklamacji
        for(Complaints comp: CompliencesList){
            map.put(comp.idofcomplaint, comp);
        }
        for(int i = 0 ; i<=complaints.size()-1;i++) {

            if (!map.containsKey(complaints.get(i).idofcomplaint)) {
                Complaints comptemp = complaints.get(i);
                System.out.println("!!!"+comptemp);
                CompliencesList.add(comptemp);
            }
        }
    }
}
