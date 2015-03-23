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
        	//�趨����˵Ķ˿ں�  
            s = new ServerSocket(PORT);  
            System.out.println("ServerSocket Start:"+s);  
            while(true){
                //�ȴ�����,�˷�����һֱ����,ֱ����������������  
                socket = s.accept();  
                System.out.println("Connection accept socket:"+socket);  
                // ��ȡ�ͻ�������    
                InputStream input = socket.getInputStream();  
                DataInputStream dataOS = new DataInputStream(input);
    			InputStreamReader outSW = new InputStreamReader(dataOS, "UTF-8");
    			br = new BufferedReader(outSW);
    			sendMessage(socket,br.readLine().trim());  //��ͻ��˷�����Ϣ
//                byte[] by=new byte[1024];  
//                input.read(by);  
//                String str=new String(by);  
//                System.out.println("�ͻ��ˣ�"+str); 
//                sendMessage(socket,str.trim());  //��ͻ��˷�����Ϣ
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
     * ������Ϣ
     * @param message
     */
    public static void sendMessage(Socket socket,String message){
    	if("pc".equals(message)){
    		//����ͼƬ��pc��
    		reply(socket,"accept");
    	}else{
    		String[] str = message.split("@@@");
    		if("accept".equals(str[0])){
    			try {
    				System.out.println("----"+str[1]);
    				Socket socket2 = new Socket("192.168.23.3",6665);
    				//�������ݸ�pc��
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
     * �ظ��ķ���
     */
	public static void reply(Socket socket, String content) {
		// ��ͻ��˻ظ���Ϣ
		try {
			DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
			BufferedImage image = SocketTools.qRCodeCommon(content,10); // ��ȡ1.gif������
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			boolean flag = ImageIO.write(image, "png", out);
			byte[] b = out.toByteArray();
			dout.write(b);
			dout.close();
			// ���ͼ��������һ��
//			String s2 = content;
//			out.write(s2.getBytes());
//			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    

}
