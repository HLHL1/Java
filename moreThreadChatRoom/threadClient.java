package hhh.Test.MyThread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
/**
 * 读取服务器信息线程
 */
class ReadFromServerThread implements Runnable {

    private Socket client;

    public ReadFromServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //获取客户端输入流
            Scanner in=new Scanner(client.getInputStream());
            in.useDelimiter("\n");
            while(true){
                if(in.hasNext()){
                    System.out.println("从服务器发来的消息为："+in.next());//碰到空格结束
                }
                if(client.isClosed()){
                    System.out.println("客户端已经关闭");
                    break;
                }
            }
            in.close();
        } catch (IOException e) {
            System.err.println("客户端读线程异常，错误为 "+e);
        }
    }
}
/**
 * 将信息发送给服务器线程
 */
class WriteToServerThread implements Runnable {
    private Socket client;

    public WriteToServerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {

            //从键盘中输入
            Scanner cin=new Scanner(System.in);
            cin.useDelimiter("\n");
            //获取客户端输出流
            PrintStream out=new PrintStream(client.getOutputStream());
            while(true){
                System.out.println("请输入要发送的信息..");
                String msg;
                //只能读一行信息
                if(cin.hasNext()){
                    msg=cin.nextLine().trim();//读取信息，删除信息的收尾空白
                    out.println(msg);
                    if(msg.equals("byebye")){
                        System.out.println("关闭客户端");
                        cin.close();
                        out.close();
                        client.close();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("客户端写线程异常，错误为 "+e);
        }

    }
}
public class threadClient{

    public static void main(String[] args) {
        try {
            //连接服务器
            Socket client=new Socket(InetAddress.getLocalHost(),6666);
            //读取服务器消息
            Thread readFromServer = new Thread(new
                    ReadFromServerThread(client));
            //向服务器发送消息
            Thread writeToServer = new Thread(new
                    WriteToServerThread(client));
            readFromServer.start();
            writeToServer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}