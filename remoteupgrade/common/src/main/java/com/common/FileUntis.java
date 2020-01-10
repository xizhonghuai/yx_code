package com.common;

import com.mina.Methods;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName FileUntis
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2020/1/2
 * @Version V1.0
 **/
public class FileUntis {


    public static LinkedList<String> readTxtFile(String filePath){
        LinkedList<String> list = new LinkedList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filePath));
            String str;
            while ((str = in.readLine()) != null) {

                if (str.length()>1){
                    str = str.substring(1);
                    list.add(Methods.hexStr2Byte(str));
                }
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return list;
    }

    /**
     * 创建父级文件夹
     *
     * @param file
     *            完整路径文件名(注:不是文件夹)
     */
    public static void createParentPath(File file) {
        File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            parentFile.mkdirs(); // 创建文件夹
            createParentPath(parentFile); // 递归创建父级目录
        }
    }
}
