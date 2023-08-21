package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class LogIn extends Command {
    protected String password;
    protected String userName;

    public LogIn(String userName,String password){
        this.userName = userName;
        this.password = password;
        opcode = 3;
    }

    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        if(database.getUsers().get(userName) == null || !database.getUsers().get(userName).getPassword().equals(password)||
        protocol.getCurrentUser().getLogIn()) {
            return new Error((short) 3);
        }
        if(database.getUsers().get(userName).getLogIn()){
                return new Error((short) 3);

        }
        protocol.setCurrentUser(database.getUsers().get(userName));
        database.getUsers().get(userName).setLogIn();
        database.currentLoggedPlus();
        return new Ack((short) 3,null);
        }

}

