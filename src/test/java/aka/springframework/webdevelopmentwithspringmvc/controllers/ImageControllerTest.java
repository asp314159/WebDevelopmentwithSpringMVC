package aka.springframework.webdevelopmentwithspringmvc.controllers;

import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.services.ImageService;
import aka.springframework.webdevelopmentwithspringmvc.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

/**
 * Created by AP
 */
public class ImageControllerTest {

    @InjectMocks
    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    MockMvc mockMVC;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMVC = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void getImageForm() throws Exception {

        RecipeCommand command = new RecipeCommand();
        command.setId(1l);

        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(Mockito.anyLong());
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Images with Spring Framework".getBytes());

        mockMVC.perform(multipart("/recipe/1/image").file(file))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/1/show"));

        Mockito.verify(imageService, Mockito.times(1)).saveImageFile(Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        String s = "Fake Image Text";
        Byte[] bytesArr = new Byte[s.getBytes().length];

        int i = 0;
        for (byte primeByte : s.getBytes()) {
            bytesArr[i++] = primeByte;
        }

        command.setImage(bytesArr);

        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

        MockHttpServletResponse response = mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/recipeimage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(s.getBytes().length, responseBytes.length);
    }
}