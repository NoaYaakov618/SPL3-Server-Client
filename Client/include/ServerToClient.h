
#ifndef BOOST_ECHO_CLIENT_SERVERTOCLIENT_H
#define BOOST_ECHO_CLIENT_SERVERTOCLIENT_H
#include "connectionHandler.h"

class ServerToClient {
public:
    ServerToClient(ConnectionHandler &connectionHandler,bool *isTermiatedbool ,bool *logIn);
    void operator()();
    short bytesToShort(char* bytesArr,int indexOfstart,int indexTofinish);

private:
    ConnectionHandler &handler;
    bool *isTermiated;
    bool *logIn;
};


#endif //BOOST_ECHO_CLIENT_SERVERTOCLIENT_H
