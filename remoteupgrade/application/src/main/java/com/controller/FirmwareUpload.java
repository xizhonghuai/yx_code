package com.controller;

import com.common.FileUntis;
import com.common.Response;
import com.entity.Firmware;
import com.initialization.SystemArgsInit;
import com.server.UpgradeServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FirmwareUpload
 * @Description: TODO
 * @Author xizhonghuai
 * @Date 2019/12/31
 * @Version V1.0
 **/
@RestController
public class FirmwareUpload {

    @Autowired
    SystemArgsInit systemArgsInit;

    private Logger logger = Logger.getLogger(UpgradeServer.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Response upload(Firmware firmware,@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return new Response(500,"上传失败，请选择文件");
        }
        String fileName = file.getOriginalFilename();
        String filePath = systemArgsInit.getFirmwareBasePath()+"/"+ UUID.randomUUID().toString()+"/";
        File dest = new File(filePath + fileName);
        FileUntis.createParentPath(dest);
        try {
            file.transferTo(dest);
            logger.info(fileName+"-----------上传成功");
        } catch (IOException e) {
            logger.error(e.toString(), e);
            return new Response(500,"上传失败，"+e.getMessage());
        }
        systemArgsInit.setCurFirmware(filePath + fileName);
        return new Response();
    }
}
