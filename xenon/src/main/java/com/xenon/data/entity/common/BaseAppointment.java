package com.xenon.data.entity.common;

import com.xenon.data.entity.hospital.AppointmentStatus;
import com.xenon.data.entity.user.Gender;
import com.xenon.data.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Base class for all appointment entities
 */
@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
public abstract class BaseAppointment extends AuditableEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus appointmentStatus;
    
    @Column(nullable = false)
    private Boolean isBeneficiary;
    
    private String beneficiaryName;
    
    private String beneficiaryPhone;
    
    private String beneficiaryAddress;
    
    @Enumerated(EnumType.STRING)
    private Gender beneficiaryGender;
    
    private Integer beneficiaryAge;
    
    private String medicalHistoryFile;
    
    public BaseAppointment(User user) {
        this.user = user;
        this.appointmentStatus = AppointmentStatus.PENDING;
    }
}