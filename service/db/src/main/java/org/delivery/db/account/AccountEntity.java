package org.delivery.db.account;

import org.delivery.db.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "account")
@Data
// Equals 와 HashCode 를 사용할 때 부모에 있는 값 까지 포함 한다고 선언
@EqualsAndHashCode(callSuper = true)
// 부모가 가지고 있는 변수도 지정이 가능
@SuperBuilder
public class AccountEntity extends BaseEntity {

}
