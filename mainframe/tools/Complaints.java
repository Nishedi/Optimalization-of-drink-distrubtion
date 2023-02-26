package tools;

import java.lang.reflect.Field;

public class Complaints {
    public String idofcomplaint="";
    public String Product ="";
    public String Client ="";
    public String status="";
    public String description="";
    public String RegistrationDate ="";
    public String ForwardDate ="";
    public String ResponseDate ="";
    public String PickupDate ="";
    public String CloseDate ="";
    public String login ="";
    public String password ="";
    public String username ="";
    public String command="";
    public String argument="";

    public String Company = "";
    public String CurrentDate = "1";
    public Complaints(){}
    public void updateFromString(String str) throws NoSuchFieldException, IllegalAccessException {
        String[] tabstr = str.split(";");
        for(String s: tabstr) {
            String[] tabfortwo = s.split(":");
            String name = tabfortwo[0];
            String value ="";
            Field field = Complaints.class.getField(name);
            if(tabfortwo.length>=2) {
                value = tabfortwo[1];
            }
            field.set(this, value);
        }
    }
    public String toString() {
        String str="";
        Field[] fields = Complaints.class.getFields();
        for(Field f:fields){
            String name = f.getName();
            String value = null;
            try {
                value = f.get(this).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            str=str+name+":"+value+";";
        }
        return str;
    }
}
