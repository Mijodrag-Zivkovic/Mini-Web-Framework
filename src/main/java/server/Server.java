package server;

import framework.DiscoveryMechanism;
import framework.ImprovisedStorage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int TCP_PORT = 8080;

    public static void main(String[] args) throws IOException {

        ImprovisedStorage.fillUsers();
        DiscoveryMechanism discoveryMechanism = new DiscoveryMechanism();
        try {
            ServerSocket serverSocket = new ServerSocket(TCP_PORT);
            System.out.println("Server is running at http://localhost:"+TCP_PORT);
            while(true){
                Socket socket = serverSocket.accept();
                new Thread(new ServerThread(socket, discoveryMechanism.getControllerClasses())).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
