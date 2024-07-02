package br.com.matteusmoreno.EventFlow.user.service;

import br.com.matteusmoreno.EventFlow.user.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    /*
    @Test
    @DisplayName("Should get personal user by id")
    void shouldGetPersonalUserById() {
        UUID uuid = UUID.randomUUID();
        Address address = new Address(1L, "28994-666", "Rua A", "Bacaxá", "Saquarema", "RJ");
        User user = new User(uuid, "Matteus Moreno", LocalDate.of(1990, 8, 28),
                "(22)999999999", address, "matteusmoreno@gmail.com", "123456",
                LocalDateTime.of(2024, 7, 1, 0, 0, 0), null, null, true);

        when(personalUserRepository.findById(uuid)).thenReturn(Optional.of(user));

        User result = personalUserService.detailPersonalUserById(uuid);

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
    }*/
}