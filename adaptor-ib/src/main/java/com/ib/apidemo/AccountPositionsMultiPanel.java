/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.ib.apidemo.util.HtmlButton;
import com.ib.apidemo.util.NewTabbedPanel;
import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ib.apidemo.util.UpperField;
import com.ib.apidemo.util.VerticalPanel;
import com.ib.client.Contract;
import com.ib.controller.ApiController.IAccountUpdateMultiHandler;
import com.ib.controller.ApiController.IPositionMultiHandler;
import com.ib.controller.Formats;

public class AccountPositionsMultiPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1430865771165402988L;
	
	private final NewTabbedPanel m_requestPanel = new NewTabbedPanel();
	private final NewTabbedPanel m_resultsPanel = new NewTabbedPanel();
	
	AccountPositionsMultiPanel() {
		m_requestPanel.addTab( "Positions Multi", new PositionsMultiPanel() );
		m_requestPanel.addTab( "Account Updates Multi", new AccountUpdatesMultiPanel() );
		
		setLayout( new BorderLayout() );
		add( m_requestPanel, BorderLayout.NORTH);
		add( m_resultsPanel);
	}
	
	private class RequestPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4947750471309185537L;
		
		protected UpperField m_account = new UpperField();
		protected JTextField m_modelCode = new JTextField();
		final JCheckBox m_ledgerAndNLV = new JCheckBox();
		
		RequestPanel() {
			VerticalPanel p = new VerticalPanel();
			p.add( "Account", m_account);
			m_modelCode.setColumns(7);
			p.add( "Model Code", m_modelCode);
			p.add( "LedgerAndNLV", m_ledgerAndNLV);
			
			setLayout( new BorderLayout() );
			add( p);
		}
		
		public void enableLedgerAndNLV(boolean enable){
			m_ledgerAndNLV.setEnabled(enable);
		}
		
		@Override public Dimension getMaximumSize() {
			return super.getPreferredSize();
		}
	}
	
	private class PositionsMultiPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4335232456817468798L;
		final RequestPanel m_requestPanel = new RequestPanel();
		
		PositionsMultiPanel() {
			
			HtmlButton requestPositionsMultiButton = new HtmlButton( "Request Positions Multi") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 7926062356359986403L;

				protected void actionPerformed() {
					onRequestPositionsMulti();
				}
			};
			
			VerticalPanel butPanel = new VerticalPanel();
			butPanel.add( requestPositionsMultiButton);
			setLayout( new BoxLayout( this, BoxLayout.X_AXIS) );
			m_requestPanel.enableLedgerAndNLV(false);
			add( m_requestPanel);
			add( Box.createHorizontalStrut(20));
			add( butPanel);
		}

		protected void onRequestPositionsMulti() {
			PositionsResultsPanel panel = new PositionsResultsPanel();
			String account = m_requestPanel.m_account.getText().trim().toUpperCase();
			String modelCode = m_requestPanel.m_modelCode.getText().trim();
			ApiDemo.INSTANCE.controller().reqPositionsMulti(account, modelCode, panel);
			m_resultsPanel.addTab( "Positions " + (!(account == null || account.isEmpty()) ? (" A:" + account) : "") 
					+ (!(modelCode == null || modelCode.isEmpty()) ? (" M :" + modelCode) : ":"), panel, true, true);
		}
	
		private class PositionsResultsPanel extends NewTabPanel implements IPositionMultiHandler {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4698073517923817385L;
			HashMap<String,PositionRow> m_map = new HashMap<String,PositionRow>();
			ArrayList<PositionRow> m_list = new ArrayList<PositionRow>();
			PositionsModel m_model = new PositionsModel();

			boolean m_complete;
			
			PositionsResultsPanel(){
				JTable tab = new JTable( m_model);
				JScrollPane scroll = new JScrollPane( tab);
				setLayout( new BorderLayout());
				add( scroll, BorderLayout.WEST);
			}

			@Override
			public void positionMulti(String account, String modelCode, Contract contract, double pos, double avgCost) {
				String key = contract.conid() + "_" + account + "_" + modelCode;
				PositionRow row = m_map.get( key);
				if (row == null) {
					row = new PositionRow();
					m_map.put( key, row);
					m_list.add( row);
				}
				row.update( account, modelCode, contract, pos, avgCost);
				
				if (m_complete) {
					m_model.fireTableDataChanged();
				}
				
			}

			@Override
			public void positionMultiEnd() {
				m_model.fireTableDataChanged();
				m_complete = true;
				
			}
			
			/** Called when the tab is first visited. */
			@Override public void activated() {
			}
			
			/** Called when the tab is closed by clicking the X. */
			@Override public void closed() {
				ApiDemo.INSTANCE.controller().cancelPositionsMulti( this);
			}
			
			class PositionsModel extends AbstractTableModel {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6806866097059561985L;

				@Override public int getRowCount() {
					return m_map.size();
				}

				@Override public int getColumnCount() {
					return 5;
				}
				
				@Override public String getColumnName(int col) {
					switch( col) {
						case 0: return "Account";
						case 1: return "ModelCode";
						case 2: return "Contract";
						case 3: return "Position";
						case 4: return "Avg Cost";
						default: return null;
					}
				}

				@Override public Object getValueAt(int rowIn, int col) {
					PositionRow row = m_list.get( rowIn);
					
					switch( col) {
						case 0: return row.m_account;
						case 1: return row.m_modelCode;
						case 2: return row.m_contract.description();
						case 3: return row.m_position;
						case 4: return Formats.fmt( row.m_avgCost);
						default: return null;
					}
				}
				
			}					

			private class PositionRow {
				String m_account;
				String m_modelCode;
				Contract m_contract;
				double m_position;
				double m_avgCost;

				void update(String account, String modelCode, Contract contract, double position, double avgCost) {
					m_account = account;
					m_modelCode = modelCode;
					m_contract = contract;
					m_position = position;
					m_avgCost = avgCost;
				}
			}
			
		}
	}		
		
	private class AccountUpdatesMultiPanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 2555083282588206061L;
		final RequestPanel m_requestPanel = new RequestPanel();
		
		AccountUpdatesMultiPanel() {
			
			HtmlButton requestAccountUpdatesMultiButton = new HtmlButton( "Request Account Updates Multi") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 8943286884170579586L;

				protected void actionPerformed() {
					onRequestAccountUpdatesMulti();
				}
			};
			
			VerticalPanel butPanel = new VerticalPanel();
			butPanel.add( requestAccountUpdatesMultiButton);
			setLayout( new BoxLayout( this, BoxLayout.X_AXIS) );
			m_requestPanel.enableLedgerAndNLV(true);
			add( m_requestPanel);
			add( Box.createHorizontalStrut(20));
			add( butPanel);
		}

		protected void onRequestAccountUpdatesMulti() {
			AccountUpdatesResultsPanel panel = new AccountUpdatesResultsPanel();
			String account = m_requestPanel.m_account.getText().trim().toUpperCase();
			String modelCode = m_requestPanel.m_modelCode.getText().trim();
			boolean ledgerAndNLV = m_requestPanel.m_ledgerAndNLV.isSelected();
			ApiDemo.INSTANCE.controller().reqAccountUpdatesMulti(account, modelCode, ledgerAndNLV, panel);
			m_resultsPanel.addTab( "Acc Updates " + (!(account == null || account.isEmpty()) ? (" A:" + account) : "") 
					+ (!(modelCode == null || modelCode.isEmpty()) ? (" M :" + modelCode) : "") + (ledgerAndNLV ? " - LW" : ""), panel, true, true);
		}
	
		private class AccountUpdatesResultsPanel extends NewTabPanel implements IAccountUpdateMultiHandler {
			/**
			 * 
			 */
			private static final long serialVersionUID = 4849182491150225694L;
			HashMap<AccountUpdateKey, AccountUpdateRow> m_map = new HashMap<AccountUpdateKey, AccountUpdateRow>();
			ArrayList<AccountUpdateRow> m_list = new ArrayList<AccountUpdateRow>();
			AccountUpdatesModel m_model = new AccountUpdatesModel();

			boolean m_complete;
			
			AccountUpdatesResultsPanel(){
				JTable tab = new JTable( m_model);
				JScrollPane scroll = new JScrollPane( tab);
				setLayout( new BorderLayout());
				add( scroll, BorderLayout.CENTER);
			}

			@Override
			public void accountUpdateMulti(String account, String modelCode, String key, String value, String currency) {
				AccountUpdateKey acctUpdateKey = new AccountUpdateKey( key, currency);
				AccountUpdateRow row = m_map.get( acctUpdateKey);
				if (row == null) {
					row = new AccountUpdateRow();
					m_map.put( acctUpdateKey, row);
					m_list.add( row);
				}
				row.update( account, modelCode, key, value, currency);
				
				if (m_complete) {
					m_model.fireTableDataChanged();
				}
				
			}

			@Override
			public void accountUpdateMultiEnd() {
				m_model.fireTableDataChanged();
				m_complete = true;
				
			}
			
			/** Called when the tab is first visited. */
			@Override public void activated() {
			}
			
			/** Called when the tab is closed by clicking the X. */
			@Override public void closed() {
				ApiDemo.INSTANCE.controller().cancelAccountUpdatesMulti( this);
			}
			
			class AccountUpdatesModel extends AbstractTableModel {
				/**
				 * 
				 */
				private static final long serialVersionUID = -1220379899610001200L;

				@Override public int getRowCount() {
					return m_map.size();
				}

				@Override public int getColumnCount() {
					return 5;
				}
				
				@Override public String getColumnName(int col) {
					switch( col) {
						case 0: return "Account";
						case 1: return "ModelCode";
						case 2: return "Key";
						case 3: return "Value";
						case 4: return "Currency";
						default: return null;
					}
				}

				@Override public Object getValueAt(int rowIn, int col) {
					AccountUpdateRow row = m_list.get( rowIn);
					
					switch( col) {
						case 0: return row.m_account;
						case 1: return row.m_modelCode;
						case 2: return row.m_key;
						case 3: return row.m_value;
						case 4: return row.m_currency;
						default: return null;
					}
				}
				
			}
			
			private class AccountUpdateKey {
				String m_key;
				String m_currency;

				AccountUpdateKey( String key, String currency) {
					m_key = key;
					m_currency = currency == null ? "" : currency;
				}
				
				@Override public int hashCode() {
					return m_key.hashCode() + m_currency.hashCode();
				}
				
				@Override public boolean equals(Object obj) {
					AccountUpdateKey other = (AccountUpdateKey)obj;
					return m_key.equals( other.m_key) && m_currency.equals( other.m_currency);
				}
			}

			private class AccountUpdateRow {
				String m_account;
				String m_modelCode;
				String m_key;
				String m_value;
				String m_currency;

				void update(String account, String modelCode, String key, String value, String currency) {
					m_account = account;
					m_modelCode = modelCode;
					m_key = key;
					m_value = value;
					m_currency = currency;
				}
			}
			
		}
	}			
}
