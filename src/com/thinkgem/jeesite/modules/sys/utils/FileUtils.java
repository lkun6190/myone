package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.PropertiesLoader;
import com.thinkgem.jeesite.modules.file.entity.SysFiles;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 功能模块【文件保存存工具类】
 * 功能说明
 * User: xianqinhong
 * Date: 2016-12-05
 * Time: 16:57
 */
public class FileUtils {
    public static final String PATH = Global.getUserfilesBaseDir();

    /**
     * @param file
     * @return 返回保存的文件名
     */
    public static final String saveFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileId = "";
        File dir = new File(PATH);
        if (!dir.exists()) {
            dir.mkdir();
        }

        if (!"".equals(fileName)) {
            fileId = UUID.randomUUID().toString().replace("-", "");
            fileName = fileId + "_" + fileName;
            String localPath = PATH + fileName;
            File newFile = new File(localPath);
            try {
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return fileName;

    }

    public static final void deleteFile(  SysFiles f)  {
        File file = new File(PATH + f.getWjlj() );
        if(file.exists()){
            file.delete();
        }
    }

    public static final void outFile(OutputStream os , SysFiles f ) throws FileNotFoundException {

        File file = new File(PATH + f.getWjlj() );
        InputStream inputStream = new FileInputStream(file);

        byte[] b = new byte[2048];
        int length;
        try {
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.close();

            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
