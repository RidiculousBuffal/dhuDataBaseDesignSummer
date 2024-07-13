package com.dhu.swimmingpool.Util;

import com.dhu.swimmingpool.Config.TecentConfig;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.AnonymousCOSCredentials;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Data
//@RestController
@Component
public class TencentUtil {
    @Autowired
    private TecentConfig tecentConfig;
    public  COSClient UploadClient;
    public  COSClient getUrlClient;

  public void init(){
      String secretId = tecentConfig.getSecretId();
      String secretKey = tecentConfig.getSecretKey();
      String region = tecentConfig.getRegion();
      COSCredentials cosCredentials = new BasicCOSCredentials(secretId,secretKey);
      Region _region = new Region(region);
      ClientConfig clientConfig = new ClientConfig(_region);
      clientConfig.setHttpProtocol(HttpProtocol.https);
//        返回上传cos客户端
      UploadClient = new COSClient(cosCredentials,clientConfig);
      COSCredentials URLcosCredentials = new AnonymousCOSCredentials();
//        返回获取url客户端
      getUrlClient = new COSClient(URLcosCredentials,clientConfig);
  }

    public  String  upLoadFile (File file,String Name){
        String bucketName = "hpcow-1316827225";
        System.out.println(Name);
        String key = "java/"+ UUID.randomUUID().toString()+Name;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,key,file);
        PutObjectResult putObjectResult = UploadClient.putObject(putObjectRequest);
        return getUrl(key);
    }
    private  String getUrl (String key){
//        不需要验证身份信息
        String bucketName = "hpcow-1316827225";
        return getUrlClient.getObjectUrl(bucketName,key).toString();
    }

}
