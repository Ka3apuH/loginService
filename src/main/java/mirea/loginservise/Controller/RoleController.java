package mirea.loginservise.Controller;

import lombok.RequiredArgsConstructor;
import mirea.loginservise.Models.Role;
import mirea.loginservise.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {
    private final UserService userService;

    /**
     * Добавление роли пользователю
     *
     * @param role Роль пользователя
     */
    @RequestMapping("/add")
    public ResponseEntity<Role> addRole(Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/role/save").toString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    /**
     * Присвоение роли пользователю
     *
     * @param role
     * @param username
     * @return
     */
    @PostMapping("/addToUser")
    public ResponseEntity<?> addRoleToUser(String role, String username) {
        userService.addRoleToUser(username, role);
        return ResponseEntity.ok().build();
    }
}

