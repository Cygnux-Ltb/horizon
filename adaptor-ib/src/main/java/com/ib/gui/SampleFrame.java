/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.ib.client.CommissionReport;
import com.ib.client.Contract;
import com.ib.client.ContractDetails;
import com.ib.client.DeltaNeutralContract;
import com.ib.client.EClientSocket;
import com.ib.client.EJavaSignal;
import com.ib.client.EReader;
import com.ib.client.EWrapper;
import com.ib.client.EWrapperMsgGenerator;
import com.ib.client.Execution;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.SoftDollarTier;
import com.ib.client.TagValue;

class SampleFrame extends JFrame implements EWrapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4524971995423491777L;

	private static final int NOT_AN_FA_ACCOUNT_ERROR = 321;
	private int faErrorCodes[] = { 503, 504, 505, 522, 1100, NOT_AN_FA_ACCOUNT_ERROR };
	private boolean faError;

	private EJavaSignal m_signal = new EJavaSignal();
	private EClientSocket m_client = new EClientSocket(this, m_signal);
	private EReader m_reader;
	private IBTextPanel m_tickers = new IBTextPanel("Market and Historical Data", false);
	private IBTextPanel m_TWS = new IBTextPanel("TWS Server Responses", false);
	private IBTextPanel m_errors = new IBTextPanel("Errors and Messages", false);
	private OrderDlg m_orderDlg = new OrderDlg(this);
	private ExtOrdDlg m_extOrdDlg = new ExtOrdDlg(m_orderDlg);
	private AccountDlg m_acctDlg = new AccountDlg(this);
	private HashMap<Integer, MktDepthDlg> m_mapRequestToMktDepthDlg = new HashMap<Integer, MktDepthDlg>();
	private NewsBulletinDlg m_newsBulletinDlg = new NewsBulletinDlg(this);
	private ScannerDlg m_scannerDlg = new ScannerDlg(this);
	private GroupsDlg m_groupsDlg;
	private SecDefOptParamsReqDlg m_secDefOptParamsReq = new SecDefOptParamsReqDlg(this);

	private ArrayList<TagValue> m_mktDataOptions = new ArrayList<TagValue>();
	private ArrayList<TagValue> m_chartOptions = new ArrayList<TagValue>();
//    private Vector<TagValue> m_orderMiscOptions = new ArrayList<TagValue>();
	private ArrayList<TagValue> m_mktDepthOptions = new ArrayList<TagValue>();
