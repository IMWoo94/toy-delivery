package org.delivery.db.userordermenu;

import java.util.List;

import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

	List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderMenuStatus status);

	@Query("select count(*) from UserOrderMenuEntity u where u.userOrderId = :userOrderId and u.status = :status group by u.userOrderId")
	int findAllByUserOrderIdAndStatusGroupByOrderId(Long userOrderId, UserOrderMenuStatus status);
}
