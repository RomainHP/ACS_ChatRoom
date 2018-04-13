package chatroom.client;

import chatroom.client.message.Message;
import chatroom.client.ui.display.Display;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
    public void receiveMessage(Message aMsg) throws RemoteException{
        // display on client interface
        out.write(aMsg);
    }
}
