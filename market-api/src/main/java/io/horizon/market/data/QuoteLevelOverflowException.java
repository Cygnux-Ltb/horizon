package io.horizon.market.data;

import java.io.Serial;

public class QuoteLevelOverflowException extends RuntimeException {
    
    @Serial
    private static final long serialVersionUID = 2602076635184902103L;

    public QuoteLevelOverflowException(String msg) {
        super(msg);
    }

}
