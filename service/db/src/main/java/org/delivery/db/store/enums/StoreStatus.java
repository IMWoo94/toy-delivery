package org.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {

	WAIT("대기"),
	REGISTERED("등록"),
	UNREGISTERED_APPLY("해지 신청"),
	UNREGISTERED("해지");

	private final String description;
}
