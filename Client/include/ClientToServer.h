
#ifndef BOOST_ECHO_CLIENT_CLIENTTOSERVER_H
#define BOOST_ECHO_CLIENT_CLIENTTOSERVER_H
#include "connectionHandler.h"

class ClientToServer {
public:
    ClientToServer(ConnectionHandler &connectionHandler, bool *isTerminated, bool *logIn);
    void operator()();
    std::vector<std::string> splitString(std::string s, char c);

private:
    ConnectionHandler &handler;
    std::vector<std::string> parseFollow(std::string match);
    bool *isTerminated;
    bool *logIn;
};



#endif
