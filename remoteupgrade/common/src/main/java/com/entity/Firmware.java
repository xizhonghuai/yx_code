package com.entity;

import java.util.Date;

/**
 * @ClassName Firmware
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/31
 * @Version V1.0
 **/
public class Firmware {

    private String name;
    private String description;
    private Date updt;

    public Firmware() {
    }

    public Firmware(String name, String description, Date updt) {
        this.name = name;
        this.description = description;
        this.updt = updt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdt() {
        return updt;
    }

    public void setUpdt(Date updt) {
        this.updt = updt;
    }

    @Override
    public String toString() {
        return "Firmware{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", updt=" + updt +
                '}';
    }
}
