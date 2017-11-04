package Controlers;

import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersControlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public UsersControler testObject;

    @Before
    public void setUp() {
        testObject = new UsersControler("Users_test.bin");
        testObject.addNewUser(new User("stefan", "pass"));
        testObject.addNewUser(new User("franek", "assword"));
    }

    @Test
    public void shouldAddNewUser(){
        User user = new User("Franek", "pass");
        assertTrue(testObject.addNewUser(user));
    }

    @Test
    public void shouldNotAddNewUser(){
        User user = new User("franek", "pass");
        assertFalse(testObject.addNewUser(user));
    }

    @Test
    public void userShouldNotBePresentInDataBase() {
        User user = new User("franeksss", "adsfsfds");
        assertFalse(testObject.isUserPresentInDataBase(user));
    }

    @Test
    public void userShouldBePresentInDataBase() {
        User user = new User("franek", "assword");
        assertTrue(testObject.isUserPresentInDataBase(user));
    }

    @Test
    public void userListShouldBeWritten() {
        assertTrue(Files.exists(Paths.get("Users_test.bin")));
    }

    @After
    public void tearDown() {
        try {
            Files.delete(Paths.get("Users_test.bin"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}