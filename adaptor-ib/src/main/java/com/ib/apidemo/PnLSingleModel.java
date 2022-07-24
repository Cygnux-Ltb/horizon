/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PnLSingleModel extends AbstractTableModel {

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Pos";
            case 1 -> "Daily PnL";
            case 2 -> "Unrealized PnL";
            case 3 -> "Realized PnL";
            case 4 -> "Value";
            default -> super.getColumnName(column);
        };
    }

    static class Row {
        int m_pos;
        double m_dailyPnL;
        double m_unrealizedPnL;
        double m_realizedPnL;
        double m_value;

        public Row(int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {
            m_pos = pos;
            m_dailyPnL = dailyPnL;
            m_unrealizedPnL = unrealizedPnL;
            m_realizedPnL = realizedPnL;
            m_value = value;
        }
    }

    List<Row> m_rows = new ArrayList<>();

    @Override
    public int getRowCount() {
        return m_rows.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Row r = m_rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> r.m_pos;
            case 1 -> r.m_dailyPnL;
            case 2 -> r.m_unrealizedPnL;
            case 3 -> r.m_realizedPnL;
            case 4 -> r.m_value;
            default -> null;
        };
    }

    public void addRow(int pos, double dailyPnL, double unrealizedPnL, double realizedPnL, double value) {
        m_rows.add(new Row(pos, dailyPnL, unrealizedPnL, realizedPnL, value));

        fireTableDataChanged();
    }

}
