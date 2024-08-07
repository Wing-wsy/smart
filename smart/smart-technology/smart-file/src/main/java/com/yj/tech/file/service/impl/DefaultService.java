package com.yj.tech.file.service.impl;

import com.yj.tech.file.code.FileErrorCode;
import com.yj.tech.file.graphics2D.entity.FileUpload;
import com.yj.tech.file.properties.FileChoiceProperties;
import com.yj.tech.utils.exception.BaseException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * 默认文件实现类
 */
public class DefaultService extends AbstractFileService {


  public DefaultService(FileChoiceProperties properties) {
    super(properties);
  }

  @Override
  protected void upload(MultipartFile file, String bucketName, String filePath) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected void delete(String bucketName, String filePath) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected String shareUrl(String bucketName, String filePath, long expiry, TimeUnit unit) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected InputStream getObject(String bucketName, String filePath) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected void createBucket(String bucketName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }


  @Override
  public FileUpload multipartUpload(MultipartFile file, String bucketName, boolean isUseOriginalName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected void multipartUpload(MultipartFile file, String bucketName, long fileSize, String uploadId, String filePath,
      int partCount) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  public boolean bucketExists(String bucketName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  public List<String> listObjects(String bucketName, String key) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  public String initiateMultipartUploadTask(String bucketName, String objectName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected boolean checkObjectExist(String bucketName, String objectName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);

  }

  @Override
  protected boolean copyFile(String sourceBucket, String targetBucket, String sourceObjectName, String targetObjectName)
      throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  protected void abortMultipartUpload(String uploadId, String bucketName, String filePath) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  public void deleteBucket(String bucketName) throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }

  @Override
  public List<String> getBucketList() throws Exception {
    throw new BaseException(FileErrorCode.NO_STORAGE_IMPLEMENTATION);
  }
}
