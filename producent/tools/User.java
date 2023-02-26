package tools;

import java.lang.reflect.Field;

public class User {
   public String User;
   public String Password;
   public String Role;
   public String Company;

    public User(){}
    public void parseClientfromString(String str){
        String[] tabstr = str.split(";");
        User =tabstr[0];
        Password=tabstr[1];
        Role=tabstr[2];
    }
    public void updateFromString(String str) throws NoSuchFieldException, IllegalAccessException {
        String[] tabstr = str.split(";");
        for(String s: tabstr) {
            String[] tabfortwo = s.split(":");
            String name = tabfortwo[0];
            String value ="";
            Field field = User.class.getField(name);
            if(tabfortwo.length>=2) {
                value = tabfortwo[1];
            }
            field.set(this, value);
        }
    }
    public String toString() {
        String str="";
        Field[] fields = User.class.getFields();
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
