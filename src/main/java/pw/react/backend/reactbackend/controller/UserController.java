package pw.react.backend.reactbackend.controller;

import pw.react.backend.reactbackend.exception.ResourceNotFoundException;
import pw.react.backend.reactbackend.exception.UserExists;
import pw.react.backend.reactbackend.model.User;
import pw.react.backend.reactbackend.repository.UserRepository;
import pw.react.backend.reactbackend.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1")
public class UserController {
  @Autowired
  private UserRepository userRepository;
   @Autowired
  private UserService userService;
  /**
   * Get all users list.
   *
   * @return the list
   */
  @GetMapping("/users")
  public List<User> getAllUsers() {
      
    return userRepository.findAll();
  }
  /**
   * Gets users by id.
   *
   * @param userId the user id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    User user =
    userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
    return ResponseEntity.ok().body(user);
  }
  /**
   * Create user user.
   *
   * @param user the user
   * @return the user
   */
  @PostMapping("/users")
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
   
     if (!(userRepository.findByLogin(user.getlogin())).isEmpty()) {
           throw new UserExists("Login: " + user.getlogin());
       }
     User result = userRepository.save(user);

     return ResponseEntity.ok(result);
        
   
  }

  @PostMapping("/users/login")
  public ResponseEntity<User> createUser(@Valid @RequestBody String l) {
    //l=l.substring(0,l.length()-2);
     
    return userService.findByLogin(l);    
  }
  /**
   * Update user response entity.
   *
   * @param userId the user id
   * @param userDetails the user details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(
      @PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails)
      
      throws ResourceNotFoundException {
      
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

    user.setLastName(userDetails.getLastName());
    user.setFirstName(userDetails.getFirstName());
    user.setDateOfBirth(userDetails.getDateOfBirth());
    user.setIsActive(userDetails.getActive());
    user.setlogin(userDetails.getlogin());
    final User updatedUser = userRepository.save(user);
    return ResponseEntity.ok(updatedUser);
  }
  /**
   * Delete user map.
   *
   * @param userId the user id
   * @return the map
   * @throws Exception the exception
   */

 /* @DeleteMapping("/user/{id}")
  public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable(value = "id") int id) {
      User userToDelete = userRepository.findById(id);
      if (userToDelete == null) {
          throw new ResourceNotFoundException("Id: " + id);
      }

      userRepository.delete(userToDelete);
      Map<String, Boolean> response = new HashMap<>();
      response.put("deleted", Boolean.TRUE);

      return ResponseEntity.ok(response);
  }*/

 @DeleteMapping("/users/{id}")
 public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
  User user =
      userRepository
          .findById(userId)
         .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
  userRepository.delete(user);
  Map<String, Boolean> response = new HashMap<>();
  response.put("deleted", Boolean.TRUE);
  return response;
 }
}