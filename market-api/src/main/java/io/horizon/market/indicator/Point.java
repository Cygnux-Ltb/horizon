package io.horizon.market.indicator;

import io.mercury.common.sequence.Serial;

public interface Point extends Serial<Point> {

	int getIndex();

}
