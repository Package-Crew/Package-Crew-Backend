package com.hufs.bhackathon.api.service;

import com.hufs.bhackathon.api.domain.entity.Item;
import com.hufs.bhackathon.api.domain.entity.Users;
import com.hufs.bhackathon.api.domain.repository.ItemRepository;
import com.hufs.bhackathon.api.domain.repository.UsersRepository;
import com.hufs.bhackathon.api.dto.request.ItemIdRequestDto;
import com.hufs.bhackathon.api.dto.request.ItemRequestDto;
import com.hufs.bhackathon.global.exception.CustomException;
import com.hufs.bhackathon.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UsersRepository usersRepository;
    private final S3Service s3Service;

    @Transactional
    public String postItem(Long userId, MultipartFile image, ItemRequestDto itemRequestDto) {
        Users user = usersRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String imageUrl = s3Service.uploadImage(image);
        itemRepository.save(Item.of(itemRequestDto.getItemName(), imageUrl, user));
        return "상품이 등록되었습니다.";
    }
}
