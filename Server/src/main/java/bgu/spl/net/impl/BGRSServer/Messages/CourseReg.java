package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.Course;
import org.omg.Messaging.SyncScopeHelper;

import java.sql.SQLOutput;

public class CourseReg extends Command {
    private short courseNum;


    public CourseReg(short courseNum) {
        this.courseNum = courseNum;
        opcode = 5;
    }

    public Command actOnProtocol(BGRSProtocol protocol) {
        if (protocol.getCurrentUser().getType().equals("ADMIN") || !protocol.getCurrentUser().getLogIn()){// the user isn't logged in
            return new Error((short) 5);
        }
        Course course = database.getCourses().get((short)courseNum);
        if(protocol.getCurrentUser().getCourses().contains(courseNum))
            return new Error((short) 5);
        if (course != null && course.maxOfStudent > course.currentNumOfStudents) { // course dose exist and have seats
            for (int i = 0; i < course.kdam.length; i++) { // have the kdam courses
                if (!protocol.getCurrentUser().getCourses().contains(((short)course.kdam[i]))) {
                    return new Error((short) 5);
                }
                    }

            protocol.getCurrentUser().addCourse((short)courseNum);
            course.currentNumOfStudents = course.currentNumOfStudents + 1;
            course.registerToCourse.add(protocol.getCurrentUser());


            return new Ack((short) 5,null);
                }
        else {
            return new Error((short) 5);
        }

    }
}
