package com.socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.socket.server.JabberServer;

public class JabberClient {
	/** 
     * ��������main ������ ���ߣ������� ���ڣ�2012-8-23 ����01:47:12 
     *  
     * @param @param args 
     * @return void 
     */  
    public static void main(String[] args) {  
        Socket socket = null;  
        BufferedReader br = null;  
        PrintWriter pw = null;  
        try {  
            //�ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�  
            socket = new Socket("127.0.0.1", JabberServer.PORT);  
            System.out.println("Socket=" + socket);  
            //ͬ������ԭ��һ��  
            br = new BufferedReader(new InputStreamReader(  
                    socket.getInputStream()));  
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
                    socket.getOutputStream())));  
            for (int i = 0; i < 10; i++) {  
                pw.println("howdy " + i);  
                pw.flush();  
                String str = br.readLine();  
                System.out.println(str);  
            }  
            pw.println("END");  
            pw.flush();  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                System.out.println("close......");  
                br.close();  
                pw.close();  
                socket.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  

}
