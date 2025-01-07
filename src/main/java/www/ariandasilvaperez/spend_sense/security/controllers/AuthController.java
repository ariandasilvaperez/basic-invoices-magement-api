package www.ariandasilvaperez.spend_sense.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import www.ariandasilvaperez.spend_sense.security.models.dto.TokenDTO;
import www.ariandasilvaperez.spend_sense.security.models.dto.UserDTO;
import www.ariandasilvaperez.spend_sense.security.services.IAuthServices;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    IAuthServices authService;

    @PostMapping("/register")
    private ResponseEntity<UserDTO> register(@RequestBody UserDTO user) throws Exception {
        return new ResponseEntity<>(authService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<TokenDTO> login(@RequestBody UserDTO loginRequest) throws Exception {
        TokenDTO token = authService.login(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }
}
