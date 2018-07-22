package aka.springframework.webdevelopmentwithspringmvc.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by AP
 */
public interface ImageService {

    void saveImageFile(Long recipeId, MultipartFile file);
}
