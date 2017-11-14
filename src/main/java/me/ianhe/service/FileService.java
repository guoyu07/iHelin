package me.ianhe.service;

import com.google.common.collect.Lists;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author iHelin
 * @since 2017/11/13 18:02
 */
@Service
public class FileService {


    private Logger logger = LoggerFactory.getLogger(getClass());
    private Configuration configuration;
    private Auth auth;

    @Autowired
    private Global global;

    @PostConstruct
    public void init() {
        Zone zone = Zone.autoZone();
        configuration = new Configuration(zone);
        auth = Auth.create(global.getValue("qiniu.access.key"), global.getValue("qiniu.secret.key"));
    }

    /**
     * 获取七牛文件列表
     *
     * @author iHelin
     * @since 2017/11/13 18:25
     */
    public List<FileInfo> getFileInfoList(String prefix, String delimiter) {
        BucketManager bucketManager = new BucketManager(auth, configuration);
        BucketManager.FileListIterator fileListIterator = bucketManager
                .createFileListIterator(global.getValue("qiniu.bucket"), prefix, 1000, delimiter);
        List<FileInfo> fileInfoList = Lists.newArrayList();
        while (fileListIterator.hasNext()) {
            fileInfoList.addAll(Arrays.asList(fileListIterator.next()));
        }
        return fileInfoList;
    }

    /**
     * 以字节流上传
     * 上传成功返回文件访问路径
     *
     * @param inputStream
     * @param key
     * @return 文件访问地址
     */
    public String uploadFile(String key, InputStream inputStream) {
        UploadManager uploadManager = new UploadManager(configuration);
        String token = auth.uploadToken(global.getValue("qiniu.bucket"));
        try {
            Response res = uploadManager.put(inputStream, key, token, null, null);
            logger.info("upload file {} to qiniu oss,result:{}", key, res.isOK());
            return global.getValue("qiniu.prefix") + key;
        } catch (QiniuException e) {
            logger.error("error upload file to qiniu ！", e);
            return "";
        }
    }

    /**
     * 删除文件
     *
     * @author iHelin
     * @since 2017/11/13 23:07
     */
    public void deleteFile(String key) {
        BucketManager bucketManager = new BucketManager(auth, configuration);
        try {
            bucketManager.delete(global.getValue("qiniu.bucket"), key);
        } catch (QiniuException e) {
            logger.error("删除失败", e);
        }
    }

}
