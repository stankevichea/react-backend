package pw.react.backend.reactbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import pw.react.backend.reactbackend.exception.UserExists;
import pw.react.backend.reactbackend.model.User;
import org.springframework.stereotype.Service;
import pw.react.backend.reactbackend.repository.UserRepository;

import java.util.List;



@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> findByLogin(String login) {
        List< User> user=userRepository.findByLogin(login);

    if ((user).size()>1 || user.isEmpty()) {
      throw new UserExists("Login: " + login);
  }

    return ResponseEntity.ok().body(user.iterator().next());
    }

    public User findById(int id) {
        return userRepository.findById(id);
    }

   
}