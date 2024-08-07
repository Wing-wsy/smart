package com.yj.tech.file.namingRules;

import com.yj.tech.utils.date.DateTimeUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 按照时间进行文件命名
 */
public class DateNamingRulesGenerationService implements FileNamingRulesGenerationService {

  @Override
  public String getFileName(MultipartFile file) {
    String contentType = getExtensionName(file.getOriginalFilename());
    return DateTimeUtils.getLocalDateTimeString("yyyy/MM/dd/HHmmssSSS") + "."+contentType;
  }
}
