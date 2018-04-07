package chatroom.client;

import chatroom.client.message.Message;
import chatroom.client.ui.display.Display;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Listener extends Remote {

    /**
     * Set the output (class Display)
     *
     * @param out new output
     * @throws RemoteException
     */
    void setOutput(Display out) throws RemoteException;

    /**
     * receive a message and display on the output
     *
     * @param aMsg message received
     * @throws RemoteException
     * @throws IOException
     */
    void receiveMessage(Message aMsg) throws RemoteException;
}
