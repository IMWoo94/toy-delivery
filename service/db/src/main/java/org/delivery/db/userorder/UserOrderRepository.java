package org.delivery.db.userorder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {

	// 특정 유저의 모든 주문
	// select * from user_order where user_id = ? and status = ? order by id desc;
	List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

	// select * from user_order where user_id = ? and status in ( ?, ?, ... ) order by id desc;
	List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> status);

	// 특정 주문
	// select * from user_order where id = ? and status = ? and user_id = ?
	Optional<UserOrderEntity> findAllByIdAndStatusAndUserId(Long id, UserOrderStatus status, Long userId);

	Optional<UserOrderEntity> findAllByIdAndUserId(Long id, Long userId);

	@Query("select u from UserOrderEntity u where u.orderedAt >= :currentDate order by u.id")
	List<UserOrderEntity> findAllByOrderedAtOrderByIdDesc(Pageable pageable, LocalDateTime currentDate);

}
