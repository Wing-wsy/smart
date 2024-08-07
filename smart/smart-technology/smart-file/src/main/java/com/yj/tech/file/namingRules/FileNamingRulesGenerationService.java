package com.yj.tech.file.namingRules;

import cn.hutool.core.io.file.FileNameUtil;
import org.springframework.web.multipart.MultipartFile;

public interface FileNamingRulesGenerationService {

  String getFileName(MultipartFile file);

  /**
   * 获取文件扩展名，不带 .
   */
  default String getExtensionName(String filename) {
    return FileNameUtil.extName(filename);
  }
}
