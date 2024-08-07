package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 * 微软文件实现类
 */
public class AzureService extends S3Service {

  public AzureService(FileChoiceProperties properties) {
    super(properties);
  }

}
