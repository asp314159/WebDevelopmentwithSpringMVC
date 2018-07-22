package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.bootstrap.GenericMapper;
import aka.springframework.webdevelopmentwithspringmvc.commands.NotesCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.Notes;
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
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Autowired
    private GenericMapper mapper;

    @Synchronized
    @Nullable
    @Override
    public NotesCommand convert(Notes source) {
        if (source == null) {
            return null;
        }

//        DozerBeanMapper mapper = new DozerBeanMapper();
        return mapper.getMapper().map(source, NotesCommand.class);
    }
}