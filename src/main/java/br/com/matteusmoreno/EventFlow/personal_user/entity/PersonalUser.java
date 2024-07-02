package br.com.matteusmoreno.EventFlow.personal_user.entity;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "personal_users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PersonalUser {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDate birthDate;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String email;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Boolean active;
}
