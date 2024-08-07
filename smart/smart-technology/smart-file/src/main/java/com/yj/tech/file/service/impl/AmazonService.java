package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  亚马逊云存储文件实现类
 */
public class AmazonService extends S3Service {

  public AmazonService(FileChoiceProperties properties) {
    super(properties);
  }

}
