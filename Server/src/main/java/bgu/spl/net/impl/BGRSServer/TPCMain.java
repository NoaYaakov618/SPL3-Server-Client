package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.Database;
import bgu.spl.net.impl.Course;
import bgu.spl.net.srv.Server;
import org.omg.CORBA.DATA_CONVERSION;

import java.io.FileNotFoundException;

public class TPCMain {
    public static void main(String[] args) throws FileNotFoundException {

        Database.getInstance().initialize("./Courses.txt");
        Server.threadPerClient(Integer.parseInt(args[0]), BGRSProtocol::new, BGRSEncoderDecoder::new).serve();


    }
}



