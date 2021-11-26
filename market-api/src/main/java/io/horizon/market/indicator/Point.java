package io.horizon.market.indicator;

public interface Point extends Comparable<Point> {

	int getIndex();

	long getKeyId();

}
