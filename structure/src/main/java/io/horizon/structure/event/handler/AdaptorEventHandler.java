package io.horizon.structure.event.handler;

import javax.annotation.Nonnull;

import io.horizon.structure.adaptor.AdaptorEvent;

@FunctionalInterface
public interface AdaptorEventHandler {

	void onAdaptorEvent(@Nonnull final AdaptorEvent event);

}
