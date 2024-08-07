package com.yj.tech.file.namingRules;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 用uuid用作文件名
 */
public class UUIDNamingRulesGenerationService implements FileNamingRulesGenerationService {

  @Override
  public String getFileName(MultipartFile file) {
    String contentType = getExtensionName(file.getOriginalFilename());
    return UUID.randomUUID().toString().replace("-","") + "."+contentType;
  }
}
