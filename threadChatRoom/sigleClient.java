package hhh.Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class sigleClient {
    public static void main(String[] args)throws IOException {
        Socket cilent=null;
        Scanner in=null;
        PrintStream out=null;
        try {
            cilent=new Socket("127.0.0.1",6666);
            out=new PrintStream(cilent.getOutputStream(),true,"UTF-8");
            in=new Scanner(cilent.getInputStream());
            out.println("Hello Server，I am cilent!");
            if(in.hasNext()){
                System.out.println("服务器说："+in.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            cilent.close();
            in.close();
            out.close();
        }

    }
}
