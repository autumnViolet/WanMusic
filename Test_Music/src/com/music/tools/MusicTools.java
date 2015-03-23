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
 * 通用工具方法类
 * @author Administrator
 *
 */
public class MusicTools {
	
	/**
	 * 保存图片
	 * @param bmp
	 */
	public static void saveBitmap(Bitmap bmp)  
    {  
        String path = Environment.getExternalStorageDirectory().getPath();
    	//存储路径  
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
	 * 生成二维码
	 * @param str
	 * @return
	 * @throws WriterException
	 */
	public static Bitmap create2DCode(String str) throws WriterException {       
        //生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败       
        BitMatrix matrix = new MultiFormatWriter().encode(str,BarcodeFormat.QR_CODE, 600, 600);       
        int width = matrix.getWidth();       
        int height = matrix.getHeight();       
        //二维矩阵转为一维像素数组,也就是一直横着排了       
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
        //通过像素数组生成bitmap,具体参考api       
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);  
        saveBitmap(bitmap);
        return bitmap;       
    }  
	
	/**
	 * 将图片转换成字节码
	 * @param bitmap
	 * @return
	 */
	public static byte[] img(Bitmap bitmap){
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	    return baos.toByteArray();
	}
	
	/**
	 * 将String数组转为String
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
