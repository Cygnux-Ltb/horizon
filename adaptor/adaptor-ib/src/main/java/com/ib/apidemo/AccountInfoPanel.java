/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import static com.ib.controller.Formats.fmt0;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import com.ib.apidemo.util.NewTabbedPanel;
import com.ib.apidemo.util.NewTabbedPanel.INewTab;
import com.ib.client.Types.SecType;
import com.ib.controller.ApiController.IAccountHandler;
import com.ib.controller.MarketValueTag;
import com.ib.controller.Position;

public class AccountInfoPanel extends JPanel implements INewTab, IAccountHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1821805215205370121L;

	private DefaultListModel<String> m_acctList = new DefaultListModel<>();
	private JList<String> m_accounts = new JList<>(m_acctList);
	private String m_selAcct = "";
	private MarginModel m_marginModel = new MarginModel();
	private JTable m_marginTable = new Table(m_marginModel);
	private PortfolioModel m_portfolioModel = new PortfolioModel();
	private JTable m_portfolioTable = new Table(m_portfolioModel);
	private MktValModel m_mktValModel = new MktValModel();
	private JTable m_mktValTable = new Table(m_mktValModel, 2);
	private JLabel m_lastUpdated = new JLabel();

	AccountInfoPanel() {
		m_lastUpdated.setHorizontalAlignment(SwingConstants.RIGHT);

		m_accounts.setPreferredSize(new Dimension(10000, 100));
		JScrollPane acctScroll = new JScrollPane(m_accounts);
		acctScroll.setBorder(new TitledBorder("Select Account"));

		JScrollPane marginScroll = new JScrollPane(m_marginTable);
		JScrollPane mvScroll = new JScrollPane(m_mktValTable);
		JScrollPane portScroll = new JScrollPane(m_portfolioTable);

		NewTabbedPanel tabbedPanel = new NewTabbedPanel();
		tabbedPanel.addTab("Balances and Margin", marginScroll);
		tabbedPanel.addTab("Market Value", mvScroll);
		tabbedPanel.addTab("Portfolio", portScroll);
		tabbedPanel.addTab("Account Summary", new AccountSummaryPanel());
		tabbedPanel.addTab("Market Value Summary", new MarketValueSummaryPanel());
		tabbedPanel.addTab("Positions (all accounts)", new PositionsPanel());

		setLayout(new BorderLayout());
		add(acctScroll, BorderLayout.NORTH);
		add(tabbedPanel);
		add(m_lastUpdated, BorderLayout.SOUTH);

		m_accounts.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				onChanged();
			}
		});
	}

	/** Called when the tab is first visited. */
	@Override
	public void activated() {
		for (String account : ApiDemo.INSTANCE.accountList()) {
			m_acctList.addElement(account);
		}

		if (ApiDemo.INSTANCE.accountList().size() == 1) {
			m_accounts.setSelectedIndex(0);
		}
	}

	/** Called when the tab is closed by clicking the X. */
	@Override
	public void closed() {
	}

	protected synchronized void onChanged() {
		int i = m_accounts.getSelectedIndex();
		if (i != -1) {
			String selAcct = m_acctList.get(i);
			if (!selAcct.equals(m_selAcct)) {
				m_selAcct = selAcct;
				m_marginModel.clear();
				m_mktValModel.clear();
				m_portfolioModel.clear();
				ApiDemo.INSTANCE.controller().reqAccountUpdates(true, m_selAcct, this);
			}
		}
	}

	/** Receive account value. */
	public synchronized void accountValue(String account, String tag, String value, String currency) {
		if (account.equals(m_selAcct)) {
			try {
				MarketValueTag mvTag = MarketValueTag.valueOf(tag);
				m_mktValModel.handle(account, currency, mvTag, value);
			} catch (Exception e) {
				m_marginModel.handle(tag, value, currency, account);
			}
		}
	}

	/** Receive position. */
	public synchronized void updatePortfolio(Position position) {
		if (position.account().equals(m_selAcct)) {
			m_portfolioModel.update(position);
		}
	}

	/** Receive time of last update. */
	public void accountTime(String timeStamp) {
		m_lastUpdated.setText("Last updated: " + timeStamp + "       ");
	}

	public void accountDownloadEnd(String account) {
	}

	private class MarginModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 4803262539738416494L;

		HashMap<MarginRowKey, MarginRow> m_map = new HashMap<MarginRowKey, MarginRow>();
		ArrayList<MarginRow> m_list = new ArrayList<MarginRow>();

		void clear() {
			m_map.clear();
			m_list.clear();
		}

		public void handle(String tag, String value, String currency, String account) {
			// useless
			if (tag.equals("Currency")) {
				return;
			}

			int type = 0; // 0=whole acct; 1=securities; 2=commodities

			// "Securities" segment?
			if (tag.endsWith("-S")) {
				tag = tag.substring(0, tag.length() - 2);
				type = 1;
			}

			// "Commodities" segment?
			else if (tag.endsWith("-C")) {
				tag = tag.substring(0, tag.length() - 2);
				type = 2;
			}

			MarginRowKey key = new MarginRowKey(tag, currency);
			MarginRow row = m_map.get(key);

			if (row == null) {
				// don't add new rows with a value of zero
				if (isZero(value)) {
					return;
				}

				row = new MarginRow(tag, currency);
				m_map.put(key, row);
				m_list.add(row);
				Collections.sort(m_list);
			}

			switch (type) {
			case 0:
				row.m_val = value;
				break;
			case 1:
				row.m_secVal = value;
				break;
			case 2:
				row.m_comVal = value;
				break;
			}

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					fireTableDataChanged();
				}
			});
		}

		@Override
		public int getRowCount() {
			return m_list.size();
		}

		@Override
		public int getColumnCount() {
			return 4;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Tag";
			case 1:
				return "Account Value";
			case 2:
				return "Securities Value";
			case 3:
				return "Commodities Value";
			default:
				return null;
			}
		}

		@Override
		public Object getValueAt(int rowIn, int col) {
			MarginRow row = m_list.get(rowIn);

			switch (col) {
			case 0:
				return row.m_tag;
			case 1:
				return format(row.m_val, row.m_currency);
			case 2:
				return format(row.m_secVal, row.m_currency);
			case 3:
				return format(row.m_comVal, row.m_currency);
			default:
				return null;
			}
		}
	}

	private static class MarginRow implements Comparable<MarginRow> {
		String m_tag;
		String m_currency;
		String m_val;
		String m_secVal;
		String m_comVal;

		MarginRow(String tag, String cur) {
			m_tag = tag;
			m_currency = cur;
		}

		@Override
		public int compareTo(MarginRow o) {
			return m_tag.compareTo(o.m_tag);
		}
	}

	private static class MarginRowKey {
		String m_tag;
		String m_currency;

		public MarginRowKey(String key, String currency) {
			m_tag = key;
			m_currency = currency;
		}

		@Override
		public int hashCode() {
			int cur = m_currency != null ? m_currency.hashCode() : 0;
			return m_tag.hashCode() + cur;
		}

		@Override
		public boolean equals(Object obj) {
			MarginRowKey other = (MarginRowKey) obj;
			return m_tag.equals(other.m_tag) && (m_currency == null && other.m_currency == null
					|| m_currency != null && m_currency.equals(other.m_currency));
		}
	}

	static class MktValModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6859058109767543359L;
		private HashMap<String, MktValRow> m_map = new HashMap<String, MktValRow>();
		private ArrayList<MktValRow> m_list = new ArrayList<MktValRow>();

		void handle(String account, String currency, MarketValueTag mvTag, String value) {
			String key = account + currency;
			MktValRow row = m_map.get(key);
			if (row == null) {
				row = new MktValRow(account, currency);
				m_map.put(key, row);
				m_list.add(row);
			}
			row.set(mvTag, value);
			fireTableDataChanged();
		}

		void clear() {
			m_map.clear();
			m_list.clear();
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return m_list.size();
		}

		@Override
		public int getColumnCount() {
			return MarketValueTag.values().length + 2;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Account";
			case 1:
				return "Currency";
			default:
				return MarketValueTag.get(col - 2).toString();
			}
		}

		@Override
		public Object getValueAt(int rowIn, int col) {
			MktValRow row = m_list.get(rowIn);
			switch (col) {
			case 0:
				return row.m_account;
			case 1:
				return row.m_currency;
			default:
				return format(row.get(MarketValueTag.get(col - 2)), null);
			}
		}
	}

