/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class PnLModel extends AbstractTableModel {

    private static class PnLItem {

        private final double m_dailyPnL;
        private final double m_unrealizedPnL;
        private final double m_realizedPnL;

        public PnLItem(double dailyPnL, double unrealizedPnL, double realizedPnL) {
            m_dailyPnL = dailyPnL;
            m_unrealizedPnL = unrealizedPnL;
            m_realizedPnL = realizedPnL;
        }

        public double dailyPnL() {
            return m_dailyPnL;
        }

        public double unrealizedPnL() {
            return m_unrealizedPnL;
        }

        public double realizedPnL() {
            return m_realizedPnL;
        }

    }

    private final List<PnLItem> m_rows = new ArrayList<>();
    private static final Dictionary<Integer, String> columnNames = new Hashtable<>();

    static {
        columnNames.put(0, "Daily Pnl");
        columnNames.put(1, "Unrealized PnL");
        columnNames.put(2, "Realized PnL");
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
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
        PnLItem item = m_rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> item.dailyPnL();
            case 1 -> item.unrealizedPnL();
            case 2 -> item.realizedPnL();
            default -> null;
        };

    }

    public void addRow(double dailyPnL, double unrealizedPnL, double realizedPnL) {
        m_rows.add(new PnLItem(dailyPnL, unrealizedPnL, realizedPnL));

        fireTableDataChanged();
    }

}
