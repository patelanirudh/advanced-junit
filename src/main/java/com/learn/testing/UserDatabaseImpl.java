package com.learn.testing;

import java.util.HashMap;
import java.util.Map;

public class UserDatabaseImpl implements UserDatabase {

    HashMap<String, User> userDataMap;

    @Override
    public void init() {
        userDataMap = new HashMap<String, User>();
    }

    @Override
    public void close() {
        userDataMap.clear();
        userDataMap = null;
    }

    @Override
    public void saveUser(String userId, User user) {
        System.out.println("Invoking UserDatabase save for userId : " + userId);
        userDataMap.put(userId, user);
    }

    @Override
    public void updateUser(String userId, User user) {
        System.out.println("Invoking UserDatabase update for userId : " + userId);
        userDataMap.put(userId, user);
    }

    @Override
    public User getUser(String userId) {
        return userDataMap.get(userId);
    }

    @Override
    public int getAllUsersCount() {
        return userDataMap.size();
    }

    @Override
    public void deleteUser(String userId) {
        System.out.println("Invoking UserDatabase delete for userId : " + userId);
        userDataMap.remove(userId);
    }
}
