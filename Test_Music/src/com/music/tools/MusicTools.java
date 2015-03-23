package com.music.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
/**
 * ͨ�ù��߷�����
 * @author Administrator
 *
 */
public class MusicTools {
	
	/**
	 * ����ͼƬ
	 * @param bmp
	 */
	public static void saveBitmap(Bitmap bmp)  
    {  
        String path = Environment.getExternalStorageDirectory().getPath();
    	//�洢·��  
//        File file = new File(path + "/song/"); 
        File file = new File(path, "song");
        if(!file.exists())  
            file.mkdirs();  
        try {  
             FileOutputStream fileOutputStream = new FileOutputStream(file.getPath() + "/wanMusic.png");  
             bmp.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);  
             fileOutputStream.close();  
        } catch (Exception e) {  
             e.printStackTrace();  
        }  
    } 
	
	/**
	 * ���ɶ�ά��
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap create2DCode(String str) throws WriterException {       
        //���ɶ�ά����,����ʱָ����С,��Ҫ������ͼƬ�Ժ��ٽ�������,������ģ������ʶ��ʧ��       
        BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, 600, 600);       
        int width = matrix.getWidth();       
        int height = matrix.getHeight();       
        //��ά����תΪһά��������,Ҳ����һֱ��������       
        int[] pixels = new int[width * height];       
        for (int y = 0; y < height; y++) {       
            for (int x = 0; x < width; x++) {       
                if(matrix.get(x, y)){       
                    pixels[y * width + x] = 0xff000000;       
                }else{
                	pixels[y * width + x] = 0xffffffff; 
                }
            }       
        }       
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);       
        //ͨ��������������bitmap,����ο�api       
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        saveBitmap(bitmap);
        return bitmap;       
    }  
	
	/**
	 * ��ͼƬת�����ֽ���
	 * @param bitmap
	 * @return
	 */
	public static byte[] img(Bitmap bitmap){
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	    return baos.toByteArray();
	}
	
	/**
	 * ��String����תΪString
	 */
	public static String asString(String[] content){
		StringBuilder temp = new StringBuilder();
		int i;
		for(i=0;i<content.length;i++){
			temp.append(content[i]);
		}
		return temp.toString();
	}


}
