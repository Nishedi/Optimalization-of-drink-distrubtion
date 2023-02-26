package tools;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menegerofusers {
    private Map<String, User> mapofusers = new HashMap<>();

    public boolean checkauthorization(String username, String password) {//check authorization
        boolean authorization = false;
        if (mapofusers.containsKey(username) == true) {
            if (mapofusers.get(username).Password.compareTo(password) == 0) {
                authorization = true;
            }
        }
        return authorization;
    }

    public User getUser(String User){
        if(mapofusers.containsKey(User)) {
            return mapofusers.get(User);
        }
        return null;
    }
    public void Load(URL url) throws NoSuchFieldException, IllegalAccessException {
        ArrayList<String> lstr = FileWriterReader.loadFromCSV(url);
        mapofusers.clear();
        for (String str : lstr) {
            User user = new User();
            user.updateFromString(str);
            String key = user.User;
            if (!mapofusers.containsKey(key)) {
                mapofusers.put(key, user);
            }
        }
    }
}
