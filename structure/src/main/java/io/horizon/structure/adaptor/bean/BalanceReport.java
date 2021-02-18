package io.horizon.structure.adaptor.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class BalanceReport {

	private final int investorId;
	private final long balance;

}
