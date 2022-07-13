package mirea.loginservise.Schemas;

import lombok.Data;

@Data
public class UserScheme {
    private Long id;
    private String username;
    private String email;
    private String role;
    private String token;
    private String refreshToken;
    private String lastLogin;
    private String lastLogout;
    private boolean active;
}
