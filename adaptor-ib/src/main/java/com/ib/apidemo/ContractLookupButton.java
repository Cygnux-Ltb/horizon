/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.HtmlButton;
import com.ib.client.ContractLookuper;

import java.awt.event.MouseEvent;

public abstract class ContractLookupButton extends HtmlButton {

    final ContractSearchDlg m_contractSearchDlg;

    public ContractLookupButton(int conId, String exchange, ContractLookuper lookuper) {
        super("");

        m_contractSearchDlg = new ContractSearchDlg(conId, exchange, lookuper);

        setText(m_contractSearchDlg.refContract() == null ? "search..." : m_contractSearchDlg.refContract());
    }

    @Override
    protected void onClicked(MouseEvent e) {
        m_contractSearchDlg.setLocationRelativeTo(this.getParent());
        m_contractSearchDlg.setVisible(true);

        setText(m_contractSearchDlg.refContract() == null ? "search..." : m_contractSearchDlg.refContract());
        actionPerformed();
        actionPerformed(m_contractSearchDlg.refConId(), m_contractSearchDlg.refExchId());
    }

    protected abstract void actionPerformed(int refConId, String refExchId);
}
