package mirea.loginservise.Service;

import mirea.loginservise.Models.Role;
import mirea.loginservise.Models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);

    User getUserByUserEmail(String email);
    List<User> allUsers();
}
