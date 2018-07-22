package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.IngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.converters.IngredientToIngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Ingredient;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * Created by AP
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientServiceImplTest {

    @Autowired
    IngredientServiceImpl ingredientService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientToIngredientCommand ingredientToIngredientCommand;

    @Test
    public void findByRecipeIdAndIngredientIdHappyPath() {

        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
    }

    @Test
    public void testSaveRecipeCommand() {
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
    }

    @Test
    public void deleteById() {
        Recipe recipe = new Recipe();
        Ingredient ingredient = new Ingredient();
        ingredient.setId(3L);
        recipe.addIngredient(ingredient);
        ingredient.setRecipe(recipe);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        ingredientService.deleteById(1L, 3L);

    }
}
