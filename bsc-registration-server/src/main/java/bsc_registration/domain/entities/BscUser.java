package bsc_registration.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "bsc_user")
@Data
public class BscUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column
	private String userName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_authorities",
			joinColumns = @JoinColumn(name = "userId"),
			inverseJoinColumns = @JoinColumn(name = "authority_id")
	)
	private List<BscAuthority> authorities;

	@ManyToMany(fetch = FetchType.LAZY,  cascade = CascadeType.DETACH)
	@JoinTable(
			joinColumns = @JoinColumn(name = "courseOwnerId"),
			inverseJoinColumns = @JoinColumn(name = "courseId"))
	private List<Course> courses;

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "sign_up_key_id", referencedColumnName = "signUpKeyId")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private SignUpKey signUpKey;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

  public String getFullName() {
    return userName;
  }
}
