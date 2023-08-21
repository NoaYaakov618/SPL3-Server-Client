
// Created by spl211 on 02/01/2021.
//

#include "../include/ServerToClient.h"
using namespace std;

ServerToClient::ServerToClient(ConnectionHandler &connectionHandler, bool *isTerminated, bool *logIn) : handler(
        connectionHandler), isTermiated(isTerminated), logIn(logIn) {}

short ServerToClient::bytesToShort(char *bytesArr, int indexOfstart, int indexTofinish) {
    short result = (short) ((bytesArr[indexOfstart] & 0xff) << 8);
    result += (short) (bytesArr[indexTofinish] & 0xff);
    return result;
}
void ServerToClient::operator()(){
    while (!*isTermiated) {
        char bytes[2];
        handler.getBytes(bytes, 2);
        short opCode = ServerToClient::bytesToShort(bytes, 0, 1);


        switch (opCode) {
            case 12: { // ACK
                handler.getBytes(bytes, 2);
                short messageOpcode = ServerToClient::bytesToShort(bytes, 0, 1);
                switch (messageOpcode) {
                    case 4: {
                        *isTermiated = true;
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) << endl;
                        break;
                    }

                    case 6: {
                        string output = "";
                        handler.getFrameAscii(output, '\0');
                        output = output.substr(0, output.length() - 1);
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) + std::string(" ") + output
                             << endl;
                        break;
                    }
                    case 7: {
                        string output = "";
                        handler.getFrameAscii(output, '\0');
                        output = output.substr(0, output.length() - 1);
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) + std::string(" ") + output
                             << endl;
                        break;
                    }
                    case 8: {
                        string output = "";
                        handler.getFrameAscii(output, '\0');
                        output = output.substr(0, output.length() - 1);
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) + std::string(" ") + output
                             << endl;
                        break;
                    }
                    case 9: {
                        string output = "";
                        handler.getFrameAscii(output, '\0');
                        output = output.substr(0, output.length());
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) + std::string(" ") + output
                             << endl;
                        break;
                    }

                    case 11: {
                        string output = "";
                        handler.getFrameAscii(output, '\0');
                        output = output.substr(0, output.length() - 1);
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) + std::string(" ") + output
                             << endl;
                        break;
                    }
                    default:
                        cout << "ACK " + std::string(std::to_string((int) messageOpcode)) << endl;
                        break;
                }
                break;
            }
            case 13: { //Error
                handler.getBytes(bytes, 2);
                short errorMessageOpcode = ServerToClient::bytesToShort(bytes, 0, 1);
                if (errorMessageOpcode == 4) {
                    *logIn = true;
                }
                cout << "ERROR " + std::string(std::to_string((int) errorMessageOpcode)) << endl;
                break;
            }

        }
    }
}
