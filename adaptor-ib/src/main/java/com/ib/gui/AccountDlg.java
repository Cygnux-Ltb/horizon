/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.gui;

import com.ib.client.Contract;
import com.ib.client.Util;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Vector;

public class AccountDlg extends JDialog {
    private final JTextField m_updateTime = new JTextField();
    private final PortfolioTable m_portfolioModel = new PortfolioTable();
    private final AcctValueModel m_acctValueModel = new AcctValueModel();
    private boolean m_rc;

    private String m_accountName;
    private boolean m_complete;

    boolean rc() {
        return m_rc;
    }

    AccountDlg(JFrame parent) {
        super(parent, true);

        JScrollPane acctPane = new JScrollPane(new JTable(m_acctValueModel));
        JScrollPane portPane = new JScrollPane(new JTable(m_portfolioModel));

        acctPane.setBorder(BorderFactory.createTitledBorder("Key, Value, Currency, and Account"));
        portPane.setBorder(BorderFactory.createTitledBorder("Portfolio Entries"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, acctPane, portPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(240);

        splitPane.setPreferredSize(new Dimension(600, 350));

        JPanel timePanel = new JPanel();
        timePanel.add(new JLabel("Update time:"));
        timePanel.add(m_updateTime);
        JButton m_close = new JButton("Close");
        timePanel.add(m_close);

        m_updateTime.setEditable(false);
        m_updateTime.setHorizontalAlignment(SwingConstants.CENTER);
        m_updateTime.setPreferredSize(new Dimension(80, 26));
        m_close.addActionListener(e -> onClose());

        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(timePanel, BorderLayout.SOUTH);
        setLocation(20, 20);
        pack();
        reset();
    }

    void updateAccountValue(String key, String value, String currency, String accountName) {
        m_acctValueModel.updateAccountValue(key, value, currency, accountName);
    }

    void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
                         double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
        m_portfolioModel.updatePortfolio(contract, position, marketPrice, marketValue,
                averageCost, unrealizedPNL, realizedPNL, accountName);
    }

    void reset() {
        m_acctValueModel.reset();
        m_portfolioModel.reset();
        m_updateTime.setText("");
    }

    void onClose() {
        setVisible(false);
    }

    void updateAccountTime(String timeStamp) {
        m_updateTime.setText(timeStamp);
    }

    void accountDownloadBegin(String accountName) {
        m_accountName = accountName;
        m_complete = false;

        updateTitle();
    }


    void accountDownloadEnd(String accountName) {

        if (!Util.StringIsEmpty(m_accountName) &&
                !m_accountName.equals(accountName)) {
            return;
        }

        m_complete = true;
        updateTitle();
    }

    private void updateTitle() {
        final StringBuilder sb = new StringBuilder();
        if (!Util.StringIsEmpty(m_accountName)) {
            sb.append(m_accountName);
        }
        if (m_complete) {
            if (sb.length() != 0) {
                sb.append(' ');
            }
            sb.append("[complete]");
        }
        setTitle(sb.toString());
    }
}


class PortfolioTable extends AbstractTableModel {
    private final Vector<PortfolioTableRow> m_allData = new Vector<>();

    void updatePortfolio(Contract contract, double position, double marketPrice, double marketValue,
                         double averageCost, double unrealizedPNL, double realizedPNL, String accountName) {
        PortfolioTableRow newData =
                new PortfolioTableRow(contract, position, marketPrice, marketValue, averageCost, unrealizedPNL, realizedPNL, accountName);
        int size = m_allData.size();
        for (int i = 0; i < size; i++) {
            PortfolioTableRow test = m_allData.get(i);
            if (test.m_contract.equals(newData.m_contract)) {
                if (newData.m_position == 0)
                    m_allData.remove(i);
                else
                    m_allData.set(i, newData);

                fireTableDataChanged();
                return;
            }
        }

        m_allData.add(newData);
        fireTableDataChanged();
    }

    void reset() {
        m_allData.clear();
    }

    public int getRowCount() {
        return m_allData.size();
    }

    public int getColumnCount() {
        return 18;
    }

    public Object getValueAt(int r, int c) {
        return m_allData.get(r).getValue(c);
    }

    public boolean isCellEditable(int r, int c) {
        return false;
    }