//    private Vector<TagValue> m_scannerSubscriptionOptions = new ArrayList<TagValue>();
	private ArrayList<TagValue> m_realTimeBarsOptions = new ArrayList<TagValue>();

	String faGroupXML;
	String faProfilesXML;
	String faAliasesXML;
	public String m_FAAcctCodes;
	public boolean m_bIsFAAccount = false;

	private boolean m_disconnectInProgress = false;

	SampleFrame() {
		JPanel scrollingWindowDisplayPanel = new JPanel(new GridLayout(0, 1));
		scrollingWindowDisplayPanel.add(m_tickers);
		scrollingWindowDisplayPanel.add(m_TWS);
		scrollingWindowDisplayPanel.add(m_errors);

		JPanel buttonPanel = createButtonPanel();

		getContentPane().add(scrollingWindowDisplayPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.EAST);
		setSize(600, 700);
		setTitle("Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		m_groupsDlg = new GroupsDlg(this, m_client);
	}

	interface ContractDetailsCallback {
		void onContractDetails(ContractDetails contractDetails);

		void onContractDetailsEnd();

		void onError(int errorCode, String errorMsg);
	}

	HashMap<Integer, ContractDetailsCallback> m_callbackMap = new HashMap<Integer, ContractDetailsCallback>();

	public ArrayList<ContractDetails> lookupContract(Contract contract) throws InterruptedException {
		final ArrayList<ContractDetails> rval = new ArrayList<ContractDetails>();
		int reqId = m_orderDlg.m_id;
		final Object sync = new Object();
		final boolean[] done = new boolean[] { false };

		m_callbackMap.put(reqId, new ContractDetailsCallback() {

			@Override
			public void onError(int errorCode, String errorMsg) {
				done[0] = true;
				synchronized (sync) {
					sync.notifyAll();
				}
			}

			@Override
			public void onContractDetailsEnd() {
				done[0] = true;
				synchronized (sync) {
					sync.notifyAll();
				}
			}

			@Override
			public void onContractDetails(ContractDetails contractDetails) {
				rval.add(contractDetails);
			}
		});

		m_client.reqContractDetails(reqId, contract);

		while (!done[0]) {
			synchronized (sync) {
				sync.wait();
			}
		}

		return rval;
	}

	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
		JButton butConnect = new JButton("Connect");
		butConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onConnect();
			}
		});
		JButton butDisconnect = new JButton("Disconnect");
		butDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onDisconnect();
			}
		});
		JButton butMktData = new JButton("Req Mkt Data");
		butMktData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqMktData();
			}
		});
		JButton butCancelMktData = new JButton("Cancel Mkt Data");
		butCancelMktData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelMktData();
			}
		});
		JButton butMktDepth = new JButton("Req Mkt Depth");
		butMktDepth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqMktDepth();
			}
		});
		JButton butCancelMktDepth = new JButton("Cancel Mkt Depth");
		butCancelMktDepth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelMktDepth();
			}
		});
		JButton butHistoricalData = new JButton("Historical Data");
		butHistoricalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onHistoricalData();
			}
		});
		JButton butCancelHistoricalData = new JButton("Cancel Hist. Data");
		butCancelHistoricalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelHistoricalData();
			}
		});
		JButton butFundamentalData = new JButton("Fundamental Data");
		butFundamentalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFundamentalData();
			}
		});
		JButton butCancelFundamentalData = new JButton("Cancel Fund. Data");
		butCancelFundamentalData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelFundamentalData();
			}
		});
		JButton butRealTimeBars = new JButton("Req Real Time Bars");
		butRealTimeBars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqRealTimeBars();
			}
		});
		JButton butCancelRealTimeBars = new JButton("Cancel Real Time Bars");
		butCancelRealTimeBars.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelRealTimeBars();
			}
		});
		JButton butCurrentTime = new JButton("Req Current Time");
		butCurrentTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqCurrentTime();
			}
		});
		JButton butScanner = new JButton("Market Scanner");
		butScanner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onScanner();
			}
		});
		JButton butOpenOrders = new JButton("Req Open Orders");
		butOpenOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqOpenOrders();
			}
		});
		JButton butCalculateImpliedVolatility = new JButton("Calculate Implied Volatility");
		butCalculateImpliedVolatility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCalculateImpliedVolatility();
			}
		});
		JButton butCancelCalculateImpliedVolatility = new JButton("Cancel Calc Impl Volatility");
		butCancelCalculateImpliedVolatility.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelCalculateImpliedVolatility();
			}
		});
		JButton butCalculateOptionPrice = new JButton("Calculate Option Price");
		butCalculateOptionPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCalculateOptionPrice();
			}
		});
		JButton butCancelCalculateOptionPrice = new JButton("Cancel Calc Opt Price");
		butCancelCalculateOptionPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelCalculateOptionPrice();
			}
		});
		JButton butWhatIfOrder = new JButton("What If");
		butWhatIfOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onWhatIfOrder();
			}
		});
		JButton butPlaceOrder = new JButton("Place Order");
		butPlaceOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onPlaceOrder();
			}
		});
		JButton butCancelOrder = new JButton("Cancel Order");
		butCancelOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelOrder();
			}
		});
		JButton butExerciseOptions = new JButton("Exercise Options");
		butExerciseOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onExerciseOptions();
			}
		});
		JButton butExtendedOrder = new JButton("Extended");
		butExtendedOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onExtendedOrder();
			}
		});
		JButton butAcctData = new JButton("Req Acct Data");
		butAcctData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqAcctData();
			}
		});
		JButton butContractData = new JButton("Req Contract Data");
		butContractData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqContractData();
			}
		});
		JButton butExecutions = new JButton("Req Executions");
		butExecutions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqExecutions();
			}
		});
		JButton butNewsBulletins = new JButton("Req News Bulletins");
		butNewsBulletins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqNewsBulletins();
			}
		});
		JButton butServerLogging = new JButton("Server Logging");
		butServerLogging.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onServerLogging();
			}
		});
		JButton butAllOpenOrders = new JButton("Req All Open Orders");
		butAllOpenOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqAllOpenOrders();
			}
		});
		JButton butAutoOpenOrders = new JButton("Req Auto Open Orders");
		butAutoOpenOrders.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqAutoOpenOrders();
			}
		});
		JButton butManagedAccts = new JButton("Req Accounts");
		butManagedAccts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqManagedAccts();
			}
		});
		JButton butFinancialAdvisor = new JButton("Financial Advisor");
		butFinancialAdvisor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onFinancialAdvisor();
			}
		});
		JButton butGlobalCancel = new JButton("Global Cancel");
		butGlobalCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onGlobalCancel();
			}
		});
		JButton butReqMarketDataType = new JButton("Req Market Data Type");
		butReqMarketDataType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onReqMarketDataType();
			}
		});

		JButton butRequestPositions = new JButton("Request Positions");
		butRequestPositions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRequestPositions();
			}
		});
		JButton butCancelPositions = new JButton("Cancel Positions");
		butCancelPositions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelPositions();
			}
		});
		JButton butRequestAccountSummary = new JButton("Request Account Summary");
		butRequestAccountSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRequestAccountSummary();
			}
		});
		JButton butCancelAccountSummary = new JButton("Cancel Account Summary");
		butCancelAccountSummary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelAccountSummary();
			}
		});
		JButton butRequestPositionsMulti = new JButton("Request Positions Multi");
		butRequestPositionsMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRequestPositionsMulti();
			}
		});
		JButton butCancelPositionsMulti = new JButton("Cancel Positions Multi");
		butCancelPositionsMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelPositionsMulti();
			}
		});
		JButton butRequestAccountUpdatesMulti = new JButton("Request Account Updates Multi");
		butRequestAccountUpdatesMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRequestAccountUpdatesMulti();
			}
		});
		JButton butCancelAccountUpdatesMulti = new JButton("Cancel Account Updates Multi");
		butCancelAccountUpdatesMulti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onCancelAccountUpdatesMulti();
			}
		});
		JButton butRequestSecurityDefinitionOptionParameters = new JButton(
				"Request Security Definition Option Parameters");
		butRequestSecurityDefinitionOptionParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onRequestSecurityDefinitionOptionParameters();
			}
		});
		JButton butGroups = new JButton("Groups");
		butGroups.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onGroups();
			}
		});

		JButton butClear = new JButton("Clear");
		butClear.addActionListener(e -> onClear());

		JButton butClose = new JButton("Close");
		butClose.addActionListener(e -> onClose());

		buttonPanel.add(new JPanel());
		buttonPanel.add(butConnect);
		buttonPanel.add(butDisconnect);

		buttonPanel.add(new JPanel());
		buttonPanel.add(butMktData);
		buttonPanel.add(butCancelMktData);
		buttonPanel.add(butMktDepth);
		buttonPanel.add(butCancelMktDepth);
		buttonPanel.add(butHistoricalData);
		buttonPanel.add(butCancelHistoricalData);
		buttonPanel.add(butFundamentalData);
		buttonPanel.add(butCancelFundamentalData);
		buttonPanel.add(butRealTimeBars);
		buttonPanel.add(butCancelRealTimeBars);
		buttonPanel.add(butScanner);
		buttonPanel.add(butCurrentTime);
		buttonPanel.add(butCalculateImpliedVolatility);
		buttonPanel.add(butCancelCalculateImpliedVolatility);
		buttonPanel.add(butCalculateOptionPrice);
		buttonPanel.add(butCancelCalculateOptionPrice);

		buttonPanel.add(new JPanel());
		buttonPanel.add(butWhatIfOrder);
		buttonPanel.add(butPlaceOrder);
		buttonPanel.add(butCancelOrder);
		buttonPanel.add(butExerciseOptions);
		buttonPanel.add(butExtendedOrder);

		buttonPanel.add(new JPanel());
		buttonPanel.add(butContractData);
		buttonPanel.add(butOpenOrders);
		buttonPanel.add(butAllOpenOrders);
		buttonPanel.add(butAutoOpenOrders);
		buttonPanel.add(butAcctData);
		buttonPanel.add(butExecutions);
		buttonPanel.add(butNewsBulletins);
		buttonPanel.add(butServerLogging);
		buttonPanel.add(butManagedAccts);
		buttonPanel.add(butFinancialAdvisor);
		buttonPanel.add(butGlobalCancel);
		buttonPanel.add(butReqMarketDataType);
		buttonPanel.add(butRequestPositions);
		buttonPanel.add(butCancelPositions);
		buttonPanel.add(butRequestAccountSummary);
		buttonPanel.add(butCancelAccountSummary);
		buttonPanel.add(butRequestPositionsMulti);
		buttonPanel.add(butCancelPositionsMulti);
		buttonPanel.add(butRequestAccountUpdatesMulti);
		buttonPanel.add(butCancelAccountUpdatesMulti);
		buttonPanel.add(butRequestSecurityDefinitionOptionParameters);
		buttonPanel.add(butGroups);

		buttonPanel.add(new JPanel());
		buttonPanel.add(butClear);
		buttonPanel.add(butClose);

		return buttonPanel;
	}

	protected void onRequestSecurityDefinitionOptionParameters() {
		m_secDefOptParamsReq.setModal(true);
		m_secDefOptParamsReq.setVisible(true);

		String underlyingSymbol = m_secDefOptParamsReq.underlyingSymbol();
		String futFopExchange = m_secDefOptParamsReq.futFopExchange();
//		String currency = m_secDefOptParamsReq.currency();
		String underlyingSecType = m_secDefOptParamsReq.underlyingSecType();
		int underlyingConId = m_secDefOptParamsReq.underlyingConId();

		if (m_secDefOptParamsReq.isOK()) {
			m_client.reqSecDefOptParams(m_secDefOptParamsReq.id(), underlyingSymbol, futFopExchange,
					/* currency, */ underlyingSecType, underlyingConId);
		}
	}

	void onConnect() {
		m_bIsFAAccount = false;
		// get connection parameters
		ConnectDlg dlg = new ConnectDlg(this);
		dlg.setVisible(true);
		if (!dlg.m_rc) {
			return;
		}
		// connect to TWS
		m_disconnectInProgress = false;

		m_client.OptionalCapabilities(dlg.m_retOptCapts);
		m_client.eConnect(dlg.m_retIpAddress, dlg.m_retPort, dlg.m_retClientId);
		if (m_client.isConnected()) {
			m_TWS.add("Connected to Tws server version " + m_client.serverVersion() + " at "
					+ m_client.TwsConnectionTime());
		}
		m_reader = new EReader(m_client, m_signal);
		m_reader.start();

		new Thread(this::processMessages, "ProcessMessages-Thread").start();

	}

	private void processMessages() {
		while (m_client.isConnected()) {
			m_signal.waitForSignal();
			try {
				m_reader.processMsgs();
			} catch (Exception e) {
				error(e);
			}
		}
	}

	void onDisconnect() {
		// disconnect from TWS
		m_disconnectInProgress = true;
		m_client.eDisconnect();
	}

	void onReqMktData() {
		// run m_orderDlg
		m_orderDlg.init("Mkt Data Options", true, "Market Data Options", m_mktDataOptions);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_mktDataOptions = m_orderDlg.getOptions();
		// req mkt data
		m_client.reqMktData(m_orderDlg.m_id, m_orderDlg.m_contract, m_orderDlg.m_genericTicks,
				m_orderDlg.m_snapshotMktData, m_mktDataOptions);
	}

	void onReqRealTimeBars() {
		// run m_orderDlg
		m_orderDlg.init("RTB Options", true, "Real Time Bars Options", m_realTimeBarsOptions);

		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_realTimeBarsOptions = m_orderDlg.getOptions();
		// req real time bars
		m_client.reqRealTimeBars(m_orderDlg.m_id, m_orderDlg.m_contract,
				5 /* TODO: parse and use m_orderDlg.m_barSizeSetting */, m_orderDlg.m_whatToShow,
				m_orderDlg.m_useRTH > 0, m_realTimeBarsOptions);
	}

	void onCancelRealTimeBars() {
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel market data
		m_client.cancelRealTimeBars(m_orderDlg.m_id);
	}

	void onScanner() {
		m_scannerDlg.show();
		if (m_scannerDlg.m_userSelection == ScannerDlg.CANCEL_SELECTION) {
			m_client.cancelScannerSubscription(m_scannerDlg.m_id);
		} else if (m_scannerDlg.m_userSelection == ScannerDlg.SUBSCRIBE_SELECTION) {
			m_client.reqScannerSubscription(m_scannerDlg.m_id, m_scannerDlg.m_subscription,
					m_scannerDlg.scannerSubscriptionOptions());
		} else if (m_scannerDlg.m_userSelection == ScannerDlg.REQUEST_PARAMETERS_SELECTION) {
			m_client.reqScannerParameters();
		}
	}

	void onReqCurrentTime() {
		m_client.reqCurrentTime();
	}

	void onHistoricalData() {
		// run m_orderDlg
		m_orderDlg.init("Chart Options", true, "Chart Options", m_chartOptions);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_chartOptions = m_orderDlg.getOptions();
		// req historical data
		m_client.reqHistoricalData(m_orderDlg.m_id, m_orderDlg.m_contract, m_orderDlg.m_backfillEndTime,
				m_orderDlg.m_backfillDuration, m_orderDlg.m_barSizeSetting, m_orderDlg.m_whatToShow,
				m_orderDlg.m_useRTH, m_orderDlg.m_formatDate, m_chartOptions);
	}

	void onCancelHistoricalData() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel historical data
		m_client.cancelHistoricalData(m_orderDlg.m_id);
	}

	void onFundamentalData() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.reqFundamentalData(m_orderDlg.m_id, m_orderDlg.m_contract, /* reportType */ m_orderDlg.m_whatToShow);
	}

	void onCancelFundamentalData() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.cancelFundamentalData(m_orderDlg.m_id);
	}

	void onReqContractData() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// req mkt data
		m_client.reqContractDetails(m_orderDlg.m_id, m_orderDlg.m_contract);
	}

	void onReqMktDepth() {
		// run m_orderDlg
		m_orderDlg.init("Mkt Depth Options", true, "Market Depth Options", m_mktDepthOptions);

		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_mktDepthOptions = m_orderDlg.getOptions();

		final Integer dialogId = m_orderDlg.m_id;
		MktDepthDlg depthDialog = m_mapRequestToMktDepthDlg.get(dialogId);
		if (depthDialog == null) {
			depthDialog = new MktDepthDlg("Market Depth ID [" + dialogId + "]", this);
			m_mapRequestToMktDepthDlg.put(dialogId, depthDialog);

			// cleanup the map after depth dialog is closed so it does not linger or leak
			// memory
			depthDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					m_mapRequestToMktDepthDlg.remove(dialogId);
				}
			});
		}

		depthDialog.setParams(m_client, dialogId);

		// req mkt data
		m_client.reqMktDepth(dialogId, m_orderDlg.m_contract, m_orderDlg.m_marketDepthRows, m_mktDepthOptions);
		depthDialog.setVisible(true);
	}

	void onCancelMktData() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel market data
		m_client.cancelMktData(m_orderDlg.m_id);
	}

	void onCancelMktDepth() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel market data
		m_client.cancelMktDepth(m_orderDlg.m_id);
	}

	void onReqOpenOrders() {
		m_client.reqOpenOrders();
	}

	void onWhatIfOrder() {
		placeOrder(true);
	}

	void onPlaceOrder() {
		placeOrder(false);
	}

	void placeOrder(boolean whatIf) {
		// run m_orderDlg
		m_orderDlg.init("Order Misc Options", true, "Order Misc Options", m_orderDlg.m_order.orderMiscOptions());

		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}

		Order order = m_orderDlg.m_order;
		order.orderMiscOptions(m_orderDlg.getOptions());

		// save old and set new value of whatIf attribute
		boolean savedWhatIf = order.whatIf();
		order.whatIf(whatIf);

		// place order
		m_client.placeOrder(m_orderDlg.m_id, m_orderDlg.m_contract, order);

		// restore whatIf attribute
		order.whatIf(savedWhatIf);
	}

	void onExerciseOptions() {
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel order
		m_client.exerciseOptions(m_orderDlg.m_id, m_orderDlg.m_contract, m_orderDlg.m_exerciseAction,
				m_orderDlg.m_exerciseQuantity, m_orderDlg.m_order.account(), m_orderDlg.m_override);
	}

	void onCancelOrder() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// cancel order
		m_client.cancelOrder(m_orderDlg.m_id);
	}

	void onExtendedOrder() {
		// Show the extended order attributes dialog
		m_extOrdDlg.setVisible(true);
		if (!m_extOrdDlg.m_rc) {
			return;
		}
		// Copy over the extended order details
		copyExtendedOrderDetails(m_orderDlg.m_order, m_extOrdDlg.m_order);
	}

	void onReqAcctData() {
		AcctUpdatesDlg dlg = new AcctUpdatesDlg(this);
		dlg.setVisible(true);
		if (dlg.m_subscribe) {
			m_acctDlg.accountDownloadBegin(dlg.m_acctCode);
		}
		m_client.reqAccountUpdates(dlg.m_subscribe, dlg.m_acctCode);
		if (m_client.isConnected() && dlg.m_subscribe) {
			m_acctDlg.reset();
			m_acctDlg.setVisible(true);
		}
	}

	void onFinancialAdvisor() {
		faGroupXML = faProfilesXML = faAliasesXML = null;
		faError = false;
		m_client.requestFA(EClientSocket.GROUPS);
		m_client.requestFA(EClientSocket.PROFILES);
		m_client.requestFA(EClientSocket.ALIASES);
	}

	void onServerLogging() {
		// get server logging level
		LogConfigDlg dlg = new LogConfigDlg(this);
		dlg.setVisible(true);
		if (!dlg.m_rc) {
			return;
		}

		// connect to TWS
		m_client.setServerLogLevel(dlg.m_serverLogLevel);
	}

	void onReqAllOpenOrders() {
		// request list of all open orders
		m_client.reqAllOpenOrders();
	}

	void onReqAutoOpenOrders() {
		// request to automatically bind any newly entered TWS orders
		// to this API client. NOTE: TWS orders can only be bound to
		// client's with clientId=0.
		m_client.reqAutoOpenOrders(true);
	}

	void onReqManagedAccts() {
		// request the list of managed accounts
		m_client.reqManagedAccts();
	}

	void onClear() {
		m_tickers.clear();
		m_TWS.clear();
		m_errors.clear();
	}

	void onClose() {
		System.exit(1);
	}

	void onReqExecutions() {
		ExecFilterDlg dlg = new ExecFilterDlg(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// request execution reports based on the supplied filter criteria
			m_client.reqExecutions(dlg.m_reqId, dlg.m_execFilter);
		}
	}

	void onReqNewsBulletins() {
		// run m_newsBulletinDlg
		m_newsBulletinDlg.setVisible(true);
		if (!m_newsBulletinDlg.m_rc) {
			return;
		}

		if (m_newsBulletinDlg.m_subscribe) {
			m_client.reqNewsBulletins(m_newsBulletinDlg.m_allMsgs);
		} else {
			m_client.cancelNewsBulletins();
		}
	}

	void onCalculateImpliedVolatility() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.calculateImpliedVolatility(m_orderDlg.m_id, m_orderDlg.m_contract, m_orderDlg.m_order.lmtPrice(),
				m_orderDlg.m_order.auxPrice());
	}

	void onCancelCalculateImpliedVolatility() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.cancelCalculateImpliedVolatility(m_orderDlg.m_id);
	}

	void onCalculateOptionPrice() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.calculateOptionPrice(m_orderDlg.m_id, m_orderDlg.m_contract, m_orderDlg.m_order.lmtPrice(),
				m_orderDlg.m_order.auxPrice());
	}

	void onCancelCalculateOptionPrice() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		m_client.cancelCalculateOptionPrice(m_orderDlg.m_id);
	}

	void onGlobalCancel() {
		m_client.reqGlobalCancel();
	}

	void onReqMarketDataType() {
		// run m_orderDlg
		m_orderDlg.init("Options", false);
		m_orderDlg.show();
		if (!m_orderDlg.m_rc) {
			return;
		}
		// req mkt data type
		m_client.reqMarketDataType(m_orderDlg.m_marketDataType);
	}

	void onRequestPositions() {
		m_client.reqPositions();
	}

	void onCancelPositions() {
		m_client.cancelPositions();
	}

	void onRequestAccountSummary() {
		AccountSummary dlg = new AccountSummary(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// request account summary
			m_client.reqAccountSummary(dlg.m_reqId, dlg.m_groupName, dlg.m_tags);
		}
	}

	void onCancelAccountSummary() {
		AccountSummary dlg = new AccountSummary(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// cancel account summary
			m_client.cancelAccountSummary(dlg.m_reqId);
		}
	}

	void onRequestPositionsMulti() {
		PositionsDlg dlg = new PositionsDlg(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// request positions multi
			m_client.reqPositionsMulti(dlg.m_retId, dlg.m_retAccount, dlg.m_retModelCode);
		}
	}

	void onCancelPositionsMulti() {
		PositionsDlg dlg = new PositionsDlg(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// cancel positions multi
			m_client.cancelPositionsMulti(dlg.m_retId);
		}
	}

	void onRequestAccountUpdatesMulti() {
		PositionsDlg dlg = new PositionsDlg(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// request account updates multi
			m_client.reqAccountUpdatesMulti(dlg.m_retId, dlg.m_retAccount, dlg.m_retModelCode, dlg.m_retLedgerAndNLV);
		}
	}

	void onCancelAccountUpdatesMulti() {
		PositionsDlg dlg = new PositionsDlg(this);
		dlg.setVisible(true);
		if (dlg.m_rc) {
			// cancel account updates multi
			m_client.cancelAccountUpdatesMulti(dlg.m_retId);
		}
	}

	void onGroups() {
		m_groupsDlg.setVisible(true);
//        if ( dlg.m_rc ) {

//        }
	}

	public void tickPrice(int tickerId, int field, double price, int canAutoExecute) {
		// received price tick
		String msg = EWrapperMsgGenerator.tickPrice(tickerId, field, price, canAutoExecute);
		m_tickers.add(msg);
	}

	public void tickOptionComputation(int tickerId, int field, double impliedVol, double delta, double optPrice,
			double pvDividend, double gamma, double vega, double theta, double undPrice) {
		// received computation tick
		String msg = EWrapperMsgGenerator.tickOptionComputation(tickerId, field, impliedVol, delta, optPrice,
				pvDividend, gamma, vega, theta, undPrice);
		m_tickers.add(msg);
	}

	public void tickSize(int tickerId, int field, int size) {
		// received size tick
		String msg = EWrapperMsgGenerator.tickSize(tickerId, field, size);
		m_tickers.add(msg);
	}

	public void tickGeneric(int tickerId, int tickType, double value) {
		// received generic tick
		String msg = EWrapperMsgGenerator.tickGeneric(tickerId, tickType, value);
		m_tickers.add(msg);
	}

	public void tickString(int tickerId, int tickType, String value) {
		// received String tick
		String msg = EWrapperMsgGenerator.tickString(tickerId, tickType, value);
		m_tickers.add(msg);
	}

	public void tickSnapshotEnd(int tickerId) {
		String msg = EWrapperMsgGenerator.tickSnapshotEnd(tickerId);
		m_tickers.add(msg);
	}

	public void tickEFP(int tickerId, int tickType, double basisPoints, String formattedBasisPoints,
			double impliedFuture, int holdDays, String futureLastTradeDate, double dividendImpact,
			double dividendsToLastTradeDate) {
		// received EFP tick
		String msg = EWrapperMsgGenerator.tickEFP(tickerId, tickType, basisPoints, formattedBasisPoints, impliedFuture,
				holdDays, futureLastTradeDate, dividendImpact, dividendsToLastTradeDate);
		m_tickers.add(msg);
	}

	public void orderStatus(int orderId, String status, double filled, double remaining, double avgFillPrice,
			int permId, int parentId, double lastFillPrice, int clientId, String whyHeld) {
		// received order status
		String msg = EWrapperMsgGenerator.orderStatus(orderId, status, filled, remaining, avgFillPrice, permId,
				parentId, lastFillPrice, clientId, whyHeld);
		m_TWS.add(msg);

		// make sure id for next order is at least orderId+1
		m_orderDlg.setIdAtLeast(orderId + 1);
	}

	public void openOrder(int orderId, Contract contract, Order order, OrderState orderState) {
		// received open order
		String msg = EWrapperMsgGenerator.openOrder(orderId, contract, order, orderState);
		m_TWS.add(msg);
	}

	public void openOrderEnd() {
		// received open order end
		String msg = EWrapperMsgGenerator.openOrderEnd();
		m_TWS.add(msg);
	}

	public void contractDetails(int reqId, ContractDetails contractDetails) {
		if (m_callbackMap.containsKey(reqId)) {
			m_callbackMap.get(reqId).onContractDetails(contractDetails);
		}

		String msg = EWrapperMsgGenerator.contractDetails(reqId, contractDetails);
		m_TWS.add(msg);
	}

	public void contractDetailsEnd(int reqId) {
		if (m_callbackMap.containsKey(reqId)) {
			m_callbackMap.get(reqId).onContractDetailsEnd();
		}

		String msg = EWrapperMsgGenerator.contractDetailsEnd(reqId);
		m_TWS.add(msg);
	}

	public void scannerData(int reqId, int rank, ContractDetails contractDetails, String distance, String benchmark,
			String projection, String legsStr) {
		String msg = EWrapperMsgGenerator.scannerData(reqId, rank, contractDetails, distance, benchmark, projection,
				legsStr);
		m_tickers.add(msg);
	}

	public void scannerDataEnd(int reqId) {
		String msg = EWrapperMsgGenerator.scannerDataEnd(reqId);
		m_tickers.add(msg);
	}

	public void bondContractDetails(int reqId, ContractDetails contractDetails) {
		String msg = EWrapperMsgGenerator.bondContractDetails(reqId, contractDetails);
		m_TWS.add(msg);
	}

	public void execDetails(int reqId, Contract contract, Execution execution) {
		String msg = EWrapperMsgGenerator.execDetails(reqId, contract, execution);
		m_TWS.add(msg);
	}

	public void execDetailsEnd(int reqId) {
		String msg = EWrapperMsgGenerator.execDetailsEnd(reqId);
		m_TWS.add(msg);
	}

	public void updateMktDepth(int tickerId, int position, int operation, int side, double price, int size) {

		MktDepthDlg depthDialog = m_mapRequestToMktDepthDlg.get(tickerId);
		if (depthDialog != null) {
			depthDialog.updateMktDepth(tickerId, position, "", operation, side, price, size);
		} else {
			System.err.println("cannot find dialog that corresponds to request id [" + tickerId + "]");
		}

	}

	public void updateMktDepthL2(int tickerId, int position, String marketMaker, int operation, int side, double price,
			int size) {
		MktDepthDlg depthDialog = m_mapRequestToMktDepthDlg.get(tickerId);
		if (depthDialog != null) {
			depthDialog.updateMktDepth(tickerId, position, marketMaker, operation, side, price, size);
		} else {
			System.err.println("cannot find dialog that corresponds to request id [" + tickerId + "]");
		}
	}

	public void nextValidId(int orderId) {
		// received next valid order id
		String msg = EWrapperMsgGenerator.nextValidId(orderId);
		m_TWS.add(msg);
		m_orderDlg.setIdAtLeast(orderId);
	}

	public void error(Exception ex) {
		// do not report exceptions if we initiated disconnect
		if (!m_disconnectInProgress) {
			String msg = EWrapperMsgGenerator.error(ex);
			Main.inform(this, msg);
		}
	}

	public void error(String str) {
		String msg = EWrapperMsgGenerator.error(str);
		m_errors.add(msg);
	}

	public void error(int id, int errorCode, String errorMsg) {
		// received error
		if (m_callbackMap.containsKey(id)) {
			m_callbackMap.get(id).onError(errorCode, errorMsg);
		} else if (id == -1) {
			for (ContractDetailsCallback callback : m_callbackMap.values()) {
				callback.onError(errorCode, errorMsg);
			}
		}

		String msg = EWrapperMsgGenerator.error(id, errorCode, errorMsg);
		m_errors.add(msg);
		for (int ctr = 0; ctr < faErrorCodes.length; ctr++) {
			faError |= (errorCode == faErrorCodes[ctr]);
		}
		if (errorCode == MktDepthDlg.MKT_DEPTH_DATA_RESET) {

			MktDepthDlg depthDialog = m_mapRequestToMktDepthDlg.get(id);
			if (depthDialog != null) {
				depthDialog.reset();
			} else {
				System.err.println("cannot find dialog that corresponds to request id [" + id + "]");
			}
		}
	}

	public void connectionClosed() {
		String msg = EWrapperMsgGenerator.connectionClosed();
		Main.inform(this, msg);
	}

	public void updateAccountValue(String key, String value, String currency, String accountName) {
		m_acctDlg.updateAccountValue(key, value, currency, accountName);
	}

	public void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
			double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
		m_acctDlg.updatePortfolio(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL,
				accountName);
	}

	public void updateAccountTime(String timeStamp) {
		m_acctDlg.updateAccountTime(timeStamp);
	}

	public void accountDownloadEnd(String accountName) {
		m_acctDlg.accountDownloadEnd(accountName);

		String msg = EWrapperMsgGenerator.accountDownloadEnd(accountName);
		m_TWS.add(msg);
	}

	public void updateNewsBulletin(int msgId, int msgType, String message, String origExchange) {
		String msg = EWrapperMsgGenerator.updateNewsBulletin(msgId, msgType, message, origExchange);
		JOptionPane.showMessageDialog(this, msg, "IB News Bulletin", JOptionPane.INFORMATION_MESSAGE);
	}

	public void managedAccounts(String accountsList) {
		m_bIsFAAccount = true;
		m_FAAcctCodes = accountsList;
		String msg = EWrapperMsgGenerator.managedAccounts(accountsList);
		m_TWS.add(msg);
	}

	public void historicalData(int reqId, String date, double open, double high, double low, double close, int volume,
			int count, double WAP, boolean hasGaps) {
		String msg = EWrapperMsgGenerator.historicalData(reqId, date, open, high, low, close, volume, count, WAP,
				hasGaps);
		m_tickers.add(msg);
	}

	public void realtimeBar(int reqId, long time, double open, double high, double low, double close, long volume,
			double wap, int count) {
		String msg = EWrapperMsgGenerator.realtimeBar(reqId, time, open, high, low, close, volume, wap, count);
		m_tickers.add(msg);
	}

	public void scannerParameters(String xml) {
		displayXML(EWrapperMsgGenerator.SCANNER_PARAMETERS, xml);
	}

	public void currentTime(long time) {
		String msg = EWrapperMsgGenerator.currentTime(time);
		m_TWS.add(msg);
	}

	public void fundamentalData(int reqId, String data) {
		String msg = EWrapperMsgGenerator.fundamentalData(reqId, data);
		m_tickers.add(msg);
	}

	public void deltaNeutralValidation(int reqId, DeltaNeutralContract underComp) {
		String msg = EWrapperMsgGenerator.deltaNeutralValidation(reqId, underComp);
		m_TWS.add(msg);
	}

	void displayXML(String title, String xml) {
		m_TWS.add(title);
		m_TWS.addText(xml);
	}

	public void receiveFA(int faDataType, String xml) {
		displayXML(EWrapperMsgGenerator.FINANCIAL_ADVISOR + " " + EClientSocket.faMsgTypeName(faDataType), xml);
		switch (faDataType) {
		case EClientSocket.GROUPS:
			faGroupXML = xml;
			break;
		case EClientSocket.PROFILES:
			faProfilesXML = xml;
			break;
		case EClientSocket.ALIASES:
			faAliasesXML = xml;
			break;
		}

		if (!faError && !(faGroupXML == null || faProfilesXML == null || faAliasesXML == null)) {
			FinancialAdvisorDlg dlg = new FinancialAdvisorDlg(this);
			dlg.receiveInitialXML(faGroupXML, faProfilesXML, faAliasesXML);
			dlg.setVisible(true);

			if (!dlg.m_rc) {
				return;
			}

			m_client.replaceFA(EClientSocket.GROUPS, dlg.groupsXML);
			m_client.replaceFA(EClientSocket.PROFILES, dlg.profilesXML);
			m_client.replaceFA(EClientSocket.ALIASES, dlg.aliasesXML);

		}
	}

	public void marketDataType(int reqId, int marketDataType) {
		String msg = EWrapperMsgGenerator.marketDataType(reqId, marketDataType);
		m_tickers.add(msg);
	}

	public void commissionReport(CommissionReport commissionReport) {
		String msg = EWrapperMsgGenerator.commissionReport(commissionReport);
		m_TWS.add(msg);
	}

	private static void copyExtendedOrderDetails(Order destOrder, Order srcOrder) {
		destOrder.tif(srcOrder.getTif());
		destOrder.activeStartTime(srcOrder.activeStartTime());
		destOrder.activeStopTime(srcOrder.activeStopTime());
		destOrder.ocaGroup(srcOrder.ocaGroup());
		destOrder.ocaType(srcOrder.getOcaType());
		destOrder.openClose(srcOrder.openClose());
		destOrder.origin(srcOrder.origin());
		destOrder.orderRef(srcOrder.orderRef());
		destOrder.transmit(srcOrder.transmit());
		destOrder.parentId(srcOrder.parentId());
		destOrder.blockOrder(srcOrder.blockOrder());
		destOrder.sweepToFill(srcOrder.sweepToFill());
		destOrder.displaySize(srcOrder.displaySize());
		destOrder.triggerMethod(srcOrder.getTriggerMethod());
		destOrder.outsideRth(srcOrder.outsideRth());
		destOrder.hidden(srcOrder.hidden());
		destOrder.discretionaryAmt(srcOrder.discretionaryAmt());
		destOrder.goodAfterTime(srcOrder.goodAfterTime());
		destOrder.shortSaleSlot(srcOrder.shortSaleSlot());
		destOrder.designatedLocation(srcOrder.designatedLocation());
		destOrder.exemptCode(srcOrder.exemptCode());
		destOrder.ocaType(srcOrder.getOcaType());
		destOrder.rule80A(srcOrder.getRule80A());
		destOrder.allOrNone(srcOrder.allOrNone());
		destOrder.minQty(srcOrder.minQty());
		destOrder.percentOffset(srcOrder.percentOffset());
		destOrder.eTradeOnly(srcOrder.eTradeOnly());
		destOrder.firmQuoteOnly(srcOrder.firmQuoteOnly());
		destOrder.nbboPriceCap(srcOrder.nbboPriceCap());
		destOrder.optOutSmartRouting(srcOrder.optOutSmartRouting());
		destOrder.auctionStrategy(srcOrder.auctionStrategy());
		destOrder.startingPrice(srcOrder.startingPrice());
		destOrder.stockRefPrice(srcOrder.stockRefPrice());
		destOrder.delta(srcOrder.delta());
		destOrder.stockRangeLower(srcOrder.stockRangeLower());
		destOrder.stockRangeUpper(srcOrder.stockRangeUpper());
		destOrder.overridePercentageConstraints(srcOrder.overridePercentageConstraints());
		destOrder.volatility(srcOrder.volatility());
		destOrder.volatilityType(srcOrder.getVolatilityType());
		destOrder.deltaNeutralOrderType(srcOrder.getDeltaNeutralOrderType());
		destOrder.deltaNeutralAuxPrice(srcOrder.deltaNeutralAuxPrice());
		destOrder.deltaNeutralConId(srcOrder.deltaNeutralConId());
		destOrder.deltaNeutralSettlingFirm(srcOrder.deltaNeutralSettlingFirm());
		destOrder.deltaNeutralClearingAccount(srcOrder.deltaNeutralClearingAccount());
		destOrder.deltaNeutralClearingIntent(srcOrder.deltaNeutralClearingIntent());
		destOrder.deltaNeutralOpenClose(srcOrder.deltaNeutralOpenClose());
		destOrder.deltaNeutralShortSale(srcOrder.deltaNeutralShortSale());
		destOrder.deltaNeutralShortSaleSlot(srcOrder.deltaNeutralShortSaleSlot());
		destOrder.deltaNeutralDesignatedLocation(srcOrder.deltaNeutralDesignatedLocation());
		destOrder.continuousUpdate(srcOrder.continuousUpdate());
		destOrder.referencePriceType(srcOrder.getReferencePriceType());
		destOrder.trailStopPrice(srcOrder.trailStopPrice());
		destOrder.trailingPercent(srcOrder.trailingPercent());
		destOrder.scaleInitLevelSize(srcOrder.scaleInitLevelSize());
		destOrder.scaleSubsLevelSize(srcOrder.scaleSubsLevelSize());
		destOrder.scalePriceIncrement(srcOrder.scalePriceIncrement());
		destOrder.scalePriceAdjustValue(srcOrder.scalePriceAdjustValue());
		destOrder.scalePriceAdjustInterval(srcOrder.scalePriceAdjustInterval());
		destOrder.scaleProfitOffset(srcOrder.scaleProfitOffset());
		destOrder.scaleAutoReset(srcOrder.scaleAutoReset());
		destOrder.scaleInitPosition(srcOrder.scaleInitPosition());
		destOrder.scaleInitFillQty(srcOrder.scaleInitFillQty());
		destOrder.scaleRandomPercent(srcOrder.scaleRandomPercent());
		destOrder.scaleTable(srcOrder.scaleTable());
		destOrder.hedgeType(srcOrder.getHedgeType());
		destOrder.hedgeParam(srcOrder.hedgeParam());
		destOrder.account(srcOrder.account());
		destOrder.modelCode(srcOrder.modelCode());
		destOrder.settlingFirm(srcOrder.settlingFirm());
		destOrder.clearingAccount(srcOrder.clearingAccount());
		destOrder.clearingIntent(srcOrder.clearingIntent());
		destOrder.solicited(srcOrder.solicited());
		destOrder.randomizePrice(srcOrder.randomizePrice());
		destOrder.randomizeSize(srcOrder.randomizeSize());
	}

	public void position(String account, Contract contract, double pos, double avgCost) {
		String msg = EWrapperMsgGenerator.position(account, contract, pos, avgCost);
		m_TWS.add(msg);
	}

	public void positionEnd() {
		String msg = EWrapperMsgGenerator.positionEnd();
		m_TWS.add(msg);
	}

	public void accountSummary(int reqId, String account, String tag, String value, String currency) {
		String msg = EWrapperMsgGenerator.accountSummary(reqId, account, tag, value, currency);
		m_TWS.add(msg);
	}

	public void accountSummaryEnd(int reqId) {
		String msg = EWrapperMsgGenerator.accountSummaryEnd(reqId);
		m_TWS.add(msg);
	}

	public void positionMulti(int reqId, String account, String modelCode, Contract contract, double pos,
			double avgCost) {
		String msg = EWrapperMsgGenerator.positionMulti(reqId, account, modelCode, contract, pos, avgCost);
		m_TWS.add(msg);
	}

	public void positionMultiEnd(int reqId) {
		String msg = EWrapperMsgGenerator.positionMultiEnd(reqId);
		m_TWS.add(msg);
	}

	public void accountUpdateMulti(int reqId, String account, String modelCode, String key, String value,
			String currency) {
		String msg = EWrapperMsgGenerator.accountUpdateMulti(reqId, account, modelCode, key, value, currency);
		m_TWS.add(msg);
	}

	public void accountUpdateMultiEnd(int reqId) {
		String msg = EWrapperMsgGenerator.accountUpdateMultiEnd(reqId);
		m_TWS.add(msg);
	}

	public void verifyMessageAPI(String apiData) {
		/* Empty */ }

	public void verifyCompleted(boolean isSuccessful, String errorText) {
		/* Empty */ }

	public void verifyAndAuthMessageAPI(String apiData, String xyzChallenge) {
		/* Empty */ }

	public void verifyAndAuthCompleted(boolean isSuccessful, String errorText) {
		/* Empty */ }

	public void displayGroupList(int reqId, String groups) {
		m_groupsDlg.displayGroupList(reqId, groups);
	}

	public void displayGroupUpdated(int reqId, String contractInfo) {
		m_groupsDlg.displayGroupUpdated(reqId, contractInfo);
	}

	public void connectAck() {
		if (m_client.isAsyncEConnect())
			m_client.startAPI();
	}

	@Override
	public void securityDefinitionOptionalParameter(int reqId, String exchange, int underlyingConId,
			String tradingClass, String multiplier, Set<String> expirations, Set<Double> strikes) {
		String msg = EWrapperMsgGenerator.securityDefinitionOptionalParameter(reqId, exchange, underlyingConId,
				tradingClass, multiplier, expirations, strikes);
		m_TWS.add(msg);
	}

	@Override
	public void securityDefinitionOptionalParameterEnd(int reqId) {
	}

	@Override
	public void softDollarTiers(int reqId, SoftDollarTier[] tiers) {
	}

}
