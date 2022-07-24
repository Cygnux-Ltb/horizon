/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;

import javax.swing.*;
import java.awt.*;


public class TradingPanel extends NewTabPanel {
    private final OrdersPanel m_ordersPanel = new OrdersPanel();
    private final CompletedOrdersPanel m_completedOrdersPanel = new CompletedOrdersPanel();
    private final TradesPanel m_tradesPanel = new TradesPanel();

    TradingPanel() {
        m_ordersPanel.setPreferredSize(new Dimension(1, 10000));
        m_completedOrdersPanel.setPreferredSize(new Dimension(1, 10000));
        m_tradesPanel.setPreferredSize(new Dimension(1, 10000));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(m_ordersPanel);
        add(m_completedOrdersPanel);
        add(m_tradesPanel);
    }

    /**
     * Called when the tab is first visited.
     */
    @Override
    public void activated() {
        m_ordersPanel.activated();
        m_completedOrdersPanel.activated();
        m_tradesPanel.activated();
    }

    /**
     * Called when the tab is closed by clicking the X.
     */
    @Override
    public void closed() {
    }
}
