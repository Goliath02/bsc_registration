package bsc_registration.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "bsc_member")
@Data
public class BscMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

	@Column
	private String name;

	@Column
	private String email;
}
