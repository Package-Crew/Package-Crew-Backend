package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.config.s3.S3UploadUtil;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3UploadUtil s3UploadUtil;

    public String uploadImage(MultipartFile image)  {
        String imageUrl = "";
        if (image != null && !image.isEmpty()) {
            try {
                imageUrl = s3UploadUtil.upload(image);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.FAIL_IMAGE_UPLOAD);
            }
        }
        return imageUrl;
    }
}
