/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import javax.swing.table.AbstractTableModel;
import java.io.Serial;
import java.util.Set;

public class OptParamsModel extends AbstractTableModel {

    @Serial
    private static final long serialVersionUID = -9193756840336575549L;
    String[] m_expirations;
    Double[] m_strikes;

    public OptParamsModel(Set<String> expirations, Set<Double> strikes) {
        expirations.toArray(m_expirations = new String[expirations.size()]);
        strikes.toArray(m_strikes = new Double[strikes.size()]);
    }

    @Override
    public int getRowCount() {
        return Math.max(m_expirations.length, m_strikes.length);
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Expirations";
            case 1 -> "Strikes";
            default -> super.getColumnName(column);
        };

    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case 0 -> rowIndex < m_expirations.length ? m_expirations[rowIndex] : null;
            case 1 -> rowIndex < m_strikes.length ? m_strikes[rowIndex] : null;
            default -> null;
        };
    }

}
