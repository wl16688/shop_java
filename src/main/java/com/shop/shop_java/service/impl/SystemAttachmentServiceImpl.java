package com.shop.shop_java.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.shop_java.entity.SystemAttachment;
import com.shop.shop_java.mapper.SystemAttachmentMapper;
import com.shop.shop_java.service.SystemAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

import java.util.UUID;
import java.io.File;
import java.io.IOException;

@Service
public class SystemAttachmentServiceImpl extends ServiceImpl<SystemAttachmentMapper, SystemAttachment> implements SystemAttachmentService {

    @Override
    public SystemAttachment upload(MultipartFile file, Integer pid) {
        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.lastIndexOf(".") != -1) {
            ext = originalName.substring(originalName.lastIndexOf("."));
        }
        String newName = UUID.randomUUID().toString().replace("-", "") + ext;

        File uploadDir = new File(System.getProperty("user.dir"), "uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File destFile = new File(uploadDir, newName);
        try {
            file.transferTo(destFile.getAbsoluteFile());
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败", e);
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String baseUrl = String.format("%s://%s:%d", request.getScheme(), request.getServerName(), request.getServerPort());
        String url = baseUrl + "/uploads/" + newName;

        SystemAttachment attachment = new SystemAttachment();
        attachment.setName(originalName);
        attachment.setAttDir(url);
        attachment.setSattDir(url);
        attachment.setAttSize(String.valueOf(file.getSize()));
        attachment.setAttType(file.getContentType());
        attachment.setPid(pid == null ? 0 : pid);
        attachment.setTime((int) (System.currentTimeMillis() / 1000));
        attachment.setImageType(1); // 1 for local
        attachment.setType(1); // platform
        attachment.setFileType(1); // image

        this.save(attachment);
        return attachment;
    }
}
