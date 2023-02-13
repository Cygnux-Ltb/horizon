/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.apidemo.util.VerticalPanel;
import com.ib.client.OrderCondition;

public abstract class OnOKPanel extends VerticalPanel {
	public abstract OrderCondition onOK();
}