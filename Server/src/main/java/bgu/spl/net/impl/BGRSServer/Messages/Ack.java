package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public class Ack extends Command{
    private short opcodeMessage;
    private String additionalData;

    public Ack(short opcodeMessage,String additionalData){
        this.opcodeMessage = opcodeMessage;
        this.additionalData = additionalData;
        opcode = 12;
    }


    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        return null;
    }

    public short getOpcodeMessage(){
        return opcodeMessage;
    }
    public String getAdditionalData(){
        return additionalData;
    }
}
