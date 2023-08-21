package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.User;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MyCourses extends Command{

    public MyCourses(){
        opcode = 11;
    }
    public Command actOnProtocol(BGRSProtocol protocol) {
        if (protocol.getCurrentUser().getType().equals("ADMIN") || !protocol.getCurrentUser().getLogIn()){
            return new Error((short) 9);
        }
        ConcurrentLinkedQueue<Short> courses =  protocol.getCurrentUser().getCourses();


        HashMap<Short,Short> collectionCourses = database.getCollectionCourses();
        Short[] myCourses = courses.toArray(new Short[courses.size()]);

        List<Short> myCoursesList = new ArrayList<Short>(myCourses.length);
        for(int i = 0; i < myCourses.length; i++){
            myCoursesList.add(myCourses[i]);
        }
        Collections.sort(myCoursesList,new KdamCheck.comparatorCourseFile(collectionCourses));

        String toAdd = "[";
        for(int i = 0; i < myCoursesList.size(); i++){
            toAdd = toAdd + myCoursesList.get(i) + ",";
        }
        if(toAdd.length() != 1)
            toAdd = toAdd.substring(0,toAdd.length()-1);
        toAdd = toAdd + "]";

        String output = "\n" + toAdd + "]";
        return new Ack((short) 11,output);
    }
}
