package org.amir.pollat.model.entity;


import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class Vote {
    @Id
//    Without Strategy.IDENTITY, The transaction won't Work
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VOTE_ID")
    private Long id;
    @ManyToOne // Option instance can have zero or more Vote instances associated with it.
    @JoinColumn(name = "OPTION_ID")
    private Option option;
}
