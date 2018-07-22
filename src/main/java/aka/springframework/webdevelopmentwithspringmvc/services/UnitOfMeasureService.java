package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.UnitOfMeasureCommand;

import java.util.Set;

/**
 * Created by AP
 */
public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();

}
