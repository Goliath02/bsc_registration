package bsc_registration.features.auth.entity;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "authority_id")
	private BscAuthority authority;

    @OneToOne(mappedBy = "signUpKey", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private BscUser user;

}
