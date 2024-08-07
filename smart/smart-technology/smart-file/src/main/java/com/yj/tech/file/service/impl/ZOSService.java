package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 * 天翼云文件实现类
 */
public class ZOSService extends S3Service {

  public ZOSService(FileChoiceProperties properties) {
    super(properties);
  }

}
