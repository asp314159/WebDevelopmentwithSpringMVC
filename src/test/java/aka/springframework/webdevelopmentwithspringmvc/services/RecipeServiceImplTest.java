package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.exceptions.NotFoundException;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by AP
 */
public class RecipeServiceImplTest {

    @InjectMocks
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);

        Mockito.when(recipeRepository.findAll()).thenReturn(recipeSet);

        assertEquals(1, recipeService.getRecipes().size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void findById() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);
        assertNotNull("Null recipe returned", recipeService.findById(1L));
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(recipeRepository, Mockito.never()).findAll();
    }

    @Test
    public void testDeleteById() {
        recipeService.deleteById(2L);
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void findByIdTestNotFound() {
        Optional<Recipe> recipeOptional = Optional.empty();

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(recipeOptional);

        Recipe recipe = recipeService.findById(1L);
    }
}