package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.IngredientCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Ingredient;
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
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    @Autowired
    private GenericMapper mapper;

    private final UnitOfMeasureToUnitOfMeasureCommand uomConverter;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }

//        DozerBeanMapper mapper = new DozerBeanMapper();
        IngredientCommand ingredientCommand = mapper.getMapper().map(source, IngredientCommand.class);
        if (source.getRecipe() != null) {
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        return ingredientCommand;
    }
}