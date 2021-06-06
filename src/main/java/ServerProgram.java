import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerProgram {

    private Set<ServiceThread> newConntect = new HashSet<>();


    void execute() throws IOException {
        ServerSocket listener = null;

        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;


        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {


                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.

                Socket socketOfServer = listener.accept();
                ServiceThread serviceThread= new ServiceThread(socketOfServer, clientNumber++, this);
                newConntect.add(serviceThread);
                serviceThread.start();
            }
        } finally {
            listener.close();
        }
    }

    public static void main(String args[]) throws IOException {
        ServerProgram serverProgram = new ServerProgram();
        serverProgram.execute();
    }

    void broadcast(String message, ServiceThread excludeUser) throws IOException {
        for (ServiceThread aUser : newConntect) {
            if (aUser != excludeUser) {
                aUser.sendMess(message);
            }
        }
    }

    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;
        BufferedWriter os;
        ServerProgram server;

        public ServiceThread(Socket socketOfServer, int clientNumber, ServerProgram server) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;
            this.server = server;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }

        @Override
        public void run() {

            try {

                // Mở luồng vào ra trên Socket tại Server.
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {
                    // Đọc dữ liệu tới server (Do client gửi tới).
                    String line = is.readLine();
                    System.out.println(line);
                    // Ghi vào luồng đầu ra của Socket tại Server.
                    // (Nghĩa là gửi tới Client).
//                    os.write(">> " + line);
//                    // Kết thúc dòng
//                    os.newLine();
//                    // Đẩy dữ liệu đi
//                    os.flush();

                    server.broadcast(line,this);

                    // Nếu người dùng gửi tới QUIT (Muốn kết thúc trò chuyện).
//                    if (line.equals("QUIT")) {
//                        os.write(">> OK");
//                        os.newLine();
//                        os.flush();
//                        break;
//                    }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

        void sendMess(String mess) throws IOException {
            os.write(">> " + mess);
            // Kết thúc dòng
            os.newLine();
            // Đẩy dữ liệu đi
            os.flush();
        }
    }
}
