package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public class Error extends Command {
    private short messageOpcode;

    public Error(short messageOpcode){
        this.messageOpcode = messageOpcode;
        opcode = 13;
    }

    public short getMessageOpcode(){
        return messageOpcode;
    }


    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        return null;
    }
}

