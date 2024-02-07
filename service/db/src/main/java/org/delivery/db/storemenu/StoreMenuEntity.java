package org.delivery.db.storemenu;

import java.math.BigDecimal;

import org.delivery.db.BaseEntity;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.storemenu.enums.StoreMenuStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "store_menu")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StoreMenuEntity extends BaseEntity {

	// @Column(nullable = false)
	// private Long storeId;
	@Column(length = 100, nullable = false)
	private String name;
	@Column(precision = 11, scale = 4, nullable = false)
	private BigDecimal amount;
	@Column(length = 50, nullable = false)
	@Enumerated(EnumType.STRING)
	private StoreMenuStatus status;
	@Column(length = 200, nullable = false)
	private String thumbnailUrl;
	private int likeCount;
	private int sequence;
	@ManyToOne
	@JoinColumn(name = "storeId")
	private StoreEntity store;
}
