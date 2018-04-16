package chatroom.server;

import java.rmi.Naming;

class Server {

    private static final String rebind_name = "login";

    public static void main(String[] args) {
        Login log;
        try {
            System.out.println("Creation de l'objet.");
            log = new LoginImpl();
            System.out.println("Enregistrement de l'objet.");
            Naming.rebind(rebind_name, log);
            System.out.println("serveur operationnel.");
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
