package com.railway.application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;
//    @Enumerated(EnumType.STRING)
//    private  UserRole userRole=UserRole.ROLE_NORMAL;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
    @ManyToMany(fetch=FetchType.EAGER)
    private List<Role> roles=new ArrayList<>();


}
