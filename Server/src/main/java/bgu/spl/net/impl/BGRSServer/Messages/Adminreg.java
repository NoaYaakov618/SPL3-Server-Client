package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;
import bgu.spl.net.impl.User;

public class Adminreg extends Command{
    protected String password;
    protected String userName;

   public Adminreg(String userName,String password){
       this.userName = userName;
       this.password = password;
       opcode = 1;

   }

    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        User admin = new User(userName,password,"ADMIN");
        if(database.getUsers().get(userName) == null && protocol.getCurrentUser() == null){
            database.addUser(userName,admin);
            protocol.setCurrentUser(admin);
            return new Ack((short) 1,null);

        }
        else if(database.getUsers().get(userName) == null && protocol.getCurrentUser().getLogIn() == false){
            database.addUser(userName,admin);
            protocol.setCurrentUser(admin);
            return new Ack((short) 1,null);

        }

        else{
            return new Error((short) 1);
        }

    }
}
