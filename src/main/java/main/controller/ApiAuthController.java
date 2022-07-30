package main.controller;

import main.api.request.LoginRequest;
import main.api.request.RegRequest;
import main.api.response.*;
import main.repositories.UserRepository;
import main.service.ApiAuthService;
import main.service.ApiGeneralService;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {
    private final ApiAuthService apiAuthService;
    private final SettingsResponse settingsResponse;
    private final CaptchaService captchaService;
    private final ApiGeneralService apiGeneralService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Autowired
    public ApiAuthController(ApiAuthService apiAuthService, SettingsResponse settingsResponse, CaptchaService captchaService, ApiGeneralService apiGeneralService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.apiAuthService = apiAuthService;
        this.settingsResponse = settingsResponse;
        this.captchaService = captchaService;
        this.apiGeneralService = apiGeneralService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthCheckResponse>login(@RequestBody LoginRequest loginRequest){
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();

        return ResponseEntity.ok(apiAuthService.getLoginResponse(user.getUsername()));

    }
    @GetMapping("/logout")
    public ResponseEntity<ResultResponse> logout(Principal principal) {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return ResponseEntity.ok(apiAuthService.getLogoutResponse());
    }


    @GetMapping("/check")
    public ResponseEntity<AuthCheckResponse> check(Principal principal){
        if (principal== null){
            return ResponseEntity.ok(new AuthCheckResponse());
        }
        return ResponseEntity.ok(apiAuthService.getLoginResponse(principal.getName()));
    }


    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> captchaCheck() {return ResponseEntity.ok(captchaService.getCaptchaCode());
    }

    @PostMapping("/register")
    public ResponseEntity<RegResponse> register(@RequestBody RegRequest regRequest) {
        if (!apiGeneralService.isMultiUser()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(apiAuthService.getRegResponse(regRequest));
    }

    @PostMapping("/restore")
    public ResponseEntity<ResultResponse> restore(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(apiAuthService.getRestoreResponse(loginRequest));
    }

    @PostMapping("/password")
    public ResponseEntity<RegResponse> restore(@RequestBody RegRequest regRequest) {
        return ResponseEntity.ok(apiAuthService.getPasswordResponse(regRequest));
    }

}
