package br.com.matteusmoreno.EventFlow.business_user;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "business_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class BusinessUser {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
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
