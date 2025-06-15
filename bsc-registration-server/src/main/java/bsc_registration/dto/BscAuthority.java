package bsc_registration.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BscAuthority implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long authorityId;

	@Column(nullable = false, unique = true)
	private String authority;

	@Override
	public String getAuthority() {
		return authority;
	}
}