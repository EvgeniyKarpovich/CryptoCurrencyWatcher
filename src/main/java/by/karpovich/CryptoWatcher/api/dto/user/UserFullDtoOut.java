package by.karpovich.CryptoWatcher.api.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserFullDtoOut {

    private String username;

    private String email;

    private byte[] image;

    private Set<String> roles;

    private String userStatus;
}
