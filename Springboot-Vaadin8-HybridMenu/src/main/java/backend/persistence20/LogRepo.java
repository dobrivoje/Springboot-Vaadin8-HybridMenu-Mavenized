package backend.persistence20;

import ent.Log;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author д06ри, dobri7@gmail.com
 */
public interface LogRepo extends CrudRepository<Integer, Log> {

    List<Log> pronadjiSve();

    @Query("SELECT u FROM Log u WHERE u.idul = :ajdi")
    List<Log> pronadjiPoId(@Param("ajdi") Integer id);

    @Query("SELECT u FROM Log u WHERE u.username = :un ORDER BY u.azudat DESC")
    List<Log> pronadjiPoUsername(@Param("un") String username);
}
