package org.amir.pollat.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Option {
    @Id
//    Without Strategy.IDENTITY, The transaction won't Work
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Long id;

    @Column(name = "OPTION_VALUE")
    private String value;

}
