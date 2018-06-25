package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;

import java.util.Set;

/**
 * Created by AP
 */
public interface RecipeService {

    Set<Recipe> getRecipes();
}
