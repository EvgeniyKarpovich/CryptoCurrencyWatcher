package by.karpovich.security.api.dto.role;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFullDtoOut {

    private Long id;
    private String name;
}