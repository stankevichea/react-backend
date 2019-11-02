package pw.react.backend.reactbackend;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pw.react.backend.reactbackend.controller.UserController;
import pw.react.backend.reactbackend.model.User;

@ActiveProfiles("it")
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMore {

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  // @Autowired
  public MockMvc mockMvc;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  protected WebApplicationContext wac;

  @Autowired
  UserController mangaController;
  @Autowired
  private ObjectMapper objectMapper;

  @Before
  public void setup() throws Exception {
    this.mockMvc = standaloneSetup(this.mangaController).build();// Standalone context
     mockMvc = MockMvcBuilders.webAppContextSetup(wac)
     .build();
  }
  
 /* @After
  public void clean() throws Exception {
      MvcResult result = this.mockMvc.perform(get("/api/v1/users").with(httpBasic("admin", "pass"))).andReturn();
      String content = result.getResponse().getContentAsString();
      List<User> users = objectMapper.readValue(content, new TypeReference<List<User>>() {
      });
      if(!users.isEmpty())
      for (User user : users) {
        this.mockMvc.perform(MockMvcRequestBuilders
        .delete("/api/v1/users/"+user.getId()).with(httpBasic("admin", "pass"))
        .contentType(MediaType.APPLICATION_JSON));
       
      }
  }*/


  @WithMockUser
  @Test
  public void create_new_user_test() throws Exception {
    String json = "{ \"firstName\": \"new111\", \"login\": \"new_player111\", \"lastName\": \"ne11w\"}";
    // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();

    this.mockMvc.perform(
        post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isOk());
    this.mockMvc.perform(post("/api/v1/users/login").with(httpBasic("admin", "pass"))
        .contentType(MediaType.APPLICATION_JSON).content("new_player111"))
        .andExpect(content().json("{'firstName': 'new111'}")).andExpect(status().isOk());

  }
  @WithMockUser
  @Test
  public void update_check() throws Exception {
    
    String json = "{ \"firstName\": \"new\", \"login\": \"new_player1\", \"lastName\": \"ne\"}";
    // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();

    this.mockMvc.perform(
       post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isOk());
    
    String json2 = "{ \"firstName\": \"polo4\", \"login\": \"polo4\", \"lastName\": \"polo4\"}";

    this.mockMvc.perform(
        put("/api/v1/users/1").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json2))
        .andExpect(status().isOk());
  }
  @WithMockUser
  @Test
  public void testDelete() throws Exception {
    String json = "{ \"firstName\": \"wroom\", \"login\": \"aba\", \"lastName\": \"mur\"}";
    // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();

  
    MvcResult result= this.mockMvc.perform(
      post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
      .andExpect(status().isOk()) .andReturn();
      String content = result.getResponse().getContentAsString();
      User user = objectMapper.readValue(content, User.class);
    this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/users/"+user.getId()).with(httpBasic("admin", "pass"))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
}


@WithMockUser
    @Test
    public void check_id() throws Exception {
      String json = "{ \"firstName\": \"lama\", \"login\": \"2lamy\", \"lastName\": \"3lamy\"}";
      // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();
  
    
      this.mockMvc.perform(
        post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isOk());
        this.mockMvc.perform(get("/api/v1/users/1") .with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

   

  // @GetMapping("/users")
  // public List<User> getAllUsers() {
  // return userRepository.findAll();
  // }
  //@WithMockUser
  @Test
  public void getAllusers_test() throws Exception {
    this.mockMvc.perform(get("/api/v1/users").with(httpBasic("admin", "pass"))).andExpect(status().isOk());
  }

  // @GetMapping("/users/{id}")
  // public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long
  // userId)
  // throws ResourceNotFoundException {
  // User user =
  // userRepository
  // .findById(userId)
  // .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " +
  // userId));
  // return ResponseEntity.ok().body(user);
  // }
  //@WithMockUser
  @Test
  public void nie_ma_pass() throws Exception {
    this.mockMvc.perform(get("/api/v1/users/19")).andExpect(status().isUnauthorized());
  }

  
  

  // @PostMapping("/users")
  // public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

  // if (!(userRepository.findByLogin(user.getlogin())).isEmpty()) {
  // throw new UserExists("Login: " + user.getlogin());
  // }
  // User result = userRepository.save(user);

  // return ResponseEntity.ok(result);

  // }
  
  @Test
  public void Chech_users_id_test() throws Exception {
    // MvcResult mvcResult =
    String json = "{ \"firstName\": \"new111\", \"login\": \"new_player111\", \"lastName\": \"ne11w\"}";
    // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();

    this.mockMvc.perform(
        post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isOk());
    this.mockMvc.perform(post("/api/v1/users/login").with(httpBasic("admin", "pass"))
        .contentType(MediaType.APPLICATION_JSON).content("new_player111"))
        .andExpect(content().json("{'firstName': 'new111'}")).andExpect(status().isOk());



    this.mockMvc.perform(get("/api/v1/users/1").with(httpBasic("admin", "pass"))).andExpect(status().isOk())
        .andExpect(content().json("{'firstName': 'new111'}"));
  }

  // @PostMapping("/users/login")
  // public ResponseEntity<User> createUser(@Valid @RequestBody String l) {
  // l=l.substring(0,l.length()-2);

  // return userService.findByLogin(l);
  // }
  @WithMockUser
  @Test
  public void service_login_get_test() throws Exception {
    String json = "{ \"firstName\": \"koko\", \"login\": \"boom1\", \"lastName\": \"lala\"}";
    // byte[] jsonObject = new JsonParser().parse(json).getAsJsonObject();

    this.mockMvc.perform(
        post("/api/v1/users").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isOk());
    


    this.mockMvc
        .perform(post("/api/v1/users/login").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON)
            .content("boom1"))

        .andExpect(status().isOk());

  }

  // @PutMapping("/users/{id}")
  // public ResponseEntity<User> updateUser(
  // @PathVariable(value = "id") Long userId, @Valid @RequestBody User
  // userDetails)

  // throws ResourceNotFoundException {

  // User user =
  // userRepository
  // .findById(userId)
  // .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " +
  // userId));

  // user.setLastName(userDetails.getLastName());
  // user.setFirstName(userDetails.getFirstName());
  // user.setDateOfBirth(userDetails.getDateOfBirth());
  // user.setIsActive(userDetails.getActive());
  ///// user.setlogin(userDetails.getlogin());
  // final User updatedUser = userRepository.save(user);
  // return ResponseEntity.ok(updatedUser);
  // }
  
  @WithMockUser
  @Test
  public void update_check_when_error_returned() throws Exception {
    String json = "{ \"firstName\": \"polo\", \"login\": \"polo\", \"lastName\": \"polo\"}";

    this.mockMvc.perform(
        put("/api/v1/users/999").with(httpBasic("admin", "pass")).contentType(MediaType.APPLICATION_JSON).content(json))
        .andExpect(status().isNotFound());
  }

  // @DeleteMapping("/user/{id}")
  // public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long
  // userId) throws Exception {
  // User user =
  // userRepository
  // .findById(userId)
  // .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " +
  // userId));
  // userRepository.delete(user);
  // Map<String, Boolean> response = new HashMap<>();
  // response.put("deleted", Boolean.TRUE);
  // return response;
  // }

  // given
 





}
    
