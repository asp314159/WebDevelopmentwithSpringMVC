package aka.springframework.webdevelopmentwithspringmvc.controllers;

import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.exceptions.ControllerExceptionHandler;
import aka.springframework.webdevelopmentwithspringmvc.exceptions.NotFoundException;
import aka.springframework.webdevelopmentwithspringmvc.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.Assert.*;

/**
 * Created by AP
 */
public class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    MockMvc mockMVC;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMVC = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId(1L);

//        MockMvc mockMVC = MockMvcBuilders.standaloneSetup(recipeController).build();

        Mockito.when(recipeService.findById(Mockito.anyLong())).thenReturn(recipe);

        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMVC.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
                .param("directions", "some string")
                .param("prepTime", "1")
                .param("cookTime", "1")
                .param("servings", "1")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));
    }

    @Test
    public void testPostNewRecipeFormValidationFail() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);

        mockMVC.perform(MockMvcRequestBuilders.post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"));
    }

    @Test
    public void testGetUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.findCommandById(Mockito.anyLong())).thenReturn(command);

        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        Mockito.verify(recipeService, Mockito.times(1)).deleteById(Mockito.anyLong());
    }

    @Test
    public void testGetRecipeNotFound() throws Exception{
        Mockito.when(recipeService.findById(Mockito.anyLong())).thenThrow(NotFoundException.class);

        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("404error"));
    }

    @Test
    public void testGetRecipeNumberFormat() throws Exception{
        mockMVC.perform(MockMvcRequestBuilders.get("/recipe/sometext/show"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.view().name("400error"));
    }
}