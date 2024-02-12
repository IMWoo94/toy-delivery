package org.delivery.db.storeuser;

import java.time.LocalDateTime;

import org.delivery.db.BaseEntity;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "store_user")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreUserEntity extends BaseEntity {

	@Column(nullable = false)
	private Long storeId;
	@Column(length = 100, nullable = false)
	private String email;
	@Column(length = 100, nullable = false)
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StoreUserStatus status;
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StoreUserRole role;
	private LocalDateTime registeredAt;
	private LocalDateTime unregisteredAt;
	private LocalDateTime lastLoginAt;

}
