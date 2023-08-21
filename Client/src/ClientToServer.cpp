
// Created by spl211 on 02/01/2021.
//

#include "../include/ClientToServer.h"

ClientToServer::ClientToServer(ConnectionHandler &connectionHandler, bool *isTerminated, bool *logIn) : handler(
        connectionHandler), isTerminated(isTerminated), logIn(logIn) {}

using namespace std;

static void shortToBytes(short num, char *bytesArr) {
    bytesArr[0] = ((num >> 8) & 0xFF);
    bytesArr[1] = (num & 0xFF);
}

void ClientToServer::operator()() {
    while (!*isTerminated) {
        const short bufsize = 1024;
        char buf[bufsize];
        if (*logIn) {
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            string firstwords = line.substr(0, line.find(" "));
            vector<string> wordss = splitString(line, ' ');


            char *bytesArr = new char[2];


                if (firstwords == "ADMINREG") {
                    shortToBytes((short) 1, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    string userName = wordss.at(1);
                    string passwords = wordss.at(2);
                    handler.sendFrameAscii(userName, '\0');
                    handler.sendFrameAscii(passwords, '\0');


                }

                if (firstwords == "STUDENTREG") {
                    shortToBytes((short) 2, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    string userName = wordss.at(1);
                    string passwords = wordss.at(2);
                    handler.sendFrameAscii(userName, '\0');
                    handler.sendFrameAscii(passwords, '\0');
                }

                if (firstwords == "LOGIN") {
                    shortToBytes((short) 3, bytesArr);
                    handler.sendBytes(bytesArr, 2);

                    string userName = wordss.at(1);
                    string passwords = wordss.at(2);
                    handler.sendFrameAscii(userName, '\0');
                    handler.sendFrameAscii(passwords, '\0');
                }
                if (firstwords == "LOGOUT") {
                    *logIn = false;
                    shortToBytes((short) 4, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                }

                if (firstwords == "COURSEREG") {
                    shortToBytes((short) 5, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    int courseNum = stoi(wordss.at(1));
                    shortToBytes((short) courseNum, bytesArr);
                    handler.sendBytes(bytesArr, 2);

                }

                if (firstwords == "KDAMCHECK") {
                    shortToBytes((short) 6, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    int courseNum = stoi(wordss.at(1));
                    shortToBytes((short) courseNum, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                }

                if (firstwords == "COURSESTAT") {
                    shortToBytes((short) 7, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    int courseNum = stoi(wordss.at(1));
                    shortToBytes((short) courseNum, bytesArr);
                    handler.sendBytes(bytesArr, 2);

                }
                if (firstwords == "STUDENTSTAT") {
                    shortToBytes((short) 8, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    string userName = wordss.at(1);
                    handler.sendFrameAscii(userName, '\0');
                }
                if (firstwords == "ISREGISTERED") {
                    shortToBytes((short) 9, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    int courseNum = stoi(wordss.at(1));
                    shortToBytes((short) courseNum, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                }
                if (firstwords == "UNREGISTER") {
                    shortToBytes((short) 10, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                    int courseNum = stoi(wordss.at(1));
                    shortToBytes((short) courseNum, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                }
                if (firstwords == "MYCOURSES") {
                    shortToBytes((short) 11, bytesArr);
                    handler.sendBytes(bytesArr, 2);
                }

            delete bytesArr;

        }
    }




}
vector<string> ClientToServer::splitString(string s, char c) {
    stringstream str1(s);
    string tmp;
    vector<string> words;
    while (getline(str1, tmp, c)) {
        words.push_back(tmp);
    }
    return words;

}




