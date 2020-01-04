package com.common;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @ClassName RedisDB
 * @Description TODO
 * @Author xizhonghuai
 * @Date 2019-05-10 15:40
 * @Version 1.0
 */
public class Conf {

    private Properties prop;
    private String fileSrc;

    public Conf(String fileName) throws IOException {

        this.fileSrc = Conf.class.getClassLoader().getResource(fileName).getPath();

        FileInputStream fis = new FileInputStream(this.fileSrc);// 属性文件输入流
        prop = new Properties();// 属性集合对象
        prop.load(fis);// 将属性文件流装载到Properties对象中
        fis.close();// 关闭流
    }

    public String getKeyValueAsString(String key, String defaultValue) {

        return prop.getProperty(key, defaultValue);
    }

    public Integer getKeyValueAsInteger(String key, Integer defaultValue) {



        return Integer.parseInt(prop.getProperty(key, String.valueOf(defaultValue)));
    }

    public Boolean getKeyValueAsBoolean(String key, Boolean defaultValue) {

        if (prop.getProperty(key, "0") == "0") {
            return false;
        }
        return true;
    }


    public void setKeyValue(String key, String value) {
        if (value != null)
            prop.setProperty(key, value);
    }

    public void setKeyValue(String key, Integer value) {
        prop.setProperty(key, String.valueOf(value));
    }

    public void setKeyValue(String key, Boolean value) {

        if (value) {
            prop.setProperty(key, "1");
        } else prop.setProperty(key, "0");

    }


    public void subm() throws Exception {
        FileOutputStream oFile = new FileOutputStream(this.fileSrc);
        prop.store(oFile, "----");
        oFile.close();
    }
}
