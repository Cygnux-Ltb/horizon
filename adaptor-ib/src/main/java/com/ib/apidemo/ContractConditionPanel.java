/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.client.ContractCondition;
import com.ib.client.ContractLookuper;

import java.io.Serial;

public class ContractConditionPanel<T extends ContractCondition> extends OperatorConditionPanel<T> {
    @Serial
    private static final long serialVersionUID = -8284094642803182534L;

    ContractConditionPanel(T c, ContractLookuper lookuper) {
        super(c);

        final ContractCondition condition = m_condition;

        add(new ContractLookupButton(condition.conId(), condition.exchange(), lookuper) {
            @Serial
            private static final long serialVersionUID = 8031871797291360811L;

            protected void actionPerformed(int refConId, String refExchId) {
                condition.conId(refConId);
                condition.exchange(refExchId);
            }
        });
    }

}
