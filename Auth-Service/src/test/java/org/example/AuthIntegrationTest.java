package org.example;

import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@EmbeddedKafka(partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
class AuthIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        String body = """
            {
              "username": "TestUser1",
              "email": "register_success@example.com",
              "password": "Haslo123!",
              "firstName": "Jan",
              "lastName": "Kowalski"
            }
            """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldFailToRegisterWhenUsernameAlreadyExists() throws Exception {
        User existing = User.builder()
                .username("TestUser2")
                .email("duplicate_username@example.com")
                .password(passwordEncoder.encode("Haslo123!"))
                .firstName("Existing")
                .lastName("User")
                .role(Role.USER)
                .build();
        userRepository.save(existing);

        String body = """
            {
              "username": "TestUser2",
              "email": "new_email@example.com",
              "password": "InneHaslo123!",
              "firstName": "Jan",
              "lastName": "Nowy"
            }
            """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorField")
                        .value("username"));
    }

    @Test
    void shouldFailToRegisterWhenEmailAlreadyExists() throws Exception {
        User existing = User.builder()
                .username("TestUser3")
                .email("duplicate@example.com")
                .password(passwordEncoder.encode("Haslo123!"))
                .firstName("Existing")
                .lastName("User")
                .role(Role.USER)
                .build();
        userRepository.save(existing);

        String body = """
            {
              "username": "TestUser4",
              "email": "duplicate@example.com",
              "password": "InneHaslo123!",
              "firstName": "Jan",
              "lastName": "Nowy"
            }
            """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errorField")
                        .value("email"));
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        String rawPassword = "Haslo123!";
        User user = User.builder()
                .username("LoginUser") // ważne: username, bo loadUserByUsername używa username
                .email("login_success@example.com")
                .password(passwordEncoder.encode(rawPassword))
                .firstName("Login")
                .lastName("User")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        // Spring Security używa parametru "username" => musi być taki jak User.username
                        .param("username", "LoginUser")
                        .param("password", rawPassword))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json("{\"message\":\"Login successful\"}"));
    }

    @Test
    void shouldFailLoginWithInvalidPassword() throws Exception {
        String rawPassword = "Haslo123!";
        User user = User.builder()
                .username("LoginFail") // też ustaw username dla spójności
                .email("login_fail@example.com")
                .password(passwordEncoder.encode(rawPassword))
                .firstName("Login")
                .lastName("Fail")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "LoginFail")
                        .param("password", "ZleHaslo999!"))
                .andExpect(status().isUnauthorized())
                .andExpect(content()
                        .json("{\"error\":\"Invalid credentials\"}"));
    }

    @Test
    void checkAuthShouldReturn401WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/auth/check"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("{\"error\": \"Unauthorized\"}"));
    }


    @Test
    void checkAuthShouldReturnSameUserAsInSession() throws Exception {
        String registerBody = """
    {
      "username": "check_user",
      "password": "Haslo123!",
      "firstName": "Jan",
      "lastName": "Kowalski",
      "email": "check_user@example.com"
    }
    """;

        // 1. REJESTRACJA
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registerBody))
                .andExpect(status().isCreated());
        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "check_user")
                        .param("password", "Haslo123!"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andReturn();

        Cookie sessionCookie = loginResult.getResponse().getCookie("WISHLISTSESSION");
        Assertions.assertNotNull(sessionCookie);
        mockMvc.perform(get("/auth/check").cookie(sessionCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("check_user"))
                .andExpect(jsonPath("$.email").value("check_user@example.com"))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"));
    }



    @Test
    void logoutShouldInvalidateSessionAndThenCheckReturnsUnauthorized() throws Exception {
        String body = """
        {
          "username": "logout_user",
          "password": "Haslo123!",
          "firstName": "Jan",
          "lastName": "Kowalski",
          "email": "logout_user@example.com"
        }
        """;

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());

        MvcResult loginResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "logout_user")
                        .param("password", "Haslo123!"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andReturn();

        Cookie sessionCookie = loginResult.getResponse().getCookie("WISHLISTSESSION");
        Assertions.assertNotNull(sessionCookie);

        mockMvc.perform(get("/auth/check").cookie(sessionCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("logout_user"));

        mockMvc.perform(post("/auth/logout").cookie(sessionCookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logout successful"));

        mockMvc.perform(get("/auth/check").cookie(sessionCookie))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error").value("Unauthorized"));
    }


    @Test
    void logout_withoutSessionShouldStillReturnOk() throws Exception {
        mockMvc.perform(post("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message")
                        .value("Logout successful"));
    }
}
