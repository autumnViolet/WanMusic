package com.socket.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class FirstExample {
	public FirstExample(){  
        try {  
            InputStream is=new Socket("10.0.0.27",6665).getInputStream();  
            byte[] by=new byte[1024];  
            is.read(by);  
            String str=new String(by);  
            System.out.println(str);  
            is.close();  
        } catch (UnknownHostException e) {  
           e.printStackTrace();  
        } catch (IOException e) {  
           e.printStackTrace();  
        }  
    }  
      
    public static void main(String[] args){  
        new FirstExample();  
    }  
}
