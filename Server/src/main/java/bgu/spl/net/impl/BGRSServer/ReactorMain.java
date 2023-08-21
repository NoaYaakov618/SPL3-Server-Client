package bgu.spl.net.impl.BGRSServer;
import bgu.spl.net.Database;
import bgu.spl.net.impl.BGRSServer.Messages.Command;
import bgu.spl.net.impl.rci.RemoteCommandInvocationProtocol;
import bgu.spl.net.srv.Server;

import java.io.FileNotFoundException;

public class ReactorMain {
    public static void main(String[] args) throws FileNotFoundException {
            Database.getInstance().initialize("./Courses.txt");
            Server.reactor(Integer.parseInt(args[1]), Integer.parseInt(args[0]), BGRSProtocol::new, BGRSEncoderDecoder::new).serve();

    }
}
