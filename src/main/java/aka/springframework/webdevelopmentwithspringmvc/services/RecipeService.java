package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;

import java.util.Set;

/**
 * Created by AP
 */
public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(long l);

    RecipeCommand findCommandById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    void deleteById(Long idToDelete);
}
