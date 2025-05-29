import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GuestTest {
    Guest guest;
    @BeforeEach
    void setUp() {
        guest = new Guest();

    }

    @Test
    @DisplayName( "Test signup with valid details")
    void testSignupWithValidDetails() {
        guest.signup("User@gmail.com", "User", "pass123", "123456789");
        assertEquals(1, User.getUsers().size(),"Should be one user");
        assertEquals("User@gmail.com", User.getUsers().getFirst().getEmail(), "Email should match");
    }

    @Test
    @DisplayName( "Test signup with existing email")
    void testSignupWithExistingEmail() {


        guest.signup("existing@test.com", "Existing User", "password123", "987654321");
        Guest guest2 = new Guest();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                guest2.signup("existing@test.com", "New User", "password456", "012345678"));
        assertEquals("email already exist", exception.getMessage(), "Exception message should match");
    }

    @Test
    @DisplayName( "Test login with valid credentials")
    void testLoginWithValidCredentials() {

        guest.signup("valid@test.com", "Valid User", "password123", "111222333");
        User loggedInUser = Guest.login("valid@test.com", "password123", false);
        assertNotNull(loggedInUser, "User should not be null");
        assertEquals("Valid User", loggedInUser.getName(), "Name should match");
    }


    @Test
    @DisplayName( "Test login with invalid password")
    void testLoginWithInvalidPassword() {

        guest.signup("invalid@test.com", "Invalid User", "correct password", "444555666");
        User loggedInUser = Guest.login("invalid@test.com", "wrong password", false);
        assertNull( loggedInUser,"User should be null");
        loggedInUser = Guest.login("invalid@test.com", "correct password", false);
        assertEquals("correct password",loggedInUser.getPassword(), "Password should match");

    }

    @Test
    @DisplayName( "Test login with nonexistent email")
    void testLoginWithNonExistentEmail() {
        User loggedInUser = Guest.login("nonexistent@test.com", "password123", false);
        assertNull(loggedInUser, "User should be null");
    }

}