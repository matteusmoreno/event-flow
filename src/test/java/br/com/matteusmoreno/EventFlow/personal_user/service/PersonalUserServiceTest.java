package br.com.matteusmoreno.EventFlow.personal_user.service;

import br.com.matteusmoreno.EventFlow.address.entity.Address;
import br.com.matteusmoreno.EventFlow.personal_user.entity.PersonalUser;
import br.com.matteusmoreno.EventFlow.personal_user.repository.PersonalUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonalUserServiceTest {

    @Mock
    private PersonalUserRepository personalUserRepository;

    @InjectMocks
    private PersonalUserService personalUserService;


    @Test
    @DisplayName("Should get personal user by id")
    void shouldGetPersonalUserById() {
        UUID uuid = UUID.randomUUID();
        Address address = new Address(1L, "28994-666", "Rua A", "Bacaxá", "Saquarema", "RJ");
        PersonalUser personalUser = new PersonalUser(uuid, "Matteus Moreno", LocalDate.of(1990, 8, 28),
                "(22)999999999", address, "matteusmoreno@gmail.com", "123456",
                LocalDateTime.of(2024, 7, 1, 0, 0, 0), null, null, true);

        when(personalUserRepository.findById(uuid)).thenReturn(Optional.of(personalUser));

        PersonalUser result = personalUserService.detailPersonalUserById(uuid);

        verify(personalUserRepository, Mockito.times(1)).findById(uuid);

        assertEquals(uuid, result.getId());
        assertEquals("Matteus Moreno", result.getName());
        assertEquals("(22)999999999", result.getPhone());
        assertEquals(address, result.getAddress());
        assertEquals("matteusmoreno@gmail.com", result.getEmail());
        assertEquals("123456", result.getPassword());
        assertEquals(LocalDate.of(1990, 8, 28), result.getBirthDate());
        assertEquals(LocalDateTime.of(2024, 7, 1, 0, 0, 0), result.getCreatedAt());
        assertNull(result.getUpdatedAt());
        assertNull(result.getDeletedAt());
        assertTrue(result.getActive());
    }
}