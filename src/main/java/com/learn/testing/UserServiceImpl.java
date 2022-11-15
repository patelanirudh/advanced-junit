package com.learn.testing;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UserDatabase userDB;

    public UserServiceImpl(UserDatabase userDatabase) {
        this.userDB = userDatabase;
    }

    @Override
    public String createUser(User user) {
        String userId = UUID.randomUUID().toString();
        System.out.println("Invoking UserServiceImpl createUser for userId : " + userId);
        userDB.saveUser(userId, user);
        return userId;
    }

    @Override
    public void updateUser(String userId, User user) {
        if (null == userId || userId.isEmpty()) {
            throw new IllegalArgumentException("userId cannot be null or empty!!!");
        }

        System.out.println("Invoking UserServiceImpl updateUser for userId : " + userId);
        if (null == userDB.getUser(userId)) {
            throw new IllegalArgumentException("User does not exist for userId : " + userId);
        }
        userDB.updateUser(userId, user);
    }

    @Override
    public User getUser(String userId) {
        return userDB.getUser(userId);
    }

    @Override
    public int getAllUsersCount() {
        return userDB.getAllUsersCount();
    }

    @Override
    public void removeUser(String userId) {
        System.out.println("Invoking UserServiceImpl deleteUser for userId : " + userId);
        userDB.deleteUser(userId);
    }
}
