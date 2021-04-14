package util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.File;

public class AliOssUtil {
    public static final String endpoint = "填写终端地址";
    public static final String accessKeyId = "填写accessKeyId";
    public static final String accessKeySecret = "填写accessKeySecret";
    public static final String bucketName = "填写oss桶名";

    public static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

    public static void main(String[] args) {
        ossClient.putObject(bucketName, "ta.txt", new File("C:\\Users\\zhouyuyang\\Desktop\\t.txt"));
        ossClient.shutdown();
        System.out.println("结束上传！");
    }

    public static void upload(String name, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, name, file);

    }
}