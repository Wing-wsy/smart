package com.yj.tech.file.service.impl;

import com.yj.tech.file.properties.FileChoiceProperties;

/**
 * 七牛云文件实现类
 */
public class KODOService extends S3Service {

  public KODOService(FileChoiceProperties properties) {
    super(properties);
  }

}
