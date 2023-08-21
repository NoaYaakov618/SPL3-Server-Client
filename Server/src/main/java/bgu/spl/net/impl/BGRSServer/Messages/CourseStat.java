package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.Course;
import bgu.spl.net.impl.User;

import javax.jws.soap.SOAPBinding;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CourseStat extends Command {
    private short courseNum;


    public CourseStat(short courseNum){
        this.courseNum = courseNum;
        opcode = 7;
    }


    public Command actOnProtocol(BGRSProtocol protocol) {
        if(protocol.getCurrentUser().getType().equals("STUDENT") || protocol.getCurrentUser().getLogIn() == false ||
                database.getCourses().get(courseNum) == null)
            return new Error((short) 7);
        Course course = database.getCourses().get(courseNum);
        int courseNumber = courseNum;
        String corseName = course.name;
        int seats = course.maxOfStudent - course.currentNumOfStudents;
        int maxSeats = course.maxOfStudent;
        ConcurrentLinkedQueue<User> registered = course.registerToCourse;

        User[] outreg = registered.toArray(new User[registered.size()]);
        Arrays.sort(outreg,new UserComparator());

        String toAdd = "[";
        for(User user : outreg){
            toAdd = toAdd + user.getUseName() + ",";
        }
        if(toAdd.length() != 1)
            toAdd = toAdd.substring(0,toAdd.length()-1);

        toAdd = toAdd + "]";

        String output = "\n" + "Course: (" + courseNumber + ")" + " " + corseName + "\n" +
                "Seats Available: " + seats + "/" + maxSeats + "\n" +
                "Students Registered: " + toAdd + "]";
        return new Ack((short) 7,output);

    }

    public class UserComparator implements Comparator<User> {

        public int compare(User t1, User t2) {
            return t1.getUseName().compareTo(t2.getUseName());


        }

    }
}
