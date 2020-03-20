package pl.bajerska.befindyourmeal.user;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType type;

}
