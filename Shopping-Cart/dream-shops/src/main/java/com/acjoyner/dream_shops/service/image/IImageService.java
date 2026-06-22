package com.acjoyner.dream_shops.service.image;

import com.acjoyner.dream_shops.dto.ImageDto;
import com.acjoyner.dream_shops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId) throws IOException;
    void updateImage(MultipartFile file, Long imageId) throws IOException;

}
