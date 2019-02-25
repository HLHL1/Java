package hhh.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class sigleServer {
    public static void main(String[] args)throws IOException {
        ServerSocket server=null;
        Socket cilent=null;
        Scanner cilentIn=null;
        PrintStream cilentOut=null;
        try {
            //建立基站
            server = new ServerSocket(6666);
            //等待客户端的连接,返回客户端Socket
            cilent=server.accept();
            cilentIn=new Scanner(cilent.getInputStream());
            cilentOut=new PrintStream(cilent.getOutputStream(),true,"UTF-8");
            if(cilentIn.hasNext()){
                System.out.println("客户端说："+cilentIn.nextLine());
            }
            cilentOut.println("Hello Client!，I am Server");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            server.close();
            cilentIn.close();
           cilentOut.close();
        }
    }

}
