package com.learn.testing;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("UserService Testing Methods")
class UserServiceImplTest {

    UserDatabase userDatabse;
    UserService userService;

    // This state is sustained b/w test method executions. Since, only one TestClass instance is created as above.
    private String createdUserId;

    // This method is not static because we are using TestInstance.Lifecycle.PER_CLASS. Else, this would be static
    @BeforeAll
    void setup() {
        System.out.println("Executing @BeforeAll setup()");
        userDatabse = new UserDatabaseImpl();
        userDatabse.init();
        userService = new UserServiceImpl(userDatabse);
    }

    @AfterAll
    void cleanup() {
        System.out.println("Executing @AfterAll cleanup()");
        userDatabse.close();
        createdUserId = null;
    }

    @Order(1)
    @DisplayName("CreateUser")
    @Test
    void testCreateUser_2UsersCreated_ShouldBeSavedToDB() {
        System.out.println("Executing @Test CreateUser");

        // Arrange/Given
        User user1 = new User("Anirudh Patel", 35);
        int expectedUserCount = 1;
        String expectedUserName = "Anirudh Patel";

        // Act/When
        createdUserId = userService.createUser(user1);

        // Assert/Then
        int actualUsersCount = userService.getAllUsersCount();
        System.out.println("ActualUsersCount : " + actualUsersCount);

        assertNotNull(createdUserId, "createdUserId should not be null");
        assertEquals(expectedUserName, userService.getUser(createdUserId).getName(), "User's name should be matching");
        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");
    }

    @Order(2)
    @DisplayName("GetUserDetails")
    @Test
    void testGetUser_SupplyUserIdState_ShouldReturnCorrectUser() {
        // Arrange
        System.out.println("Executing @Test GetUserDetails");

        // Act
        User returnedUserDetails = userService.getUser(createdUserId);

        // Assert
        assertNotNull(returnedUserDetails, () -> "Returned UserDetails should not be null for createdUserId : " + createdUserId);
    }


    @Order(3)
    @DisplayName("UpdateUser")
    @Test
    void testUpdateUser_ExistingUsersUpdated_ShouldBeUpdatedToDB() {
        // Arrange
        System.out.println("Executing @Test UpdateUser");
        User updateUserDetails = userService.getUser(createdUserId);
        int expectedUserCount = 1, expectedUserAge = 40;

        // Act
        updateUserDetails.setAge(40);
        userService.updateUser(createdUserId, updateUserDetails);

        // Assert
        int actualUsersCount = userService.getAllUsersCount();
        int updatedUserAge = userService.getUser(createdUserId).getAge();
        System.out.println("UpdatedUserAge : " + updatedUserAge);

        assertEquals(expectedUserCount, actualUsersCount, "Mismatch between expected and actual users count");
        assertEquals(expectedUserAge, updatedUserAge, "Mismatch between expected and updatedUserAge");
    }

    @Order(4)
    @DisplayName("UpdateUserWithEmptyUserId")
    @Test
    void testUpdateUser_PassEmptyUserId_ShouldThrowException() {
        // Arrange
        System.out.println("Executing @Test UpdateUserWithEmptyUserId");
        User updateUserDetails = userService.getUser(createdUserId);
        String expectedExceptionMessage = "userId cannot be null or empty!!!";
        updateUserDetails.setAge(40);

        // Assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser("", updateUserDetails);
        });

        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(), "Exception message should have matched");
    }

    @Order(5)
    @DisplayName("UpdateUserNotFound")
    @Test
    void testUpdateUser_PassIncorrectUserId_ShouldThrowException() {
        // Arrange
        System.out.println("Executing @Test UpdateUserNotFound");
        User updateUserDetails = userService.getUser(createdUserId);
        updateUserDetails.setAge(40);
        String incorrectUserId = "1234";
        String expectedExceptionMessage = "User does not exist for userId : " + incorrectUserId;

        // Assert
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(incorrectUserId, updateUserDetails);
        });

        assertEquals(expectedExceptionMessage, illegalArgumentException.getMessage(), "Exception message should have matched");
    }

    @Order(6)
    @DisplayName("RemoveUser")
    @Test
    void testRemoveUser_UsersRemoved_ShouldBeDeletedFromDB() {
        System.out.println("Executing @Test RemoveUser");

        // Act
        userService.removeUser(createdUserId);

        // Assert
        assertTrue(userService.getAllUsersCount() == 0,"UserService should not have any users in database");
    }
}