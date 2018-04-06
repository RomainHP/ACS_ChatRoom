package chatroom.client;

import chatroom.client.message.Message;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chatroom.client.ui.Display;

public interface Listener extends Remote {

    /**
     * Set the output (class Display)
     * @param out new output
     * @throws RemoteException 
     */
    public abstract void setOutput(Display out) throws RemoteException;

    /**
     * receive a message and display on the output
     * @param aMsg message received
     * @throws RemoteException
     * @throws IOException 
     */
    public abstract void receiveMessage(Message aMsg) throws RemoteException, IOException;
}
