package com.yj.tech.file.service.impl;

import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  交大云存储文件实现类
 */
public class Boto3Service extends S3Service {

  public Boto3Service(FileChoiceProperties properties) {
    super(properties);
  }

}
