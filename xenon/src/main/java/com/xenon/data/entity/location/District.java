package com.xenon.data.entity.location;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DISTRICT")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class District {
    @Id
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DIVISION_ID")
    private Division division;
}
