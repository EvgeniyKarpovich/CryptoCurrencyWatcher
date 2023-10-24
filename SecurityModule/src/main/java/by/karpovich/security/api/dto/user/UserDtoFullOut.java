package by.karpovich.security.api.dto.user;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoFullOut {

    private String username;

    private String email;

    private byte[] image;

    private Set<String> roles;

    private String userStatus;
}
