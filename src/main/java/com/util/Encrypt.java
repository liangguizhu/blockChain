package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
    private String SHA(final String strText,final String strType){
        //返回值
        String strResult = null;
        //判断是否有效字符串
        if (strText != null && strText.length()>0){
            try {
                //创建加密对象，传入加密类型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                //传入要加密的字符串
                messageDigest.update(strText.getBytes());
                //得到byte数组
                byte[] byteBuffer = messageDigest.digest();
                //System.out.println("messageDigest.digest:"+byteBuffer);
                //将byte数组转换string类型
                StringBuffer strHexstring = new StringBuffer();
                //遍历byte数组
                for (int i=0;i<byteBuffer.length;i++){
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length()==1){
                        strHexstring.append('0');
                    }
                    strHexstring.append(hex);
                }
                strResult = strHexstring.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }

    /**
     * 传入字符串,返回SHA-256字符串
     * @param strText
     * @return
     */
    public String getSHA256(final String strText){
        return SHA(strText,"SHA-256");
    }

    public String getSHA512(final String strText){
        return SHA(strText,"SHA-512");
    }

    public String getMD5(final String strText){
        return SHA(strText,"MD5");
    }
}
