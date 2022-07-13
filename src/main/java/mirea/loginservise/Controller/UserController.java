package mirea.loginservise.Controller;

import lombok.RequiredArgsConstructor;
import mirea.loginservise.Models.User;
import mirea.loginservise.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Вывод всех пользователей
     */
    @RequestMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.allUsers());
    }

    /**
     * Добавление пользователя
     * @param user Пользователь
     */
    @PostMapping("/add")
    public ResponseEntity<User> addUser(User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/user/save").toString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
}

