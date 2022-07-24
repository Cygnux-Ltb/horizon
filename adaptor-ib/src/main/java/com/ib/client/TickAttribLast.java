/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.client;

public class TickAttribLast {
    private boolean m_pastLimit = false; // aka halted
    private boolean m_unreported = false;

    public boolean pastLimit() {
        return m_pastLimit;
    }

    public boolean unreported() {
        return m_unreported;
    }

    public void pastLimit(boolean pastLimit) {
        this.m_pastLimit = pastLimit;
    }

    public void unreported(boolean unreported) {
        this.m_unreported = unreported;
    }

    public String toString() {
        return (m_pastLimit ? "pastLimit " : "") +
                (m_unreported ? "unreported " : "");
    }
}
