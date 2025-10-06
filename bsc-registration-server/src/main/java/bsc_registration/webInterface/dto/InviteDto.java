package bsc_registration.webInterface.dto;

import lombok.Data;

@Data
public class InviteDto {

    private String username;

    private String email;

    private AuthorityType type;

}
