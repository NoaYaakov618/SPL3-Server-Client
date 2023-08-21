package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.User;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StudentStat extends Command {
    String studentUserName;
    public StudentStat (String studentUserName){
        this.studentUserName = studentUserName;
        opcode = 8;
    }

    public Command actOnProtocol(BGRSProtocol protocol) {
        if(protocol.getCurrentUser().getType().equals("STUDENT") || !protocol.getCurrentUser().getLogIn() ||
                database.getUsers().get(studentUserName) == null || database.getUsers().get(studentUserName).getType().equals("ADMIN"))
            return new Error((short) 8);
        User student = database.getUsers().get(studentUserName);
        ConcurrentLinkedQueue<Short>  courses = student.getCourses();
        HashMap<Short,Short> collectionCourses = database.getCollectionCourses();


        Short[] coursesArray =   courses.toArray(new Short[courses.size()]);
        List<Short> coursesList = new ArrayList<Short>(coursesArray.length);
        for(int i = 0; i < coursesArray.length; i++){
            coursesList.add(coursesArray[i]);
        }

        Collections.sort(coursesList,new KdamCheck.comparatorCourseFile(collectionCourses));
        String toAdd = "[";
        for(int i =0; i < coursesList.size(); i++){
            toAdd = toAdd + coursesList.get(i) + ",";
        }
        if(toAdd.length() != 1)
            toAdd = toAdd.substring(0,toAdd.length()-1);
        toAdd = toAdd + "]";

        String output = "\n" + "Student: " + studentUserName + "\n" +
                "Courses: " + toAdd + "]";
        return new Ack((short) 8,output);

    }
}
