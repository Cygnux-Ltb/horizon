/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.AccountInfoPanel.Table;
import com.ib.apidemo.util.HtmlButton;
import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ib.apidemo.util.VerticalPanel;
import com.ib.client.DepthMktDataDescription;
import com.ib.controller.ApiController.IMktDepthExchangesHandler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


public class MktDepthExchangesPanel extends NewTabPanel {
    private final MktDepthExchangesModel m_model = new MktDepthExchangesModel();

    MktDepthExchangesPanel() {
        HtmlButton reqMktDepthExchangesButton = new HtmlButton("Request Market Depth Exchanges") {
            protected void actionPerformed() {
                reqMktDepthExchanges();
            }
        };

        HtmlButton clearMktDepthExchangesButton = new HtmlButton("Clear MarketDepth Exchanges") {
            protected void actionPerformed() {
                clearMktDepthExchanges();
            }
        };

        JPanel buts = new VerticalPanel();
        buts.add(reqMktDepthExchangesButton);
        buts.add(clearMktDepthExchangesButton);

        JTable table = new Table(m_model, 2);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
        add(scroll);
        add(buts, BorderLayout.EAST);
    }

    /**
     * Called when the tab is first visited.
     */
    @Override
    public void activated() { /* noop */ }

    /**
     * Called when the tab is closed by clicking the X.
     */
    @Override
    public void closed() {
        clearMktDepthExchanges();
    }

    private void reqMktDepthExchanges() {
        ApiDemo.INSTANCE.controller().reqMktDepthExchanges(m_model);
    }

    private void clearMktDepthExchanges() {
        m_model.clear();
    }

    private class MktDepthExchangesModel extends AbstractTableModel implements IMktDepthExchangesHandler {
        List<DepthMktDataDescriptionRow> m_list = new ArrayList<>();

        @Override
        public void mktDepthExchanges(DepthMktDataDescription[] depthMktDataDescriptions) {
            for (DepthMktDataDescription depthMktDataDescription : depthMktDataDescriptions) {
                DepthMktDataDescriptionRow row = new DepthMktDataDescriptionRow();
                m_list.add(row);
                row.update(depthMktDataDescription.exchange(), depthMktDataDescription.secType(),
                        depthMktDataDescription.listingExch(), depthMktDataDescription.serviceDataType(),
                        depthMktDataDescription.aggGroup());
            }
            m_model.fireTableDataChanged();
        }

        public void clear() {
            m_list.clear();
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return m_list.size();
        }

        @Override
        public int getColumnCount() {
            return 5;
        }

        @Override
        public String getColumnName(int col) {
            return switch (col) {
                case 0 -> "Exchange";
                case 1 -> "Security Type";
                case 2 -> "Listing Exch";
                case 3 -> "Service Data Type";
                case 4 -> "Agg Group";
                default -> null;
            };
        }

        @Override
        public Object getValueAt(int rowIn, int col) {
            DepthMktDataDescriptionRow row = m_list.get(rowIn);
            return switch (col) {
                case 0 -> row.m_exchange;
                case 1 -> row.m_secType;
                case 2 -> row.m_listingExch;
                case 3 -> row.m_serviceDataType;
                case 4 -> row.m_aggGroup;
                default -> null;
            };
        }
    }

    private static class DepthMktDataDescriptionRow {
        String m_exchange;
        String m_secType;
        String m_listingExch;
        String m_serviceDataType;
        String m_aggGroup;

        void update(String exchange, String secType, String listingExch, String serviceDataType, int aggGroup) {
            m_exchange = exchange;
            m_secType = secType;
            m_listingExch = listingExch;
            m_serviceDataType = serviceDataType;
            m_aggGroup = (aggGroup != Integer.MAX_VALUE ? String.valueOf(aggGroup) : "");
        }
    }
}
