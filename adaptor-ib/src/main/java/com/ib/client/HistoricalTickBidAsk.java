/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.client;

public class HistoricalTickBidAsk {
    private final long m_time;
    private final TickAttribBidAsk m_tickAttribBidAsk;
    private final double m_priceBid;
    private final double m_priceAsk;
    private final long m_sizeBid;
    private final long m_sizeAsk;

    public HistoricalTickBidAsk(long time, TickAttribBidAsk tickAttribBidAsk, double priceBid, double priceAsk, long sizeBid, long sizeAsk) {
        m_time = time;
        m_tickAttribBidAsk = tickAttribBidAsk;
        m_priceBid = priceBid;
        m_priceAsk = priceAsk;
        m_sizeBid = sizeBid;
        m_sizeAsk = sizeAsk;
    }

    public long time() {
        return m_time;
    }

    public TickAttribBidAsk tickAttribBidAsk() {
        return m_tickAttribBidAsk;
    }

    public double priceBid() {
        return m_priceBid;
    }

    public double priceAsk() {
        return m_priceAsk;
    }

    public long sizeBid() {
        return m_sizeBid;
    }

    public long sizeAsk() {
        return m_sizeAsk;
    }

}