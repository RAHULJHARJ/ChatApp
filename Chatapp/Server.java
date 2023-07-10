import java.net.*;
import java.io.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
// constructor..

    public Server() {
        try {
            server = new ServerSocket(7778);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting");
            socket=server.accept();

            br=new BufferedReader(new  InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
            startReading();
            startWriting();
        }
        catch (Exception e){
            //TODO: handle exception
            e.printStackTrace();
        }

        }
        public void startReading(){
        // thread-read the data and provide output
            Runnable r1=()->{
                System.out.println("Reader started..");

                try {

                while(true) {

                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("Client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("client:" + msg);
                }

                }catch (Exception e){
                   // e.printStackTrace();
                    System.out.println("connection is closed   ");
                }

            };
            new Thread(r1).start();
        }
        public void startWriting(){
// thread - take the use of data and send to the user
            Runnable r2=()->{
                System.out.println("Writer started...");
                try {
                    while (true && socket.isClosed()) {
                        BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                        String content = br1.readLine();
                        out.println(content);
                        out.flush();
                        if(content.equals("exit")){
                            socket.close();
                            break;
                        }
                    }
                    System.out.println("connection is closed");

                }catch (Exception e){
                    e.printStackTrace();
                }
            };
            new Thread(r2).start();
        }
        public static void main (String[]args){
            System.out.println("This is server...going to start server");
            new Server();
        }
}
