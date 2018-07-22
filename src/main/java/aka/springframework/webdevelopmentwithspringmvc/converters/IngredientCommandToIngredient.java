package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.IngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Ingredient;
import aka.springframework.webdevelopmentwithspringmvc.domain.Recipe;
import aka.springframework.webdevelopmentwithspringmvc.domain.UnitOfMeasure;
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
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>{

    @Autowired
    private GenericMapper mapper;

    @Autowired
    private UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }
//        DozerBeanMapper mapper = new DozerBeanMapper();

        Ingredient ingredient = mapper.getMapper().map(source, Ingredient.class);
        if (source.getRecipeId() != null) {
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }
//        ingredient.setUom(uomConverter.convert(source.getUom()));
        return ingredient;
    }
}