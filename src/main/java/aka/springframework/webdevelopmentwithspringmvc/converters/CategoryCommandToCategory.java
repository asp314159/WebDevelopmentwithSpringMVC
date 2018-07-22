package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.CategoryCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Category;
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
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

    @Autowired
    private GenericMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }
//        DozerBeanMapper mapper = new DozerBeanMapper();

        return mapper.getMapper().map(source, Category.class);
    }
}