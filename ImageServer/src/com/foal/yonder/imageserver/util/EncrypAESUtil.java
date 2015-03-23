package com.foal.yonder.imageserver.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * @Author: pokiz 徐秀滨
 * @Description: AES加密与解密工具
 * @Date: 2013-9-3
 * @Param: 
 * @Return: 
 * @Exception: 
 */
public class EncrypAESUtil
{
    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法  
    private static KeyGenerator keygen;  
    //SecretKey 负责保存对称密钥  
    private static SecretKey deskey;  
    //Cipher负责完成加密或解密工作  
    private static Cipher cipher;  
    //该字节数组负责保存加密的结果  
    private static byte[] cipherByte;  
      
    static 
    {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        try
        {
            keygen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
            String key = "cipher";
            secureRandom.setSeed(key.getBytes());  
            keygen.init(128 ,secureRandom);  
            // 生成密钥
            deskey = keygen.generateKey();
            // 生成Cipher对象,指定其支持的DES算法
            cipher = Cipher.getInstance("AES");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
      
    /** 
     * 对字符串加密 
     * @param str 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     * @throws UnsupportedEncodingException 
     */  
    public static byte[] encrytor(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException 
    {  
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, deskey);  
        byte[] src = str.getBytes("utf-8");  
        // 加密，结果保存进cipherByte  
        cipherByte = cipher.doFinal(src);  
        return cipherByte;  
    }  
    
    /**
     * @Description: 传入明文加密，加密后的二进制转成十六进制
     * @Date: 2013-9-3
     * @Param: 
     * @Return: 
     * @Exception: 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException 
     */
    public static String encrytToString(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException 
    {  
        byte[] bytes = encrytor(str);
        return parseByte2HexStr(bytes);
    }  
  
    /** 
     * 对字符串解密 
     * @param buff 
     * @return 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public static byte[] decryptor(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
    {  
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式  
        cipher.init(Cipher.DECRYPT_MODE, deskey);  
        cipherByte = cipher.doFinal(buff);  
        return cipherByte;  
    }  
    
    /**
     * @Description: 传入十六进制的字符串，解密后再转成字符串
     * @Date: 2013-9-3
     * @Param: 
     * @Return: 
     * @Exception: 
     * @param str
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws UnsupportedEncodingException 
     */
    public synchronized static String decryptToString(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException 
    {  
        byte[] bytes = parseHexStr2Byte(str);
        return new String(decryptor(bytes), "utf-8");
    }  
    
    /**
     * @Description: 2进制转16进制
     * @Date: 2013-9-3
     * @Param: 
     * @Return: 
     * @Exception: 
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[])
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if(hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */ 
    public static byte[] parseHexStr2Byte(String hexStr)
    {
        if (hexStr.length() < 1)
        {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
  
    /** 
     * @param args 
     * @throws NoSuchPaddingException  
     * @throws NoSuchAlgorithmException  
     * @throws BadPaddingException  
     * @throws IllegalBlockSizeException  
     * @throws InvalidKeyException  
     */  
    public static void main(String[] args) throws Exception 
    {  
        String msg ="xx公司";  
        System.out.println("明文是:" + msg);  
        String s = EncrypAESUtil.encrytToString(msg);
        System.out.println("加密后:" + s);  
        System.out.println("解密后:" + EncrypAESUtil.decryptToString(s));  
    }  
}
