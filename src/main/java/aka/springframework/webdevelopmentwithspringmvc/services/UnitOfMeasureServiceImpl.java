package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.UnitOfMeasureCommand;
import aka.springframework.webdevelopmentwithspringmvc.converters.UnitOfMeasureToUnitOfMeasureCommand;
import aka.springframework.webdevelopmentwithspringmvc.repositories.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AP
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
