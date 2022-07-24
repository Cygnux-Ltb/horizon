/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ib.apidemo.util.VerticalPanel.StackPanel;
import com.ib.client.*;
import com.ib.client.Types.TickByTickType;
import com.ib.controller.ApiController.ITickByTickDataHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class TickByTickResultsPanel extends NewTabPanel implements ITickByTickDataHandler {

    private final JTable m_table;
    private final List<TickByTick> m_tickByTickRows = new ArrayList<>();
    private final TickByTickModel m_tickModel;

    TickByTickResultsPanel(TickByTickType tickType) {
        m_tickModel = new TickByTickModel(m_tickByTickRows, tickType);
        m_table = new JTable(m_tickModel);
        JScrollPane scroll = new JScrollPane(m_table) {
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();

                d.width = 500;

                return d;
            }
        };

        setLayout(new GridLayout());
        StackPanel hTicksPanel = new StackPanel();

        hTicksPanel.add(new JLabel("Tick-By-Tick: " + tickType.toString()));
        hTicksPanel.add(scroll, BorderLayout.WEST);
        add(hTicksPanel);
    }

    @Override
    public void tickByTickAllLast(int reqId, int tickType, long time, double price, int size, TickAttribLast tickAttribLast,
                                  String exchange, String specialConditions) {
        TickByTick tick = new TickByTick(tickType, time, price, size, tickAttribLast, exchange, specialConditions);
        m_tickByTickRows.add(tick);

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }

    @Override
    public void tickByTickBidAsk(int reqId, long time, double bidPrice, double askPrice, int bidSize, int askSize,
                                 TickAttribBidAsk tickAttribBidAsk) {
        TickByTick tick = new TickByTick(time, bidPrice, bidSize, askPrice, askSize, tickAttribBidAsk);
        m_tickByTickRows.add(tick);

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }

    @Override
    public void tickByTickMidPoint(int reqId, long time, double midPoint) {
        TickByTick tick = new TickByTick(time, midPoint);
        m_tickByTickRows.add(tick);

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }

    @Override
    public void activated() {
    }

    @Override
    public void closed() {
        ApiDemo.INSTANCE.controller().cancelTickByTickData(this);
    }

    @Override
    public void tickByTickHistoricalTickAllLast(int reqId, List<HistoricalTickLast> ticks) {
        for (HistoricalTickLast tick : ticks) {
            TickByTick tickByTick = new TickByTick(2, tick.time(), tick.price(), tick.size(), tick.tickAttribLast(), tick.exchange(), tick.specialConditions());
            m_tickByTickRows.add(tickByTick);
        }

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }

    @Override
    public void tickByTickHistoricalTickBidAsk(int reqId, List<HistoricalTickBidAsk> ticks) {
        for (HistoricalTickBidAsk tick : ticks) {
            TickByTick tickByTick = new TickByTick(tick.time(), tick.priceBid(), tick.sizeBid(), tick.priceAsk(), tick.sizeAsk(), tick.tickAttribBidAsk());
            m_tickByTickRows.add(tickByTick);
        }

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }

    @Override
    public void tickByTickHistoricalTick(int reqId, List<HistoricalTick> ticks) {
        for (HistoricalTick tick : ticks) {
            TickByTick tickByTick = new TickByTick(tick.time(), tick.price());
            m_tickByTickRows.add(tickByTick);
        }

        m_table.setModel(m_tickModel);
        m_tickModel.fireTableDataChanged();
    }
}