//	private static class MktValKey {
//		String m_account;
//		String m_currency;
//
//		public MktValKey(String account, String currency) {
//			m_account = account;
//			m_currency = currency;
//		}
//
//		@Override public int hashCode() {
//			return m_account.hashCode() + m_currency.hashCode();
//		}
//		
//		@Override public boolean equals(Object obj) {
//			MktValKey other = (MktValKey)obj;
//			return m_account.equals( other.m_account) && m_currency.equals( other.m_currency);
//		}
//	}

	private static class MktValRow {
		String m_account;
		String m_currency;
		HashMap<MarketValueTag, String> m_map = new HashMap<MarketValueTag, String>();

		public MktValRow(String account, String currency) {
			m_account = account;
			m_currency = currency;
		}

		public String get(MarketValueTag tag) {
			return m_map.get(tag);
		}

		public void set(MarketValueTag tag, String value) {
			m_map.put(tag, value);
		}
	}

	/** Shared with ExercisePanel. */
	static class PortfolioModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5638147400177371801L;
		private HashMap<Integer, Position> m_portfolioMap = new HashMap<Integer, Position>();
		private ArrayList<Integer> m_positions = new ArrayList<Integer>(); // must store key because Position is
																			// overwritten

		void clear() {
			m_positions.clear();
			m_portfolioMap.clear();
		}

		Position getPosition(int i) {
			return m_portfolioMap.get(m_positions.get(i));
		}

		public void update(Position position) {
			// skip fake FX positions
			if (position.contract().secType() == SecType.CASH) {
				return;
			}

			if (!m_portfolioMap.containsKey(position.conid()) && position.position() != 0) {
				m_positions.add(position.conid());
			}
			m_portfolioMap.put(position.conid(), position);
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return m_positions.size();
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public String getColumnName(int col) {
			switch (col) {
			case 0:
				return "Description";
			case 1:
				return "Position";
			case 2:
				return "Price";
			case 3:
				return "Value";
			case 4:
				return "Avg Cost";
			case 5:
				return "Unreal Pnl";
			case 6:
				return "Real Pnl";
			default:
				return null;
			}
		}

		@Override
		public Object getValueAt(int row, int col) {
			Position pos = getPosition(row);
			switch (col) {
			case 0:
				return pos.contract().description();
			case 1:
				return pos.position();
			case 2:
				return pos.marketPrice();
			case 3:
				return format("" + pos.marketValue(), null);
			case 4:
				return pos.averageCost();
			case 5:
				return pos.unrealPnl();
			case 6:
				return pos.realPnl();
			default:
				return null;
			}
		}
	}

	private static boolean isZero(String value) {
		try {
			return Double.parseDouble(value) == 0;
		} catch (Exception e) {
			return false;
		}
	}

	/** If val is a number, format it with commas and no decimals. */
	static String format(String val, String currency) {
		if (val == null || val.length() == 0) {
			return null;
		}

		try {
			double dub = Double.parseDouble(val);
			val = fmt0(dub);
		} catch (Exception e) {
		}

		return currency != null && currency.length() > 0 ? val + " " + currency : val;
	}

	/**
	 * Table where first n columsn are left-justified, all other columns are
	 * right-justified.
	 */
	static class Table extends JTable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8811256362558860107L;

		private int m_n;

		public Table(AbstractTableModel model) {
			this(model, 1);
		}

		public Table(AbstractTableModel model, int n) {
			super(model);
			m_n = n;
		}

		@Override
		public TableCellRenderer getCellRenderer(int row, int col) {
			TableCellRenderer rend = super.getCellRenderer(row, col);
			((JLabel) rend).setHorizontalAlignment(col < m_n ? SwingConstants.LEFT : SwingConstants.RIGHT);
			return rend;
		}
	}
}
