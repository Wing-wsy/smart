package com.yj.tech.file.service.impl;

import com.yj.tech.file.properties.FileChoiceProperties;

/**
 * 移动云文件实现类
 */
public class EOSService extends S3Service {

  public EOSService(FileChoiceProperties properties) {
    super(properties);
  }

}
