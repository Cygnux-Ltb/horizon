package com.ib.apidemo;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.ib.client.ContractCondition;
import com.ib.client.ContractLookuper;
import com.ib.client.OrderCondition;

public class ConditionsModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8863694383693992945L;

	@Override
	public void setValueAt(Object val, int row, int col) {
		if (col == 0) {
			super.setValueAt(val, row, col);

			return;
		}

		m_conditions.get(row).conjunctionConnection(val.toString().equals("and"));
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 1;
	}

	ArrayList<OrderCondition> m_conditions;
	ContractLookuper m_lookuper;

	public ConditionsModel(ArrayList<OrderCondition> conditions, ContractLookuper lookuper) {
		m_conditions = conditions;
		m_lookuper = lookuper;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return m_conditions.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			OrderCondition orderCondition = m_conditions.get(rowIndex);

			return orderCondition instanceof ContractCondition
					? ((ContractCondition) orderCondition).toString(m_lookuper)
					: orderCondition.toString();
		}

		return m_conditions.get(rowIndex).conjunctionConnection() ? "and" : "or";
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0:
			return "Description";

		case 1:
			return "Logic";
		}

		return null;
	}

}
