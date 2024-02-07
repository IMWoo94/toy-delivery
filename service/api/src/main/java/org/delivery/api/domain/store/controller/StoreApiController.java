package org.delivery.api.domain.store.controller;

import java.util.List;

import org.delivery.api.common.api.Api;
import org.delivery.api.domain.store.business.StoreBusiness;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.enums.StoreCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreApiController {

	private final StoreBusiness storeBusiness;

	@GetMapping("/search")
	public Api<List<StoreResponse>> search(
		@RequestParam(required = false)
		StoreCategory storeCategory,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		var response = storeBusiness.searchCategory(storeCategory, pageable);
		return Api.OK(response);
	}
}
