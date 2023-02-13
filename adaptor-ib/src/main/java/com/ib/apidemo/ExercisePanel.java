/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.AccountInfoPanel.PortfolioModel;
import com.ib.apidemo.util.HtmlButton;
import com.ib.apidemo.util.NewTabbedPanel.INewTab;
import com.ib.apidemo.util.TCombo;
import com.ib.apidemo.util.UpperField;
import com.ib.apidemo.util.VerticalPanel;
import com.ib.apidemo.util.VerticalPanel.HorzPanel;
import com.ib.client.Types.ExerciseType;
import com.ib.client.Types.SecType;
import com.ib.controller.ApiController.IAccountHandler;
import com.ib.controller.Position;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import java.io.Serial;


public class ExercisePanel extends HorzPanel implements INewTab, IAccountHandler {

    @Serial
    private static final long serialVersionUID = 5035750311798657692L;
    private final DefaultListModel<String> m_acctList = new DefaultListModel<>();
    private final JList<String> m_accounts = new JList<>(m_acctList);
    private String m_selAcct = "";
    private final PortfolioModel m_portfolioModel = new PortfolioModel();
    private final JTable m_portTable = new JTable(m_portfolioModel);

    ExercisePanel() {
        JScrollPane accountsScroll = new JScrollPane(m_accounts);
        accountsScroll.setBorder(new TitledBorder("Select account"));

        JScrollPane portScroll = new JScrollPane(m_portTable);
        portScroll.setBorder(new TitledBorder("Select long option position"));

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(accountsScroll);
        add(portScroll);
        add(new ExPanel());

        m_accounts.addListSelectionListener(e -> onAcctChanged());
    }

    private void onAcctChanged() {
        int i = m_accounts.getSelectedIndex();
        if (i != -1) {
            String selAcct = m_acctList.get(i);
            if (!selAcct.equals(m_selAcct)) {
                m_selAcct = selAcct;
                m_portfolioModel.clear();
                ApiDemo.INSTANCE.controller().reqAccountUpdates(true, m_selAcct, this);
            }
        }
    }

    class ExPanel extends VerticalPanel {
        @Serial
        private static final long serialVersionUID = 42898363606066874L;
        TCombo<ExerciseType> m_combo = new TCombo<>(ExerciseType.values());
        UpperField m_qty = new UpperField("1");
        JCheckBox m_override = new JCheckBox();

        ExPanel() {
            HtmlButton but = new HtmlButton("Go") {
                @Serial
                private static final long serialVersionUID = 2481827953473257299L;

                protected void actionPerformed() {
                    onExercise();
                }
            };

            m_combo.removeItem(ExerciseType.None);

            add("Action", m_combo);
            add("Quantity", m_qty);
            add("Override", m_override);
            add(but);
        }

        void onExercise() {
            String account = m_accounts.getSelectedValue();
            int i = m_portTable.getSelectedRow();
            if (i != -1 && account != null) {
                Position position = m_portfolioModel.getPosition(i);
                ApiDemo.INSTANCE.controller().exerciseOption(account, position.contract(), m_combo.getSelectedItem(), m_qty.getInt(), m_override.isSelected());
            }
        }
    }

    /**
     * Show long option positions only.
     */
    @Override
    public void updatePortfolio(Position position) {
        if (position.account().equals(m_selAcct) && position.contract().secType() == SecType.OPT) {
            m_portfolioModel.update(position);
        }
    }

    /**
     * Called when the tab is first visited.
     */
    @Override
    public void activated() {
        for (String account : ApiDemo.INSTANCE.accountList()) {
            m_acctList.addElement(account);
        }
    }

    /**
     * Called when the tab is closed by clicking the X.
     */
    @Override
    public void closed() {
    }

    @Override
    public void accountValue(String account, String key, String value, String currency) {
    }

    @Override
    public void accountTime(String timeStamp) {
    }

    @Override
    public void accountDownloadEnd(String account) {
    }
}
