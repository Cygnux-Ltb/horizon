package io.horizon.definition.event.handler;

import io.horizon.definition.adaptor.AdaptorEvent;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(AdaptorEvent event);

}
