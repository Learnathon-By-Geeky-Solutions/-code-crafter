package com.xenon.data.entity.location;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DIVISION")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Division {
    @Id
    private Long id;
    private String name;
}
