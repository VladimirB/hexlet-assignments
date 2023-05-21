package exercise.repository;

import exercise.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    List<City> findByNameStartingWithIgnoreCase(String prefix);

    List<City> findByNameIsNotNullOrderByName();
}
