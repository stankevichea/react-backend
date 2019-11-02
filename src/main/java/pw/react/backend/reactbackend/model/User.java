
package pw.react.backend.reactbackend.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "login", nullable = false)
    private String login;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "date_of_birth")
    private  Date birth;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "active")
    private boolean activeu;
  
   
  /**
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
        return id;
    }
  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
        this.id = id;
    }
    


 /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getlogin() {
    return login;
}
/**
* Sets first name.
*
* @param firstName the first name
*/
public void setlogin(String loginu) {
    this.login = loginu;
}

public Date getDateOfBirth() {
  return birth;
}

public void setDateOfBirth(Date birth) {
  this.birth = birth;
}



    
  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
        return firstName;
    }
  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
        return lastName;
    }
  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public boolean getActive() {
      return activeu;
  }

  public void setIsActive(boolean activeu) {
      this.activeu = activeu;
  }

}