package de.sboe0705.users.persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void testFindAll() {
        Assertions.assertThat(underTest.findAll())
                .hasSize(4)
                .extracting(User::getFirstName, User::getLastName)
                .containsExactlyInAnyOrder(
                        Tuple.tuple("Bruce", "Wayne"),
                        Tuple.tuple("Peter", "Parker"),
                        Tuple.tuple("Clark", "Kent"),
                        Tuple.tuple("Tony", "Stark")
                );
    }

    @Test
    void testSave() {
        long initialCount = underTest.count();

        User user = new User();
        user.setFirstName("Barry");
        user.setLastName("Allen");
        underTest.save(user);

        Assertions.assertThat(underTest.count()).isEqualTo(initialCount + 1);
    }

}
