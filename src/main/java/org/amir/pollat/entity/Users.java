package org.amir.pollat.entity;

import jdk.jfr.BooleanFlag;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS")
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
    @NotEmpty
    private String username;

    @NotEmpty
    @Column(name = "LAST_NAME")
    private String lastname;
    @NotEmpty
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLL")
    @Enumerated(EnumType.STRING)
    private Rolls roll;

    @BooleanFlag
    private boolean isActive;

}
