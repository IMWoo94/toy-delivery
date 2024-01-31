package org.delivery.db.user;

import java.util.Optional;

import org.delivery.db.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	// select * from user where id = ? and status = ? order by id desc limit 1
	Optional<UserEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, UserStatus status);

	// select * from user where email = ? and password = ? and status = ? order by id desc limit 1;
	Optional<UserEntity> findFirstByEmailAndPasswordAndStatusOrderByIdDesc(String email, String password,
		UserStatus status);

	// select * from user where email = ? and status = ? limit 1;
	Optional<UserEntity> findFirstByEmailAndStatus(String email, UserStatus status);

	// select * from user where name = ? and address = ? and status = ? order by id desc limit 1;
	Optional<UserEntity> findFirstByNameAndAddressAndStatusOrderByIdDesc(String name, String address,
		UserStatus status);

	// select * from user where email = ? and name = ? and address = ? and status = ? order by id desc limit 1;
	Optional<UserEntity> findFirstByEmailAndNameAndAddressAndStatusOrderByIdDesc(String email, String name,
		String address,
		UserStatus status);

	@Query("select u from UserEntity u where (u.email = :email or ( u.name = :name and u.address = :address)) and u.status = :status order by u.id limit 1")
	Optional<UserEntity> duplicationJoin(
		@Param("email") String email,
		@Param("name") String name,
		@Param("address") String address,
		@Param("status") UserStatus status);
}
