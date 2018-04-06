package chatroom.client;

import chatroom.client.message.Message;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import chatroom.client.ui.Display;

public class ListenerImpl extends UnicastRemoteObject implements Listener {

    /**
     *
     */
    private static final long serialVersionUID = -6990192506189041301L;

    private Display out;

    public ListenerImpl(Display out) throws RemoteException {
        this.out = out;
    }

    @Override
    public void setOutput(Display out) throws RemoteException {
        this.out = out;
    }

    @Override
    public void receiveMessage(Message aMsg) throws RemoteException, IOException {
        // display on client interface
        out.write(aMsg);
    }
}
