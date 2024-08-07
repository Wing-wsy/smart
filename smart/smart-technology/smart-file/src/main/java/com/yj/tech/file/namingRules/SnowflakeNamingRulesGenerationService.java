package com.yj.tech.file.namingRules;

import cn.hutool.core.lang.Snowflake;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用雪花用作文件名
 */
public class SnowflakeNamingRulesGenerationService implements FileNamingRulesGenerationService {

  private final Snowflake generator;

  public SnowflakeNamingRulesGenerationService(){
    this.generator = new Snowflake();
  }

  @Override
  public String getFileName(MultipartFile file) {
    String contentType = getExtensionName(file.getOriginalFilename());
    return new StringBuilder().append(generator.nextIdStr()).append(".").append(contentType).toString();
  }
}
