package org.amir.pollat.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Vote {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long id;
    @ManyToOne // Option instance can have zero or more Vote instances associated with it.
    @JoinColumn(name = "OPTION_ID")
    private Option option;
}
