package finalLab.control;

import finalLab.model.User;
import java.util.ArrayList;

public class ServerDBControl {

    /*
    * Ham getAllUsers thuc hien viec lay toan bo cac Users cua he thong
     */
    public static ArrayList<User> getAllUsers() {
        ArrayList<User> listActiveAccounts = new ArrayList<>();
        listActiveAccounts.add(new User("0987654321", "111111"));
        listActiveAccounts.add(new User("0988888888", "111111"));
        listActiveAccounts.add(new User("0977777777", "111111"));
        listActiveAccounts.add(new User("0987575701", "111111"));
        return listActiveAccounts;
    }
}
