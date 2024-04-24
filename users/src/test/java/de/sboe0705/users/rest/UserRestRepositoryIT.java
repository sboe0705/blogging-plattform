package de.sboe0705.users.rest;

import de.sboe0705.users.persistence.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * Tests the auto-generated REST service of the UserRepository.
 * <p>
 * In this integration test the server is started as well and the web layer is tested with a real HTTP request.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestRepositoryIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFindAllUsers() throws Exception {
        Assertions.assertThat(
                        restTemplate.getForObject(String.format("http://localhost:%s/api/users/1", port), User.class)
                ).extracting(User::getFirstName, User::getLastName)
                .containsExactly("Bruce", "Wayne");
    }

}
