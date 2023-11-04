package org.amir.pollat.model.entity;

import lombok.*;

import javax.inject.Inject;
import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER_ROLES")
public class Roles {
    @Id
//    Without Strategy.IDENTITY, The transaction won't Work
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;
    @Column(name = "ROLE_NAME")
    private String name;
}
