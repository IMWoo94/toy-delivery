package org.delivery.db.user;

import java.time.LocalDateTime;

import org.delivery.db.BaseEntity;
import org.delivery.db.user.enums.UserStatus;

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
@Table(name = "user")
@Data
// Equals 와 HashCode 를 사용할 때 부모에 있는 값 까지 포함 한다고 선언
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
// 부모가 가지고 있는 변수도 지정이 가능
@SuperBuilder
public class UserEntity extends BaseEntity {

	@Column(length = 50, nullable = false)
	private String name;
	@Column(length = 100, nullable = false)
	private String email;
	@Column(length = 100, nullable = false)
	private String password;
	@Column(length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	@Column(length = 150, nullable = false)
	private String address;
	private LocalDateTime registeredAt;
	private LocalDateTime unregisteredAt;
	private LocalDateTime lastLoginAt;

}
