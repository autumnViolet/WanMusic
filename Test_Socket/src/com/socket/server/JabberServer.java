package com.socket.server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.socket.tools.SocketTools;

public class JabberServer {
	public static int PORT = 6665;  
    public static void main(String[] agrs) {  
        ServerSocket s = null;  
        Socket socket = null;  
        BufferedReader br = null;  
        try {  
        	//设定服务端的端口号  
            s = new ServerSocket(PORT);  
            System.out.println("ServerSocket Start:"+s);  
            while(true){
                //等待请求,此方法会一直阻塞,直到获得请求才往下走  
                socket = s.accept();  
                System.out.println("Connection accept socket:"+socket);  
                // 读取客户端数据    
                InputStream input = socket.getInputStream();  
                DataInputStream dataOS = new DataInputStream(input);
    			InputStreamReader outSW = new InputStreamReader(dataOS, "UTF-8");
    			br = new BufferedReader(outSW);
    			sendMessage(socket,br.readLine().trim());  //向客户端发送消息
//                byte[] by=new byte[1024];  
//                input.read(by);  
//                String str=new String(by);  
//                System.out.println("客户端："+str); 
//                sendMessage(socket,str.trim());  //向客户端发送消息
				dataOS.close();
				outSW.close();
				br.close();
                input.close();  
            }
            
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }  
    
    /**
     * 发送消息
     * @param message
     */
    public static void sendMessage(Socket socket,String message){
    	if("pc".equals(message)){
    		//发送图片给pc端
    		reply(socket,"accept");
    	}else{
    		String[] str = message.split("@@@");
    		if("accept".equals(str[0])){
    			try {
    				System.out.println("----"+str[1]);
    				Socket socket2 = new Socket("192.168.23.3",6665);
    				//发送内容给pc端
        			DataOutputStream dataOS = new DataOutputStream(socket2.getOutputStream());
    				OutputStreamWriter outSW = new OutputStreamWriter(dataOS, "UTF-8");
    				BufferedWriter is = new BufferedWriter(outSW);
    	            is.write(str[1]);  
    	            is.flush();
    	            is.close();
//    				OutputStream out = (new Socket("192.168.23.4",6665)).getOutputStream();
    				
				} catch (Exception e) {
					e.printStackTrace();
				}  
    		}
    	}
    }
    
    /**
     * 回复的方法
     */
	public static void reply(Socket socket, String content) {
		// 向客户端回复信息
		try {
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			BufferedImage image = SocketTools.qRCodeCommon(content,10); // 读取1.gif并传输
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(image, "png", out);
			byte[] b = out.toByteArray();
			dout.write(b);
			dout.close();
			// 发送键盘输入的一行
//			String s2 = content;
//			out.write(s2.getBytes());
//			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    

}
