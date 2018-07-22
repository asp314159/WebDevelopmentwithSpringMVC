package aka.springframework.webdevelopmentwithspringmvc.bootstrap;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

/**
 * Created by AP
 */
@Component
public class GenericMapper {

    final DozerBeanMapper mapper = new DozerBeanMapper();

    public DozerBeanMapper getMapper() {
        return mapper;
    }
}
