package com.yj.tech.file.service.impl;

import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  R2云存储文件实现类
 */
public class R2Service extends S3Service {

  public R2Service(FileChoiceProperties properties) {
    super(properties);
  }

}
