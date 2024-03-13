package org.delivery.db.settle;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.delivery.db.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "settleDays")
public class SettleDaysEntity extends BaseEntity {

	@Column(nullable = false)
	private Long storeId;
	@Column(nullable = false)
	private Long storeMenuId;
	@Column(precision = 11, scale = 4, nullable = false)
	private BigDecimal amount;
	@Column(nullable = false)
	private int totalCount;
	@Column(nullable = false)
	private LocalDateTime orderedAt;
}
