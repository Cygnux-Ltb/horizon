package io.gemini.definition.event.handler;

import io.gemini.definition.adaptor.AdaptorEvent;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(AdaptorEvent event);

}
