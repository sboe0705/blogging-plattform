package de.sboe0705.users.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Tests the auto-generated REST service of the UserRepository.
 * <p>
 * With MockMvc the web layer is tested without starting the server.
 * <p>
 * To make repositories available in this MockMvc test the whole Spring Boot application has to be loaded
 * and extended by MockMvc functionality. Using @MockMvc would not instantiate the UserRepository.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class UserRestRepositoryTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFindAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("_embedded.users", Matchers.hasSize(4)),
                        // check if HAL self link exists
                        MockMvcResultMatchers.jsonPath("_links.self.href", Matchers.endsWith("/api/users"))
                ).andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testFindUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpectAll(
                        MockMvcResultMatchers.jsonPath("firstName").value("Bruce"),
                        MockMvcResultMatchers.jsonPath("lastName").value("Wayne"),
                        // check if HAL self link exists
                        MockMvcResultMatchers.jsonPath("_links.self.href", Matchers.endsWith("/api/users/1"))
                ).andDo(MockMvcResultHandlers.print());
    }

}
