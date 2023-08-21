package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.impl.BGRSServer.Messages.*;
import bgu.spl.net.impl.BGRSServer.Messages.Error;
import bgu.spl.net.impl.rci.Command;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class BGRSEncoderDecoder<Command> implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[1 << 10]; // 1k
    private int len = 0;
    private short opcode = -1;
    private short courseNum = -1;
    private String userName = null;
    private String password = null;



    public Command decodeNextByte(byte nextByte) {
        Command command = null;
        if (opcode == -1) {
            bytes[len] = nextByte;
            len++;
            if (len == 2) {
                opcode = bytesToShort(Arrays.copyOfRange(bytes, 0, 2));
                len = 0;
                if(opcode == 4 || opcode == 11){
                    if(opcode == 4)
                        command =  (Command) new LogOut();
                    else
                        command = (Command) new MyCourses();
                    clean();
                    return command;
                }
            }
            return null;
        }
        else {
            if (decoByOp(nextByte)) {
                command = ComByOp();
                clean();
            }
        }
            return command;


    }

    private Command ComByOp() {
        if(opcode == 1) {
            return (Command) new Adminreg(userName, password);
        }
        if(opcode == 2) {
            return (Command) new StudentReg(userName, password);
        }
        if(opcode == 3){
            return (Command) new LogIn(userName,password);
        }
        if(opcode == 5) {
            return (Command) new CourseReg(courseNum);
        }
        if(opcode == 6) {
            return (Command) new KdamCheck(courseNum);
        }
        if(opcode == 7) {
            return (Command) new CourseStat(courseNum);
        }
        if(opcode == 8) {
            return (Command) new StudentStat(userName);
        }
        if(opcode == 9) {
            return (Command) new IsRegistered(courseNum);
        }
        if(opcode == 10) {
            return (Command) new UnRegister(courseNum);
        }
        return null;
    }

    private void clean(){
        opcode = -1;
        len = 0;
        bytes = new byte[1 << 10];
        courseNum = -1;
        userName = null;
        password = null;
    }





    private boolean decoByOp(byte nextByte) {
        if (opcode == 1 || opcode == 2 || opcode == 3) {
            if (userName == null)
                userName = decodeBytesToString(nextByte);
            else if (password == null) {
                password = decodeBytesToString(nextByte);
            }
            return userName != null && password != null;

        }
        else if (opcode == 5 || opcode == 6 || opcode == 7 || opcode == 9 || opcode == 10) {
            if (courseNum == -1) {
                courseNum = decodeToNum(nextByte);
            }

            return courseNum != -1;
        }
        else if (opcode == 8) {
            if (userName == null) {
                userName = decodeBytesToString(nextByte);
                return userName != null;
            }

        }
        return true;
    }


    private short decodeToNum(byte nextByte) {
        if (courseNum == -1) {
            bytes[len] = nextByte;
            len++;
            if (len == 2) {
                courseNum = bytesToShort(Arrays.copyOfRange(bytes, 0, 2));
                len = 0;
            }

        }
        return courseNum;
    }

    private String decodeBytesToString(byte nextByte){
        if(nextByte == '\0'){
            String output = new String(bytes,0,len, StandardCharsets.UTF_8);
            len = 0;
            return output;
        }
        pushByte(nextByte);
        return null;

    }


    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }


    public byte[] encode(Command message) {
        if (message instanceof Ack) {
            short opcode = ((Ack) message).getOpcode();
            short opcodeMessage = (((Ack) message).getOpcodeMessage());
            String additionalData = ((Ack) message).getAdditionalData();
            byte[] opcodeByte = shortToBytes(opcode);
            byte[] opcodeMessegeByte = shortToBytes(opcodeMessage);
            byte[] output = new byte[4];
            for(int i = 0; i < 2 ; i++){
                output[i] = opcodeByte[i];
            }
            output[2] = opcodeMessegeByte[0];
            output[3] = opcodeMessegeByte[1];

            if (additionalData != null){
                additionalData = additionalData + "\0";
                byte[] add = additionalData.getBytes(StandardCharsets.US_ASCII);
                byte[] newOutput = new byte[add.length + 4];
                for(int i = 0; i < 4 ; i++){
                    newOutput[i] = output[i];
                }
                int k = 0;
                for(int j = 4 ;j < newOutput.length ; j++){
                    newOutput[j] = add[k];
                    k++;
                }
                return newOutput;

            }

            return output;
        }
        else if (message instanceof Error)  {
            short opcode = ((Error) message).getOpcode();
            short opcodeMessage = (((Error) message).getMessageOpcode());
            byte[] opcodeByte = shortToBytes(opcode);
            byte[] opcodeMessegeByte = shortToBytes(opcodeMessage);
            byte[] output = new byte[4];
            for(int i = 0; i < 2 ; i++){
                output[i] = opcodeByte[i];
            }
            output[2] = opcodeMessegeByte[0];
            output[3] = opcodeMessegeByte[1];
            return  output;

        }
        return null;

    }
    public byte[] shortToBytes(short num)
    {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte)((num >> 8) & 0xFF);
        bytesArr[1] = (byte)(num & 0xFF);
        return bytesArr;
    }
    public short bytesToShort(byte[] byteArr)
    {
        short result = (short)((byteArr[0] & 0xff) << 8);
        result += (short)(byteArr[1] & 0xff);
        return result;
    }


}