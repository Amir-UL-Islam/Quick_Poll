package org.amir.pollat.entity;

import jdk.jfr.BooleanFlag;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import org.amir.pollat.entity.Roles;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS")
public class Users {
    @Id
//    Without Strategy.IDENTITY, The transaction won't Work
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
//    @NotEmpty
    private String username;

//    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastname;
//    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLE")
//    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER) // Load all the roles when loading the user By Setting the fetch attribute to FetchType.EAGER
//    @JoinTable(
//            name = "USER_ROLES",
//            joinColumns = @JoinColumn(name = "USER_ID"))
    private Collection<Roles> roles = new ArrayList<>();
}
