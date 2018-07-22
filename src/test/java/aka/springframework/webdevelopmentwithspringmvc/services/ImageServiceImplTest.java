package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by AP
 */
public class ImageServiceImplTest {

    @InjectMocks
    ImageServiceImpl imageService;

    @Mock
    RecipeRepository recipeRepository;

    MockMvc mockMVC;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMVC = MockMvcBuilders.standaloneSetup(imageService).build();
    }

    @Test
    public void saveImageFile() throws Exception {
        Long id = 1L;
        MockMultipartFile file = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Images with Spring Framework".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        imageService.saveImageFile(id, file);

        Mockito.verify(recipeRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }
}