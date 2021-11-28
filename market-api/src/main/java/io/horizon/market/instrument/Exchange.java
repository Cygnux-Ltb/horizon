package io.horizon.market.instrument;

import java.time.ZoneOffset;

public interface Exchange {

	int getExchangeId();

	String getExchangeCode();

	String getFullName();

	ZoneOffset getZoneOffset();

	int getSymbolId(int serialInExchange);

}
