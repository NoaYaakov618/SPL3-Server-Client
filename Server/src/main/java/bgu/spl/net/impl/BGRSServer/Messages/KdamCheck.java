package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class KdamCheck extends Command{
    private short courseNum;



    public KdamCheck(short courseNum){
        this.courseNum = courseNum;
        opcode = 6;

    }

    public static class comparatorCourseFile implements Comparator<Short>{
        HashMap<Short,Short> courses;

        public comparatorCourseFile(HashMap<Short,Short> courses){
            this.courses = courses;
        }

        public int compare(Short t1, Short t2) {
            return courses.get(t1).compareTo(courses.get(t2));


        }

    }


    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        if (protocol.getCurrentUser().getType().equals("ADMIN") || !protocol.getCurrentUser().getLogIn() ||
        database.getCourses().get(courseNum) == null){
            return new Error((short) 6);
        }
        HashMap<Short,Short> collectionCourses = database.getCollectionCourses();
        int[] kdamInt =   database.getCourses().get(courseNum).kdam;
        List<Short> kdams = new ArrayList<Short>(kdamInt.length);
        for(int i = 0; i < kdamInt.length; i++){
            kdams.add((short) kdamInt[i]);
        }
        Collections.sort(kdams,new comparatorCourseFile(collectionCourses));

        String toAdd = "[";
        for(int i =0; i < kdams.size(); i++){
            toAdd = toAdd + kdams.get(i) + ",";
        }
        if(toAdd.length() != 1)
            toAdd = toAdd.substring(0,toAdd.length()-1);
        toAdd = toAdd + "]";

        String kdam = "\n" + toAdd + "]";


        return new Ack((short) 6,kdam);


    }
}
