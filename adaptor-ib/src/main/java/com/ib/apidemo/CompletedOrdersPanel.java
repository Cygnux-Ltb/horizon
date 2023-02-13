/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.HtmlButton;
import com.ib.client.Contract;
import com.ib.client.Order;
import com.ib.client.OrderState;
import com.ib.client.Util;
import com.ib.controller.ApiController.ICompletedOrdersHandler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class CompletedOrdersPanel extends JPanel implements ICompletedOrdersHandler {
    private final List<CompletedOrder> m_completedOrders = new ArrayList<>();
    private final Model m_model = new Model();

    CompletedOrdersPanel() {
        JTable table = new JTable(m_model);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new TitledBorder("Completed Orders"));

        HtmlButton but = new HtmlButton("Refresh") {
            @Override
            public void actionPerformed() {
                onRefresh();
            }
        };

        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.add(but);

        setLayout(new BorderLayout());
        add(scroll);
        add(p, BorderLayout.SOUTH);
    }

    public void activated() {
        onRefresh();
    }

    private void onRefresh() {
        m_completedOrders.clear();
        ApiDemo.INSTANCE.controller().reqCompletedOrders(this);
    }

    private class Model extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return m_completedOrders.size();
        }

        @Override
        public int getColumnCount() {
            return 12;
        }

        @Override
        public String getColumnName(int col) {
            return switch (col) {
                case 0 -> "Perm ID";
                case 1 -> "Parent Perm ID";
                case 2 -> "Account";
                case 3 -> "Action";
                case 4 -> "Quantity";
                case 5 -> "Cash Qty";
                case 6 -> "Filled Qty";
                case 7 -> "Lmt Price";
                case 8 -> "Aux Price";
                case 9 -> "Contract";
                case 10 -> "Status";
                case 11 -> "Completed Time";
                case 12 -> "Completed Status";
                default -> null;
            };
        }

        @Override
        public Object getValueAt(int row, int col) {
            CompletedOrder completedOrder = m_completedOrders.get(row);
            return switch (col) {
                case 0 -> completedOrder.m_order.permId();
                case 1 -> Util.LongMaxString(completedOrder.m_order.parentPermId());
                case 2 -> completedOrder.m_order.account();
                case 3 -> completedOrder.m_order.action();
                case 4 -> completedOrder.m_order.totalQuantity();
                case 5 -> completedOrder.m_order.cashQty();
                case 6 -> completedOrder.m_order.filledQuantity();
                case 7 -> completedOrder.m_order.lmtPrice();
                case 8 -> completedOrder.m_order.auxPrice();
                case 9 -> completedOrder.m_contract.description();
                case 10 -> completedOrder.m_orderState.status();
                case 11 -> completedOrder.m_orderState.completedTime();
                case 12 -> completedOrder.m_orderState.completedStatus();
                default -> null;
            };
        }
    }

    static class CompletedOrder {
        Contract m_contract;
        Order m_order;
        OrderState m_orderState;

        CompletedOrder(Contract contract, Order order, OrderState orderState) {
            m_contract = contract;
            m_order = order;
            m_orderState = orderState;
        }
    }

    @Override
    public void completedOrder(Contract contract, Order order, OrderState orderState) {
        CompletedOrder completedOrder = new CompletedOrder(contract, order, orderState);
        m_completedOrders.add(completedOrder);
        m_model.fireTableDataChanged();
    }

    @Override
    public void completedOrdersEnd() {
    }
}
