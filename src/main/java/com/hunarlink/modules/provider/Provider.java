package com.hunarlink.modules.provider;

import com.hunarlink.modules.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "providers")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @NotBlank(message = "Skill is required")
    @Column(nullable = false)
    private String skill;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @Column
    private String country;

    @Column(length = 13)
    private String cnic;

    @Column
    private Integer yearsOfExperience;

    @NotNull(message = "Hourly rate is required")
    @Column(nullable = false)
    private Double hourlyRate;

    @Column(length = 500)
    private String bio;

    @Column(nullable = false)
    private Boolean isVerified = false;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private Double averageRating = 0.0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}