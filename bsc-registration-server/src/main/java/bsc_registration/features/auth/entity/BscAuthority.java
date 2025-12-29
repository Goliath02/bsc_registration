package bsc_registration.features.auth.entity;

import bsc_registration.webInterface.dto.AuthorityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bsc_authority")
public class BscAuthority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorityId;

	@Column(nullable = false, unique = true)
	@Enumerated(EnumType.STRING)
	private AuthorityType authority;

	@Override
	public String getAuthority() {
		return authority.name();
	}

	@Override
	public String toString() {
		return authority.name();
	}
}