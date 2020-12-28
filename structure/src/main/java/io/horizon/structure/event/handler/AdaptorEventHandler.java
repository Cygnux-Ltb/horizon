package io.horizon.definition.event.handler;

import javax.annotation.Nonnull;

import io.horizon.definition.adaptor.AdaptorEvent;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(@Nonnull final AdaptorEvent event);

}
