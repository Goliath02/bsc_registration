package bsc_registration.feature.auth.entities;

import bsc_registration.feature.auth.dto.AuthorityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long signUpKeyId;

    @Column
    private String key;

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

}
