package com.yj.tech.file.admin.controller;

import com.yj.tech.constant.pojo.vo.ResultVO;
import com.yj.tech.file.graphics2D.entity.FileUpload;
import com.yj.tech.file.service.FileServiceInterface;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author wing
 * @create 2024/8/3
 */

@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileServiceInterface fileServiceInterface;

    public FileController(FileServiceInterface fileServiceInterface) {
        this.fileServiceInterface = fileServiceInterface;
    }

    /**
     * 文件上传-MultipartFile
     */
    @PostMapping("/{bucket}/upload")
    public ResultVO<FileUpload> upload(@RequestPart("file") MultipartFile file,
                                       @PathVariable("bucket") String bucket) throws Exception {

        /** 上传文件 */
        // file：文件；bucket：桶名 ( 桶 bucket 的概念类似于不同的库)
        FileUpload fileUpload = fileServiceInterface.upload(file,bucket);
        return ResultVO.success(fileUpload);

        //分享文件
//        String shareUrl = fileServiceInterface.share("文件名","桶名",5000L, TimeUnit.SECONDS);
        //分片上传
//        FileUpload fileUpload = fileServiceInterface.multipartUpload(file,"桶名",true);
        //删除文件
//        fileServiceInterface.deleteFile("文件名","桶名");
        //拷贝文件
//        boolean     flag        = fileServiceInterface.copyObject("原桶名","目标桶名","原文件名","目标文件名");
    }

    /**
     * 文件上传-Base64
     */
    @PostMapping("/{bucket}/uploadBase64")
    public ResultVO<FileUpload> uploadBase64(String base64Data,
                                       @PathVariable("bucket") String bucket) throws Exception {
        // 解码Base64字符串
        byte[] data = Base64.getDecoder().decode(base64Data);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        FileUpload fileUpload = fileServiceInterface.upload(byteArrayInputStream, bucket, "1233.jpg");
        return ResultVO.success(fileUpload);
    }

    /**
     * 文件下载
     */
    // 桶 bucket 的概念类似于不同的库
    @GetMapping("/{bucket}/download/{fileId}")
    public ResponseEntity<InputStreamResource> upload(
            @PathVariable("bucket") String bucket, @PathVariable("fileId") String fileId) throws Exception {
        //下载文件流
        InputStream inputStream = fileServiceInterface.download(fileId, bucket);
        System.out.println("fileId=" + fileId);
        // 设置文件响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileId);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(inputStream));
    }
}
