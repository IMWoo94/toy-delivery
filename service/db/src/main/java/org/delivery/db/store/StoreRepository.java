package org.delivery.db.store;

import java.util.List;
import java.util.Optional;

import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

	// 유효한 스토어
	// select * from store where id = ? and status = ? order by id desc limit 1
	Optional<StoreEntity> findFirstByIdAndStatusOrderByIdDesc(Long id, StoreStatus status);

	// 유효한 스토어 리스트
	// select * from store where status = ? order by id desc
	List<StoreEntity> findAllByStatusOrderByIdDesc(StoreStatus status);

	// 유효한 특정 카테고리의 스토어 리스트
	List<StoreEntity> findAllByStatusAndCategoryOrderByStarDesc(StoreStatus status, StoreCategory category,
		Pageable pageable);
}
