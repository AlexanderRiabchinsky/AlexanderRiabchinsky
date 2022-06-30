package main.service;

import main.model.Users;
import main.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private UsersRepository usersRepository;
    public int setNewUser(){
        Users newUser = new Users();
        newUser.setIsModerator(newUser.getIsModerator());
        newUser.setRegTime(newUser.getRegTime());
        newUser.setName(newUser.getName());
        newUser.setEmail(newUser.getEmail());
        newUser.setPassword(newUser.getPassword());
        newUser.setCode(newUser.getCode());
        newUser.setPhoto(newUser.getPhoto());
        usersRepository.save(newUser);
        return newUser.getId();
    }
}
