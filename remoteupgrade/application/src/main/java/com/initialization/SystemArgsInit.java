package com.initialization;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName SystemArgsInit
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/31
 * @Version V1.0
 **/
@Component
@ConfigurationProperties(prefix = "system-args-init")
public class SystemArgsInit {

    private String firmwareBasePath;
    private String curFirmware;


    public String getFirmwareBasePath() {
        return firmwareBasePath;
    }

    public void setFirmwareBasePath(String firmwareBasePath) {
        this.firmwareBasePath = firmwareBasePath;
    }

    public String getCurFirmware() {
        return curFirmware;
    }

    public void setCurFirmware(String curFirmware) {
        this.curFirmware = curFirmware;
    }
}
