package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  京东云存储文件实现类
 */
public class JDService extends S3Service {

  public JDService(FileChoiceProperties properties) {
    super(properties);
  }

}
