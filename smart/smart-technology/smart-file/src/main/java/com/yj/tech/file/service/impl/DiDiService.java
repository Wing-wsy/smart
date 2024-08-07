package com.yj.tech.file.service.impl;


import com.yj.tech.file.properties.FileChoiceProperties;

/**
 *  滴滴云存储文件实现类
 */
public class DiDiService extends S3Service {

  public DiDiService(FileChoiceProperties properties) {
    super(properties);
  }

}
