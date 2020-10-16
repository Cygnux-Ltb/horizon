package io.gemini.definition.event;

import io.gemini.definition.adaptor.AdaptorEvent;

public interface AdaptorEventHandler {

	void onAdaptorEvent(AdaptorEvent event);

}
