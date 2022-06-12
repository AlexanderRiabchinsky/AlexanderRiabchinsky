package main.service;

import main.api.response.AuthCheckResponse;
import main.api.response.UserAuthCheck;
import main.model.Users;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApiAuthCheckService {
    @Autowired
    private UsersRepository usersRepository;

    public AuthCheckResponse getAuthCheckResponse() {
        AuthCheckResponse authCheckResponse = new AuthCheckResponse();
        Integer id = 1;

        Optional<Users> userAuthCheck = usersRepository.findById(id);
        authCheckResponse.setResult(true);
        AuthCheckResponse user = new AuthCheckResponse();
        UserAuthCheck users = new UserAuthCheck();
        users.setId(id);
        users.setName(usersRepository.findNameById(id));
        users.setPhoto(usersRepository.findPhotoById(id));
        users.setEmail(usersRepository.findEmailById(id));
        users.setModeration(true);
        users.setModerationCount(88);
        users.setSettings(true);
        authCheckResponse.setUser(users);
        return authCheckResponse;
    }
}
