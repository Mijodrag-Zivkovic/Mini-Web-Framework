package framework;

import java.lang.reflect.Array;
import java.util.ArrayList;

import framework.bussineslogic.model.User;
public class ImprovisedStorage {

    public static ArrayList<User> users = new ArrayList<User>();

    public static void fillUsers(){
        users.add(new User(1,"User1", true));
        users.add(new User(2,"User2", true));
        users.add(new User(3,"User3", false));
    }
}
