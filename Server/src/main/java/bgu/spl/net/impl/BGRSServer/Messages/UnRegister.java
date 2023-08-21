package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.Course;
import bgu.spl.net.impl.User;

public class UnRegister extends Command {
    private short courseNum;

    public UnRegister(short courseNum){
        this.courseNum = courseNum;
        opcode = 10;

    }

    public Command actOnProtocol(BGRSProtocol protocol) {


        if (protocol.getCurrentUser().getType().equals("ADMIN") || !protocol.getCurrentUser().getLogIn() ||
                database.getCourses().get(courseNum) == null){
            return new Error((short) 9);
        }
        User student = protocol.getCurrentUser();
        if(!student.getCourses().contains(courseNum))
            return new Error((short) 9);


        student.getCourses().remove(courseNum);
        Course course = database.getCourses().get(courseNum);
        course.currentNumOfStudents = course.currentNumOfStudents - 1;
        course.registerToCourse.remove(student);
        return new Ack((short) 10,null);
    }
}
