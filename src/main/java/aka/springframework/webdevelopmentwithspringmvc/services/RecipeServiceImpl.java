package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.converters.RecipeCommandToRecipe;
import aka.springframework.webdevelopmentwithspringmvc.converters.RecipeToRecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.exceptions.NotFoundException;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by AP
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the Service");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);
        return recipeOptional.orElseGet(() -> {throw new NotFoundException("Recipe Not Found. For ID: " + l);});
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
        Recipe saveRecipe = recipeRepository.save(detachedRecipe);
        return recipeToRecipeCommand.convert(saveRecipe);
    }

    @Override
    public void deleteById(Long idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

}
