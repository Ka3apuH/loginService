package mirea.loginservise.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mirea.loginservise.Models.Role;
import mirea.loginservise.Models.User;
import mirea.loginservise.Repository.RoleRepo;
import mirea.loginservise.Repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    /**
     * Сохраняет пользователя в базе данных
     * @param user пользователь для сохранения
     */
    @Override
    public User saveUser(User user) {
        log.info("Saving user {} to the database", user);
        user.setHashPassword(passwordEncoder.encode(user.getHashPassword()));
        return userRepo.save(user);
    }

    /**
     * Сохраняет роль в базе данных
     * @param role роль для сохранения
     */
    @Override
    public Role saveRole(Role role) {
        log.info("Saving role {} to the database", role);
        return roleRepo.save(role);
    }

    /**
     * Добавляет роль пользователю
     * @param username логин пользователя
     * @param roleName название роли
     */
    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        User user = userRepo.findByEmail(username);
        Role role = roleRepo.findByRoleNane(roleName);
        user.getUserRoles().add(role);
        userRepo.save(user);
    }

    /**
     * Возвращает пользователя по логину (username)
     * @param email логин(имя) пользователя в системе
     * @return пользователь
     */
    @Override
    public User getUserByUserEmail(String email) {
        log.info("Getting user by login {}", email);
        return userRepo.findByEmail(email);
    }

    /**
     * Возвращает всех пользователей из базы данных
     */
    @Override
    public List<User> allUsers() {
        log.info("Getting all users");
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null) {
            log.error("User with login {} not found in the database", email);
            throw new UsernameNotFoundException("User with login " + email + " not found in the database");
        }
        else {
            log.info("User with login {} found in the database", email);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleNane()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getHashPassword(), authorities);
    }
}
