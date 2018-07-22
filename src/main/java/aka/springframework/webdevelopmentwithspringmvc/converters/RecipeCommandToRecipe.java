package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.RecipeCommand;
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
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    @Autowired
    private CategoryCommandToCategory categoryConveter;

    @Autowired
    private IngredientCommandToIngredient ingredientConverter;

    @Autowired
    private NotesCommandToNotes notesConverter;

    @Autowired
    private GenericMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

//        DozerBeanMapper mapper = new DozerBeanMapper();
        Recipe recipe = mapper.getMapper().map(source, Recipe.class);
       /* if (source.getNotes() != null) {
            recipe.setNotes(notesConverter.convert(source.getNotes()));
        }*/

        /*if (source.getCategories() != null && source.getCategories().size() > 0){
            source.getCategories().forEach( category -> recipe.getCategories().add(categoryConveter.convert(category)));
        }

        if (source.getIngredients() != null && source.getIngredients().size() > 0){
            source.getIngredients().forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
        }*/

        return recipe;
    }
}