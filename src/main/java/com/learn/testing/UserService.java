package com.learn.testing;

public interface UserService {

    public String createUser(User user);

    public void updateUser(String userId, User user);

    public User getUser(String userId);

    public int getAllUsersCount();

    public void removeUser(String userId);
}
