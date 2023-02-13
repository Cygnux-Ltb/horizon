/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.ib.client.ComboLeg;
import com.ib.client.OrderComboLeg;

public class ComboLegDlg extends JDialog {

    @Serial
    private static final long serialVersionUID = 8942532667888916031L;
    //private static String 	BUY = "BUY";
    //private static String 	SELL = "SELL";
    //private static String 	SSHORT = "SSHORT";

    private final List<ComboLeg> m_comboLegs;
    private final List<OrderComboLeg> m_orderComboLegs;

    private final JTextField m_conId = new JTextField("0");
    private final JTextField m_ratio = new JTextField("0");
    private final JTextField m_action = new JTextField("BUY");
    private final JTextField m_exchange = new JTextField("");
    private final JTextField m_openClose = new JTextField("0");
    private final JTextField m_shortSaleSlot = new JTextField("0");
    private final JTextField m_designatedLocation = new JTextField("");
    private final JTextField m_exemptCode = new JTextField("-1");
    private final JTextField m_price = new JTextField("");

    private final ComboLegModel m_comboLegsModel = new ComboLegModel();
    private final JTable m_comboTable = new JTable(m_comboLegsModel);

    public ComboLegModel comboLegModel() {
        return m_comboLegsModel;
    }

    ComboLegDlg(List<ComboLeg> comboLegs, List<OrderComboLeg> orderComboLegs, String orderExchange, JDialog owner) {
        super(owner, true);

        m_comboLegs = comboLegs;
        m_orderComboLegs = orderComboLegs;

        setTitle("Combination Legs");

        // create combos list panel
        JPanel pLegList = new JPanel(new GridLayout(0, 1, 10, 10));
        pLegList.setBorder(BorderFactory.createTitledBorder("Combination Order legs:"));
        m_comboLegsModel.comboLegData().addAll(comboLegs);
        m_comboLegsModel.orderComboLegData().addAll(orderComboLegs);
        pLegList.add(new JScrollPane(m_comboTable));

        if (orderExchange != null && orderExchange.length() > 0) {
            m_exchange.setText(orderExchange);
        }

        // create combo details panel
        JPanel pComboDetails = new JPanel(new GridLayout(0, 2, 10, 10));
        pComboDetails.setBorder(BorderFactory.createTitledBorder("Combo Leg Details:"));
        pComboDetails.add(new JLabel("ConId:"));
        pComboDetails.add(m_conId);
        pComboDetails.add(new JLabel("Ratio:"));
        pComboDetails.add(m_ratio);
        pComboDetails.add(new JLabel("Side:"));
        pComboDetails.add(m_action);
        pComboDetails.add(new JLabel("Exchange:"));
        pComboDetails.add(m_exchange);
        pComboDetails.add(new JLabel("Open/Close:"));
        pComboDetails.add(m_openClose);
        pComboDetails.add(new JLabel("Short Sale Slot:"));
        pComboDetails.add(m_shortSaleSlot);
        pComboDetails.add(new JLabel("Designated Location:"));
        pComboDetails.add(m_designatedLocation);
        pComboDetails.add(new JLabel("Exempt Code:"));
        pComboDetails.add(m_exemptCode);
        pComboDetails.add(new JLabel("Price:"));
        pComboDetails.add(m_price);
        JButton btnAddLeg = new JButton("Add");
        pComboDetails.add(btnAddLeg);
        JButton btnRemoveLeg = new JButton("Remove");
        pComboDetails.add(btnRemoveLeg);

        // create button panel
        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("OK");
        buttonPanel.add(btnOk);
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnCancel);

        // create wrapper panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(pLegList);
        topPanel.add(pComboDetails);

        // create dlg box
        getContentPane().add(topPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // create action listeners
        btnAddLeg.addActionListener(e -> onAddLeg());
        btnRemoveLeg.addActionListener(e -> onRemoveLeg());
        btnOk.addActionListener(e -> onOk());
        btnCancel.addActionListener(e -> onCancel());

        setSize(250, 600);
        centerOnOwner(this);
    }

    void onAddLeg() {
        try {
            int conId = Integer.parseInt(m_conId.getText());
            int ratio = Integer.parseInt(m_ratio.getText());
            int openClose = Integer.parseInt(m_openClose.getText());
            int shortSaleSlot = Integer.parseInt(m_shortSaleSlot.getText());
            int exemptCode = Integer.parseInt(m_exemptCode.getText().length() != 0 ? m_exemptCode.getText() : "-1");
            double price = parseStringToMaxDouble(m_price.getText());
            m_comboLegsModel.addComboLeg(new ComboLeg(conId, ratio,
                            m_action.getText(), m_exchange.getText(), openClose,
                            shortSaleSlot, m_designatedLocation.getText(), exemptCode),
                    new OrderComboLeg(price));
        } catch (Exception e) {
            reportError("Error - ", e);
        }
    }

