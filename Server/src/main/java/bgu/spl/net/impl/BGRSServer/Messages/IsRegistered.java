package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public class IsRegistered extends Command {
    private short courseNum;

    public IsRegistered(short courseNum){
        this.courseNum = courseNum;
        opcode = 9;
    }

    public Command actOnProtocol(BGRSProtocol protocol) {
        if (protocol.getCurrentUser().getType().equals("ADMIN") || !protocol.getCurrentUser().getLogIn() ||
                database.getCourses().get(courseNum) == null){
            return new Error((short) 9);
        }

        if(protocol.getCurrentUser().getCourses().contains(courseNum))
            return new Ack((short) 9, "\n" + "REGISTERED");
        else
            return new Ack((short) 9,"\n" + "NOT REGISTERED");

    }
}
