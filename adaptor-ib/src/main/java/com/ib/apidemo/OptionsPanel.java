/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.NewTabbedPanel;

import java.io.Serial;

public class OptionsPanel extends NewTabbedPanel {

    @Serial
    private static final long serialVersionUID = 7421577715756898630L;

    OptionsPanel() {
        NewTabbedPanel tabs = this;
        OptionChainsPanel m_optionChains = new OptionChainsPanel();
        tabs.addTab("Option Chains", m_optionChains);
        ExercisePanel m_exercisePanel = new ExercisePanel();
        tabs.addTab("Option Exercise", m_exercisePanel);
    }
}
