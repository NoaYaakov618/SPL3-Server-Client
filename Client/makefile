CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system
CFLAGS += -pthread
LDFLAGS += -pthread

all: EchoClient
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/echoClient.o bin/ClientToServer.o bin/ServerToClient.o $(LDFLAGS)

EchoClient: bin/connectionHandler.o bin/echoClient.o bin/ClientToServer.o bin/ServerToClient.o

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/echoClient.o: src/echoClient.cpp
	g++ $(CFLAGS) -o bin/echoClient.o src/echoClient.cpp

bin/ClientToServer.o: src/ClientToServer.cpp
	g++ $(CFLAGS) -o bin/ClientToServer.o src/ClientToServer.cpp

bin/ServerToClient.o: src/ServerToClient.cpp
	g++ $(CFLAGS) -o bin/ServerToClient.o src/ServerToClient.cpp

.PHONY: clean
clean:
	rm -f bin/*

