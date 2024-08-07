package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  B2云存储文件实现类
 */
public class B2Service extends S3Service {

  public B2Service(FileChoiceProperties properties) {
    super(properties);
  }

}
