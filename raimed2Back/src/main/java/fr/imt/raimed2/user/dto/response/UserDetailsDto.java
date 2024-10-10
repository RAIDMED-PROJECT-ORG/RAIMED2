package fr.imt.raimed2.user.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto {

    private UUID id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String role;


}
