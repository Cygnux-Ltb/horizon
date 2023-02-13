/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.AccountInfoPanel.Table;
import com.ib.apidemo.util.HtmlButton;
import com.ib.apidemo.util.NewTabbedPanel.NewTabPanel;
import com.ib.apidemo.util.VerticalPanel;
import com.ib.client.FamilyCode;
import com.ib.controller.ApiController.IFamilyCodesHandler;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.BorderLayout;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;


public class FamilyCodesPanel extends NewTabPanel {
    @Serial
    private static final long serialVersionUID = -4886932577205272584L;
    private final FamilyCodesModel m_model = new FamilyCodesModel();

    FamilyCodesPanel() {
        HtmlButton requestFamilyCodesButton = new HtmlButton("Request Family Codes") {
            @Serial
            private static final long serialVersionUID = 3407168667633464494L;

            protected void actionPerformed() {
                requestFamilyCodes();
            }
        };

        HtmlButton clearFamilyCodesButton = new HtmlButton("Clear Family Codes") {
            @Serial
            private static final long serialVersionUID = 5042053585989778926L;

            protected void actionPerformed() {
                clearFamilyCodes();
            }
        };

        JPanel buts = new VerticalPanel();
        buts.add(requestFamilyCodesButton);
        buts.add(clearFamilyCodesButton);

        JTable table = new Table(m_model, 2);
        JScrollPane scroll = new JScrollPane(table);

        setLayout(new BorderLayout());
        add(scroll);
        add(buts, BorderLayout.EAST);
    }

    /**
     * Called when the tab is first visited.
     */
    @Override
    public void activated() { /* noop */ }

    /**
     * Called when the tab is closed by clicking the X.
     */
    @Override
    public void closed() {
        clearFamilyCodes();
    }

    private void requestFamilyCodes() {
        ApiDemo.INSTANCE.controller().reqFamilyCodes(m_model);
    }

    private void clearFamilyCodes() {
        m_model.clear();
    }

    private class FamilyCodesModel extends AbstractTableModel implements IFamilyCodesHandler {
        @Serial
        private static final long serialVersionUID = -6550684964712700098L;
        List<FamilyCodeRow> m_list = new ArrayList<>();

        @Override
        public void familyCodes(FamilyCode[] familyCodes) {
            for (FamilyCode familyCode : familyCodes) {
                FamilyCodeRow row = new FamilyCodeRow();
                m_list.add(row);
                row.update(familyCode.accountID(), familyCode.familyCodeStr());
            }
            m_model.fireTableDataChanged();
        }

        public void clear() {
            m_list.clear();
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return m_list.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(int col) {
            return switch (col) {
                case 0 -> "Account ID";
                case 1 -> "Family Code";
                default -> null;
            };
        }

        @Override
        public Object getValueAt(int rowIn, int col) {
            FamilyCodeRow row = m_list.get(rowIn);
            return switch (col) {
                case 0 -> row.m_accountID;
                case 1 -> row.m_familyCodeStr;
                default -> null;
            };
        }

    }

    private static class FamilyCodeRow {
        String m_accountID;
        String m_familyCodeStr;

        void update(String accountID, String familyCodeStr) {
            m_accountID = accountID;
            m_familyCodeStr = familyCodeStr;
        }
    }
}
