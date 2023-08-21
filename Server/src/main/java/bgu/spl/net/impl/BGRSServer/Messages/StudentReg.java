package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.User;

public class StudentReg extends Command {
    protected String password;
    protected String userName;

    public StudentReg(String userName,String password){
        this.userName = userName;
        this.password = password;
        opcode = 2;

    }

    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        User student = new User(userName,password,"STUDENT");

        if(database.getUsers().get(userName) == null && protocol.getCurrentUser() == null )  {
            database.addUser(userName,student);
            protocol.setCurrentUser(student);
            return new Ack((short) 2,null);
        }
        else if(database.getUsers().get(userName) == null && protocol.getCurrentUser().getLogIn() == false){
            database.addUser(userName,student);
            protocol.setCurrentUser(student);
            return new Ack((short) 2,null);
        }
        else{
            return new Error((short) 2);
        }
    }

}

