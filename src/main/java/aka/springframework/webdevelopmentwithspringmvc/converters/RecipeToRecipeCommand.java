package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Category;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import lombok.Synchronized;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by AP
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    @Autowired
    private CategoryToCategoryCommand categoryConveter;

    @Autowired
    private IngredientToIngredientCommand ingredientConverter;

    @Autowired
    private NotesToNotesCommand notesConverter;

    @Autowired
    private GenericMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }

//        DozerBeanMapper mapper = new DozerBeanMapper();
        RecipeCommand recipeCommand = mapper.getMapper().map(source, RecipeCommand.class);
        /*if (source.getNotes() != null) {
            recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
        }*/

        /*if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories()
                    .forEach(category -> recipeCommand.getCategories().add(categoryConveter.convert(category)));
        }*/

        /*if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients()
                    .forEach(ingredient -> recipeCommand.getIngredients().add(ingredientConverter.convert(ingredient)));
        }*/

        return recipeCommand;
    }
}