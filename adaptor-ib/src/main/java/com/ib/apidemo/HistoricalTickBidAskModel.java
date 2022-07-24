/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.client.HistoricalTickBidAsk;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class HistoricalTickBidAskModel extends AbstractTableModel {

    private final List<HistoricalTickBidAsk> m_rows;

    public HistoricalTickBidAskModel(List<HistoricalTickBidAsk> rows) {
        m_rows = rows;
    }

    @Override
    public int getRowCount() {
        return m_rows.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HistoricalTickBidAsk row = m_rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> row.time();
            case 1 -> row.priceBid();
            case 2 -> row.priceAsk();
            case 3 -> row.sizeBid();
            case 4 -> row.sizeAsk();
            case 5 -> row.tickAttribBidAsk().toString();
            default -> null;
        };

    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Time";
            case 1 -> "Price Bid";
            case 2 -> "Price Ask";
            case 3 -> "Size Bid";
            case 4 -> "Size Ask";
            case 5 -> "Bid/Ask Tick Attribs";
            default -> super.getColumnName(column);
        };

    }

}
