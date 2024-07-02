package br.com.matteusmoreno.EventFlow.address.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zipcode;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
}
