package bgu.spl.net.impl;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class User {
    private String type;
    private String useName;
    private String password;
    private ConcurrentLinkedQueue<Short> courses;
    private boolean logIn;

    public User(String useName,String password, String type){
        this.useName = useName;
        this.password = password;
        this.type = type;
        courses = new ConcurrentLinkedQueue<>();
        logIn = false;
    }
    public String getUseName(){
        return useName;
    }

    public void setLogIn(){
        if(logIn)
            logIn = false;
        else
            logIn = true;
    }

    public boolean getLogIn(){
        return logIn;
    }
    public String getType(){
        return type;
    }

    public String getPassword(){
        return password;
    }

    public void addCourse(short numOfCourse){

        courses.add(numOfCourse);
    }
    public ConcurrentLinkedQueue<Short> getCourses(){
        return courses;
    }

}
