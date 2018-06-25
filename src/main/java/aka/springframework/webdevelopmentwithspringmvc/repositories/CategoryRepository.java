package aka.springframework.webdevelopmentwithspringmvc.repositories;

import aka.springframework.webdevelopmentwithspringmvc.domain.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by AP
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
