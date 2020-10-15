/* Copyright (C) 2013 Interactive Brokers LLC. All rights reserved.  This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingConstants;

import com.ib.apidemo.util.HtmlButton;
import com.ib.client.Contract;

class ContractDlg extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4719720385860146237L;
	ContractPanel m_contractPanel;

	ContractDlg(JFrame f, Contract c) {
		super(f, true);

		m_contractPanel = new ContractPanel(c);

		setLayout(new BorderLayout());

		HtmlButton ok = new HtmlButton("OK") {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4669293569169595114L;

			@Override
			public void actionPerformed() {
				onOK();
			}
		};
		ok.setHorizontalAlignment(SwingConstants.CENTER);

		m_contractPanel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("lkj");
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		add(m_contractPanel);
		add(ok, BorderLayout.SOUTH);
		pack();
	}

	public void onOK() {
		m_contractPanel.onOK();
		setVisible(false);
	}
}
