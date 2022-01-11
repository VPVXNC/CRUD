package myapp.dao;

import myapp.model.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    User getUserById(Long id);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(Long id);
}
