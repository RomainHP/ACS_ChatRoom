package chatroom.client;

import java.io.Serializable;

public class Message implements Serializable {
    
    TypeMessage type;
    
    String msg;
    
    public Message(String msg, String nick){
        this.msg = nick + " : " + msg + "\n";
    }
    
    public byte[] getMessage(){
        return msg.getBytes();
    }
}
