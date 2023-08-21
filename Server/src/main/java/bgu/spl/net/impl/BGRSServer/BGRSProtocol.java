package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.impl.BGRSServer.Messages.Command;
import bgu.spl.net.impl.User;

public class BGRSProtocol<T> implements MessagingProtocol<Command> {
    private boolean shouldTerminate = false;
    private User currentUser;


    public BGRSProtocol(){
    }




    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }



    @Override
    public Command process(Command msg) {
        return msg.actOnProtocol(this);

    }


    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }

    public void setShouldTerminate(){
        shouldTerminate = true;
    }
}
