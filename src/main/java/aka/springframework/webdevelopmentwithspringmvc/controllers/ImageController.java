package aka.springframework.webdevelopmentwithspringmvc.controllers;

import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.services.ImageService;
import aka.springframework.webdevelopmentwithspringmvc.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by AP
 */
@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private RecipeService recipeService;

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if (recipeCommand.getImage() != null) {
            byte[] byteArr = new byte[recipeCommand.getImage().length];

            int i = 0;
            for (Byte wrappedByte: recipeCommand.getImage()) {
                byteArr[i++] = wrappedByte; // auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArr);
            IOUtils.copy(is, response.getOutputStream());
        }
    }


}
