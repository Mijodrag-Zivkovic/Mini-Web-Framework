package framework.bussineslogic.controllers;

import framework.ImprovisedStorage;
import framework.annotations.Controller;
import framework.annotations.GET;
import framework.annotations.POST;
import framework.annotations.Path;
import framework.bussineslogic.model.User;

import java.util.ArrayList;

@Controller(path="/users")
public class UserController {

    //static UserController instance = null;

    public UserController() {

    }

//    static public UserController getInstance()
//    {
//        if (instance == null)
//            instance = new UserController();
//
//        return instance;
//    }

    @GET
    public ArrayList<User> getUsers(){
        return ImprovisedStorage.users;
    }

    @POST
    public void addUser(){

    }

    @GET
    @Path(path = "/{id}")
    public void getUserById(){

    }


}
