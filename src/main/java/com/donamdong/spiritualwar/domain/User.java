package com.donamdong.spiritualwar.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String userId;

    @Column
    private String userPassword;

    @Column
    private String userName;

    @Column
    private Boolean managerYn;

}
