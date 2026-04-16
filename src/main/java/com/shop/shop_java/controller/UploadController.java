package com.shop.shop_java.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/upload")
public class UploadController {

    @PostMapping("/image")
    public Map<String, Object> uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        if (file.isEmpty()) {
            result.put("code", 400);
            result.put("msg", "上传文件不能为空");
            return result;
        }
        
        try {
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
            
            File uploadDir = new File(System.getProperty("user.dir"), "uploads");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            File destFile = new File(uploadDir, fileName);
            file.transferTo(destFile.getAbsoluteFile());
            
            // 构建访问 URL
            String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
            String fileUrl = baseUrl + "/uploads/" + fileName;
            
            Map<String, String> data = new HashMap<>();
            data.put("url", fileUrl);
            data.put("name", originalFilename);
            
            result.put("code", 200);
            result.put("msg", "上传成功");
            result.put("data", data);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("msg", "上传失败: " + e.getMessage());
        }
        
        return result;
    }
}
