package io.horizon.market.instrument;

import java.time.ZoneOffset;

public interface Exchange {

	int getExchangeId();

	String getFullName();

	ZoneOffset getZoneOffset();

	String getCode();

	int genSymbolId(Symbol symbol);

}
