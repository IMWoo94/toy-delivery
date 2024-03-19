package org.delivery.batch.settle.days;

import org.delivery.db.settle.SettleDaysEntity;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;
import org.delivery.db.userordermenu.UserOrderMenuRepository;
import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@StepScope
public class SettleDaysItemProcessor implements ItemProcessor<UserOrderEntity, SettleDaysEntity> {

	private final UserOrderMenuRepository userOrderMenuRepository;

	public SettleDaysItemProcessor(UserOrderMenuRepository userOrderMenuRepository) {
		this.userOrderMenuRepository = userOrderMenuRepository;
	}

	@Override
	public SettleDaysEntity process(UserOrderEntity item) throws Exception {
		// item [ UserOrder ] 를 통해서 메뉴 갯수를 가져온다.\
		var total = userOrderMenuRepository.findAllByUserOrderIdAndStatusGroupByOrderId(
			item.getId(), UserOrderMenuStatus.REGISTERED);

		var menus = userOrderMenuRepository.findAllByUserOrderIdAndStatus(
			item.getId(), UserOrderMenuStatus.REGISTERED);
		var storeMenuId = menus.stream().map(UserOrderMenuEntity::getStoreMenuId).findFirst().orElseThrow();

		return SettleDaysEntity.builder()
			.storeId(item.getStoreId())
			.storeMenuId(storeMenuId)
			.amount(item.getAmount())
			.totalCount(total)
			.orderedAt(item.getOrderedAt())
			.build();
	}
}
