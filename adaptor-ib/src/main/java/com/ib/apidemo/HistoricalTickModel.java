/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.client.HistoricalTick;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.List;

class HistoricalTickModel extends AbstractTableModel {

    @Serial
    private static final long serialVersionUID = -6009427868982343868L;
    private final List<HistoricalTick> m_rows;

    public HistoricalTickModel(List<HistoricalTick> rows) {
        m_rows = rows;
    }

    @Override
    public int getRowCount() {
        return m_rows.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HistoricalTick row = m_rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> row.time();
            case 1 -> row.price();
            case 2 -> row.size();
            default -> null;
        };

    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Time";
            case 1 -> "Price";
            case 2 -> "Size";
            default -> super.getColumnName(column);
        };

    }

}
