package com.xenon.data.entity.donor;

import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "donor")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BloodType bloodType;

    @Column(nullable = false, length = 10)
    private Integer age;

    @Column(nullable = false, length = 100)
    private Integer weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Interested interested;


    public Donor(BloodType bloodType, Integer age, Integer weight, Interested interested, User user) {
        this.bloodType = bloodType;
        this.age = age;
        this.weight = weight;
        this.interested = interested;
        this.user = user;
    }

}
