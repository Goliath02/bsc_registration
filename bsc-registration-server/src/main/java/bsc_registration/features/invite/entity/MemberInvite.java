package bsc_registration.features.invite.entity;

import bsc_registration.webInterface.dto.AuthorityType;
import jakarta.persistence.*;

@Entity
@Table(name = "member_invite")
public class MemberInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long inviteId;

    @Column
    private String email;

    @Column
    private Boolean used;

    @Column
    private AuthorityType type;

}
