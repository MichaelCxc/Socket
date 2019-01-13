import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        //超时时间
        socket.setSoTimeout(3000);

        //连接本地，端口2000，超时时间3000ms
        socket.connect(new InetSocketAddress(Inet4Address.getLocalHost(), 2000), 3000);

        System.out.println("已发起服务器连接，并进入后续流程");
        System.out.println("客户端信息： "+ socket.getLocalAddress() +
                " P: " + socket.getLocalPort());
        System.out.println("服务端信息： " + socket.getInetAddress() + " P: " + socket.getPort());

        try{
            todo(socket);
        }catch (Exception e){
            System.out.println("异常关闭");
        }

        socket.close();
        System.out.println("客户端已退出");

    }

    private static void todo(Socket client) throws IOException{
        InputStream in = System.in;
        BufferedReader input = new BufferedReader(new InputStreamReader(in));



        OutputStream outputStream = client.getOutputStream();
        PrintStream socketPrintStream = new PrintStream(outputStream);

        InputStream inputStream = client.getInputStream();
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(inputStream));

        boolean flag = true;
        do{
            String str = input.readLine();
            //发送到服务器
            socketPrintStream.println(str);

            //从服务器读取一行
            String scho = socketReader.readLine();
            if("bye".equalsIgnoreCase(scho)){
                flag = false;
            }else{
                System.out.println(scho);
            }
        }while(flag);

        //资源释放
        socketPrintStream.close();
        socketReader.close();


    }
}
