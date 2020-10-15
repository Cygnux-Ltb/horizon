package com.ib.apidemo;

import javax.swing.JDialog;

import com.ib.apidemo.util.TCombo;
import com.ib.apidemo.util.UpperField;
import com.ib.client.ContractLookuper;
import com.ib.client.Order;
import com.ib.client.OrderCondition;

public class PegBenchPanel extends OnOKPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3286509927212027924L;

	/**
	 * 
	 */
	private final JDialog m_parentDlg;
	private final Order m_order;
	final UpperField m_startingPrice = new UpperField();
	final UpperField m_startingRefPrice = new UpperField();
	final ContractLookupButton m_refCon;
	final UpperField m_pegChangeAmount = new UpperField();
	final UpperField m_refChangeAmount = new UpperField();
	final TCombo<String> m_pegChangeType = new TCombo<String>("increase", "decrease");

	public PegBenchPanel(JDialog parentDlg, Order order, ContractLookuper lookuper) {
		m_parentDlg = parentDlg;
		m_order = order;
		m_startingPrice.setText(m_order.startingPrice());
		m_startingRefPrice.setText(m_order.stockRefPrice());
		m_pegChangeAmount.setText(m_order.peggedChangeAmount());
		m_refChangeAmount.setText(m_order.referenceChangeAmount());
		m_pegChangeType.setSelectedIndex(m_order.isPeggedChangeAmountDecrease() ? 1 : 0);

		m_refCon = new ContractLookupButton(m_order.referenceContractId(), m_order.referenceExchangeId(), lookuper) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 2174136602270536778L;

			@Override
			protected void actionPerformed(int refConId, String refExchId) {
				PegBenchPanel.this.m_order.referenceContractId(refConId);
				PegBenchPanel.this.m_order.referenceExchangeId(refExchId);
			}
			
		};

		add("Starting price", m_startingPrice);
		add("Reference contract", m_refCon);
		add("Starting reference price", m_startingRefPrice);
		add("Pegged change amount", m_pegChangeAmount);
		add("Pegged change type", m_pegChangeType);
		add("Reference change amount", m_refChangeAmount);
	}

	public OrderCondition onOK() {
		m_order.startingPrice(m_startingPrice.getDouble());
		m_order.stockRefPrice(m_startingRefPrice.getDouble());
		m_order.peggedChangeAmount(m_pegChangeAmount.getDouble());
		m_order.referenceChangeAmount(m_refChangeAmount.getDouble());
		m_order.isPeggedChangeAmountDecrease(m_pegChangeType.getSelectedIndex() == 1);

		return null;
	}
}