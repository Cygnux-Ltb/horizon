package io.horizon.indicator;

import io.mercury.common.sequence.Serial;

public interface Point<S extends Serial<S>> extends Comparable<Point<S>> {

	int index();

	S serial();

	@Override
	default int compareTo(Point<S> o) {
		return serial().compareTo(o.serial());
	}

}
