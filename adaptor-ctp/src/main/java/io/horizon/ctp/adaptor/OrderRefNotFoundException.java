package io.horizon.ctp.adaptor;

import java.io.Serial;

public final class OrderRefNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = -74254388017422611L;

    public OrderRefNotFoundException(long ordSysId) {
        super("ordSysId -> [" + ordSysId + "] is not find orderRef.");
    }

    public OrderRefNotFoundException(String orderRef) {
        super("orderRef -> [" + orderRef + "] is not find uniqueId.");
    }

}
