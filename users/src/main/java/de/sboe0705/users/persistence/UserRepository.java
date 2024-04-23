package de.sboe0705.users.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// available as REST service under http://localhost:8080/api/users
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
