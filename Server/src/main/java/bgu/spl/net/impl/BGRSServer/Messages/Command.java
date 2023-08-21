package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public abstract class Command {
    protected short opcode;
    protected Database database = Database.getInstance();

  public Command(){
    }
    public abstract Command actOnProtocol(BGRSProtocol protocol);

  public short getOpcode(){
      return opcode;
  }

}
