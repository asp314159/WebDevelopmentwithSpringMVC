package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.IngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.converters.IngredientCommandToIngredient;
import aka.springframework.webdevelopmentwithspringmvc.converters.IngredientToIngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Ingredient;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.repositories.RecipeRepository;
import aka.springframework.webdevelopmentwithspringmvc.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by AP
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @Autowired
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.orElseGet(() -> {
            throw new RuntimeException("Recipe Not Found");
        });

        /*Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();*/

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientCommand::convert).findFirst();

        return ingredientCommandOptional.orElseGet(() -> {throw new RuntimeException("Ingredient Not Found");});
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        Recipe recipe = recipeOptional.orElseGet(() -> {throw new RuntimeException("Recipe Not Found for id: " + command.getRecipeId());});

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();

        if(ingredientOptional.isPresent()){
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(command.getDescription());
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
        } else {
            //add new Ingredient
            Ingredient ingredient = ingredientCommandToIngredient.convert(command);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);

        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                .findFirst();

        //check by description
        if(!savedIngredientOptional.isPresent()){
            //not totally safe... But best guess
            savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                    .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                    .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOptional.orElseGet(() -> {
            throw new RuntimeException("Recipe Not Found");
        });

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        if(ingredientOptional.isPresent()) {
            Ingredient ingredientToDelete = ingredientOptional.get();
            ingredientToDelete.setRecipe(null);
            recipe.getIngredients().remove(ingredientOptional.get());
            recipeRepository.save(recipe);
        } else {
            log.debug("Recipe Id not found. Id:" + recipeId);
        }
    }


}
