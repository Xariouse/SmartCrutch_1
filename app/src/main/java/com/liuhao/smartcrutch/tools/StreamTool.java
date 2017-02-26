package com.liuhao.smartcrutch.tools;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {
    public static String readStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int lenth = -1;
        while((lenth = inputStream.read(buffer))!=-1){
            baos.write(buffer,0,lenth);
        }
        inputStream.close();
        return baos.toString();
    }
}
