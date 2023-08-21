package bgu.spl.net.impl.BGRSServer.Messages;

import bgu.spl.net.impl.BGRSServer.BGRSProtocol;

public class LogOut extends Command {

    public LogOut() {
        opcode = 4;
    }

    @Override
    public Command actOnProtocol(BGRSProtocol protocol) {
        if (database.getCurrentLoggedIn().intValue() == 0)
            return new Error((short) 4);

        protocol.setShouldTerminate();
        protocol.getCurrentUser().setLogIn();
        database.currentLoggedMinus();
        return new Ack((short) 4,null);

    }
}
