package org.delivery.db.store;

import java.math.BigDecimal;

import org.delivery.db.BaseEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;

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
@Table(name = "store")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreEntity extends BaseEntity {

	@Column(length = 100, nullable = false)
	private String name;
	@Column(length = 150, nullable = false)
	private String address;
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StoreStatus status;
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StoreCategory category;

	private double star;
	@Column(length = 200, nullable = false)
	private String thumbnail_url;
	@Column(precision = 11, scale = 4, nullable = false)
	private BigDecimal minimumAmount;
	@Column(precision = 11, scale = 4, nullable = false)
	private BigDecimal minimumDeliveryAmount;
	@Column(length = 20)
	private String phoneNumber;
}
