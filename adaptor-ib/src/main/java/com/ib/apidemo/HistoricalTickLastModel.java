/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.client.HistoricalTickLast;

import javax.swing.table.AbstractTableModel;
import java.util.List;

class HistoricalTickLastModel extends AbstractTableModel {

    private final List<HistoricalTickLast> m_rows;

    public HistoricalTickLastModel(List<HistoricalTickLast> rows) {
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
        HistoricalTickLast row = m_rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> row.time();
            case 1 -> row.price();
            case 2 -> row.size();
            case 3 -> row.exchange();
            case 4 -> row.specialConditions();
            case 5 -> row.tickAttribLast().toString();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Time";
            case 1 -> "Price";
            case 2 -> "Size";
            case 3 -> "Exchange";
            case 4 -> "Special conditions";
            case 5 -> "Last Tick Attribs";
            default -> super.getColumnName(column);
        };
    }

}