    public String getColumnName(int c) {
        return switch (c) {
            case 0 -> "ConId";
            case 1 -> "Symbol";
            case 2 -> "SecType";
            case 3 -> "Last trade date";
            case 4 -> "Strike";
            case 5 -> "Right";
            case 6 -> "Multiplier";
            case 7 -> "Exchange";
            case 8 -> "Currency";
            case 9 -> "Local Symbol";
            case 10 -> "Trading Class";
            case 11 -> "Position";
            case 12 -> "Market Price";
            case 13 -> "Market Value";
            case 14 -> "Average Cost";
            case 15 -> "Unrealized P&L";
            case 16 -> "Realized P&L";
            case 17 -> "Account Name";
            default -> null;
        };
    }

    static class PortfolioTableRow {
        Contract m_contract;
        double m_position;
        double m_marketPrice;
        double m_marketValue;
        double m_averageCost;
        double m_unrealizedPNL;
        double m_realizedPNL;
        String m_accountName;

        PortfolioTableRow(Contract contract, double position, double marketPrice,
                          double marketValue, double averageCost, double unrealizedPNL,
                          double realizedPNL, String accountName) {
            m_contract = contract;
            m_position = position;
            m_marketPrice = marketPrice;
            m_marketValue = marketValue;
            m_averageCost = averageCost;
            m_unrealizedPNL = unrealizedPNL;
            m_realizedPNL = realizedPNL;
            m_accountName = accountName;
        }

        Object getValue(int c) {
            return switch (c) {
                case 0 -> m_contract.conid();
                case 1 -> m_contract.symbol();
                case 2 -> m_contract.secType();
                case 3 -> m_contract.lastTradeDateOrContractMonth();
                case 4 -> m_contract.lastTradeDateOrContractMonth() == null ? null : "" + m_contract.strike();
                case 5 -> (m_contract.getRight() != null && m_contract.getRight().equals("???"))
                        ? null : m_contract.getRight();
                case 6 -> m_contract.multiplier();
                case 7 -> (m_contract.primaryExch() != null ? m_contract.primaryExch() : "");
                case 8 -> m_contract.currency();
                case 9 -> (m_contract.localSymbol() != null ? m_contract.localSymbol() : "");
                case 10 -> (m_contract.tradingClass() != null ? m_contract.tradingClass() : "");
                case 11 -> "" + m_position;
                case 12 -> "" + m_marketPrice;
                case 13 -> "" + m_marketValue;
                case 14 -> "" + m_averageCost;
                case 15 -> "" + m_unrealizedPNL;
                case 16 -> "" + m_realizedPNL;
                case 17 -> m_accountName;
                default -> null;
            };
        }
    }
}

class AcctValueModel extends AbstractTableModel {
    private final Vector<AccountTableRow> m_allData = new Vector<>();

    void updateAccountValue(String key, String val, String currency, String accountName) {
        AccountTableRow newData = new AccountTableRow(key, val, currency, accountName);
        for (AccountTableRow test : m_allData) {
            if (test.m_key != null &&
                    test.m_key.equals(newData.m_key) &&
                    test.m_currency != null &&
                    test.m_currency.equals(newData.m_currency)) {
                test.m_value = newData.m_value;
                fireTableDataChanged();
                return;
            }
        }

        m_allData.add(newData);
        fireTableDataChanged();
    }

    void reset() {
        m_allData.clear();
    }

    public int getRowCount() {
        return m_allData.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public Object getValueAt(int r, int c) {
        return m_allData.get(r).getValue(c);
    }


    public boolean isCellEditable(int r, int c) {
        return false;
    }

    public String getColumnName(int c) {
        return switch (c) {
            case 0 -> "Key";
            case 1 -> "Value";
            case 2 -> "Currency";
            case 3 -> "Account Name";
            default -> null;
        };
    }

    static class AccountTableRow {
        String m_key;
        String m_value;
        String m_currency;
        String m_accountName;

        AccountTableRow(String key, String val, String cur, String accountName) {
            m_key = key;
            m_value = val;
            m_currency = cur;
            m_accountName = accountName;
        }

        Object getValue(int c) {
            return switch (c) {
                case 0 -> m_key;
                case 1 -> m_value;
                case 2 -> m_currency;
                case 3 -> m_accountName;
                default -> null;
            };
        }
    }
}