    void onRemoveLeg() {
        try {
            if (m_comboTable.getSelectedRowCount() != 0) {
                int[] rows = m_comboTable.getSelectedRows();
                for (int i = rows.length - 1; i >= 0; i--) {
                    m_comboLegsModel.removeComboLeg(rows[i]);
                }
            }
        } catch (Exception e) {
            reportError("Error - ", e);
        }
    }

    void onOk() {
        m_comboLegs.clear();
        m_comboLegs.addAll(m_comboLegsModel.comboLegData());
        m_orderComboLegs.clear();
        m_orderComboLegs.addAll(m_comboLegsModel.orderComboLegData());
        setVisible(false);
    }

    void onCancel() {
        setVisible(false);
    }

    void reportError(String msg, Exception e) {
        Main.inform(this, msg + " --" + e);
    }

    private static void centerOnOwner(Window window) {
        Window owner = window.getOwner();
        if (owner == null) {
            return;
        }
        int x = owner.getX() + ((owner.getWidth() - window.getWidth()) / 2);
        int y = owner.getY() + ((owner.getHeight() - window.getHeight()) / 2);
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        window.setLocation(x, y);
    }

    private static double parseStringToMaxDouble(String value) {
        if (value.trim().length() == 0) {
            return Double.MAX_VALUE;
        }
        return Double.parseDouble(value);
    }
}

class ComboLegModel extends AbstractTableModel {
    @Serial
    private static final long serialVersionUID = 1674215810294964757L;
    private final List<ComboLeg> m_comboLegData = new ArrayList<>();
    private final List<OrderComboLeg> m_orderComboLegData = new ArrayList<>();

    synchronized void addComboLeg(ComboLeg comboLeg, OrderComboLeg orderComboLeg) {
        m_comboLegData.add(comboLeg);
        m_orderComboLegData.add(orderComboLeg);
        fireTableDataChanged();
    }

    synchronized void removeComboLeg(int index) {
        m_comboLegData.remove(index);
        m_orderComboLegData.remove(index);
        fireTableDataChanged();
    }

    synchronized public void removeComboLeg(ComboLeg comboLeg) {
        for (int i = 0; i < m_comboLegData.size(); i++) {
            if (comboLeg.equals(m_comboLegData.get(i))) {
                m_comboLegData.remove(i);
                m_orderComboLegData.remove(i);
                break;
            }
        }
        fireTableDataChanged();
    }

    synchronized public void reset() {
        m_comboLegData.clear();
        m_orderComboLegData.clear();
        fireTableDataChanged();
    }

    synchronized public int getRowCount() {
        return m_comboLegData.size();
    }

    synchronized public int getColumnCount() {
        return 9;
    }

    synchronized public Object getValueAt(int r, int c) {
        ComboLeg comboLeg = m_comboLegData.get(r);
        OrderComboLeg orderComboLeg = m_orderComboLegData.get(r);

        return switch (c) {
            case 0 -> Integer.toString(comboLeg.conid());
            case 1 -> Integer.toString(comboLeg.ratio());
            case 2 -> comboLeg.action();
            case 3 -> comboLeg.exchange();
            case 4 -> Integer.toString(comboLeg.getOpenClose());
            case 5 -> Integer.toString(comboLeg.shortSaleSlot());
            case 6 -> comboLeg.designatedLocation();
            case 7 -> Integer.toString(comboLeg.exemptCode());
            case 8 -> parseMaxDoubleToString(orderComboLeg.price());
            default -> "";
        };
    }

    public boolean isCellEditable(int r, int c) {
        return false;
    }

    public String getColumnName(int c) {
        return switch (c) {
            case 0 -> "ConId";
            case 1 -> "Ratio";
            case 2 -> "Side";
            case 3 -> "Exchange";
            case 4 -> "Open/Close";
            case 5 -> "Short Sale Slot";
            case 6 -> "Designated Location";
            case 7 -> "Exempt Code";
            case 8 -> "Price";
            default -> null;
        };
    }

    List<ComboLeg> comboLegData() {
        return m_comboLegData;
    }

    List<OrderComboLeg> orderComboLegData() {
        return m_orderComboLegData;
    }

    private static String parseMaxDoubleToString(double value) {
        return value == Double.MAX_VALUE ? "" : String.valueOf(value);
    }
}
