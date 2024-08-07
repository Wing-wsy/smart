package com.yj.tech.file.service.impl;

import com.yj.tech.file.properties.FileChoiceProperties;

/**
 * 谷歌云文件实现类
 */
public class GoogleCloudStorageService extends S3Service {

  public GoogleCloudStorageService(FileChoiceProperties properties) {
    super(properties);
  }

}
