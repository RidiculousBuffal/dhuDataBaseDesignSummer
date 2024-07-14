package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Config.TecentConfig;
import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Util.TencentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@CrossOrigin
public class UploadController {
    @Autowired
    private TencentUtil tencentUtil;
    @Autowired
    private TecentConfig tecentConfig;
    @PostMapping("/upload")
    public Result<String> upload (@RequestBody MultipartFile file) throws IOException{
        tencentUtil.init();
        String originalFileName = file.getOriginalFilename();
        file.transferTo(new File(tecentConfig.getFilePath()+originalFileName));
        File uploadFile = new File(tecentConfig.getFilePath() + originalFileName);
        return Result.success(tencentUtil.upLoadFile(uploadFile,originalFileName));
    }

}
