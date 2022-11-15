package com.learn.testing;

public interface UserDatabase {

    public void init();
    public void close();

    void saveUser(String userId, User user);

    void updateUser(String userId, User user);

    User getUser(String userId);

    int getAllUsersCount();

    void deleteUser(String userId);

}
