/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.apidemo;

import com.ib.client.Contract;
import com.ib.client.MarketDataType;
import com.ib.client.TickAttrib;
import com.ib.client.TickType;
import com.ib.controller.ApiController.TopMktDataAdapter;
import com.ib.controller.Formats;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

import static com.ib.controller.Formats.*;

class TopModel extends AbstractTableModel {

    private final List<TopRow> m_rows = new ArrayList<>();
    private final MarketDataPanel m_parentPanel;
    private static final int CANCEL_CHBX_COL_INDEX = 26;
    String m_genericTicks = "";

    TopModel(MarketDataPanel parentPanel) {
        m_parentPanel = parentPanel;
    }

    void setGenericTicks(String genericTicks) {
        m_genericTicks = genericTicks;
    }

    void addRow(Contract contract) {
        TopRow row = new TopRow(this, contract.description(), m_parentPanel);
        m_rows.add(row);
        ApiDemo.INSTANCE.controller().reqTopMktData(contract, m_genericTicks, false, false, row);
        fireTableRowsInserted(m_rows.size() - 1, m_rows.size() - 1);
    }

    void addRow(TopRow row) {
        m_rows.add(row);
        fireTableRowsInserted(m_rows.size() - 1, m_rows.size() - 1);
    }

    void removeSelectedRows() {
        for (int rowIndex = m_rows.size() - 1; rowIndex >= 0; rowIndex--) {
            if (m_rows.get(rowIndex).m_cancel) {
                ApiDemo.INSTANCE.controller().cancelTopMktData(m_rows.get(rowIndex));
                m_rows.remove(rowIndex);
            }
        }
        fireTableDataChanged();
    }

    void desubscribe() {
        for (TopRow row : m_rows) {
            ApiDemo.INSTANCE.controller().cancelTopMktData(row);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == CANCEL_CHBX_COL_INDEX) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == CANCEL_CHBX_COL_INDEX;
    }

    @Override
    public int getRowCount() {
        return m_rows.size();
    }

    @Override
    public int getColumnCount() {
        return 27;
    }

    @Override
    public String getColumnName(int col) {
        return switch (col) {
            case 0 -> "Description";
            case 1 -> "Bid Size";
            case 2 -> "Bid";
            case 3 -> "Bid Mask";
            case 4 -> "Bid Can Auto Execute";
            case 5 -> "Bid Past Limit";
            case 6 -> "Pre Open Bid";
            case 7 -> "Ask";
            case 8 -> "Ask Size";
            case 9 -> "Ask Mask";
            case 10 -> "Ask Can Auto Execute";
            case 11 -> "Ask Past Limit";
            case 12 -> "Pre Open Ask";
            case 13 -> "Last";
            case 14 -> "Time";
            case 15 -> "Change";
            case 16 -> "Volume";
            case 17 -> "Min Tick";
            case 18 -> "BBO Exchange";
            case 19 -> "Snapshot Permissions";
            case 20 -> "Close";
            case 21 -> "Open";
            case 22 -> "Market Data Type";
            case 23 -> "Futures Open Interest";
            case 24 -> "Avg Opt Volume";
            case 25 -> "Shortable Shares";
            case CANCEL_CHBX_COL_INDEX -> "Cancel";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int rowIn, int col) {
        TopRow row = m_rows.get(rowIn);
        return switch (col) {
            case 0 -> row.m_description;
            case 1 -> row.m_bidSize;
            case 2 -> fmt(row.m_bid);
            case 3 -> row.m_bidMask;
            case 4 -> row.m_bidCanAutoExecute;
            case 5 -> row.m_bidPastLimit;
            case 6 -> row.m_preOpenBid;
            case 7 -> fmt(row.m_ask);
            case 8 -> row.m_askSize;
            case 9 -> row.m_askMask;
            case 10 -> row.m_askCanAutoExecute;
            case 11 -> row.m_askPastLimit;
            case 12 -> row.m_preOpenAsk;
            case 13 -> fmt(row.m_last);
            case 14 -> fmtTime(row.m_lastTime);
            case 15 -> row.change();
            case 16 -> Formats.fmt0(row.m_volume);
            case 17 -> row.m_minTick;
            case 18 -> row.m_bboExch;
            case 19 -> row.m_snapshotPermissions;
            case 20 -> fmt(row.m_close);
            case 21 -> fmt(row.m_open);
            case 22 -> row.m_marketDataType;
            case 23 -> row.m_futuresOpenInterest;
            case 24 -> row.m_avgOptVolume;
            case 25 -> row.m_shorTableShares;
            case CANCEL_CHBX_COL_INDEX -> row.m_cancel;
            default -> null;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIn, int col) {
        TopRow row = m_rows.get(rowIn);
        if (col == CANCEL_CHBX_COL_INDEX) {
            row.m_cancel = (Boolean) aValue;
        }
    }

    public void cancel(int i) {
        ApiDemo.INSTANCE.controller().cancelTopMktData(m_rows.get(i));
    }

    static class TopRow extends TopMktDataAdapter {
        AbstractTableModel m_model;
        MarketDataPanel m_parentPanel;
        String m_description;
        double m_bid;
        double m_ask;
        double m_last;
        long m_lastTime;
        int m_bidSize;
        int m_askSize;
        double m_close;
        int m_volume;
        double m_open;
        boolean m_cancel;
        String m_marketDataType = MarketDataType.getField(MarketDataType.REALTIME);
        boolean m_frozen;
        boolean m_bidCanAutoExecute, m_askCanAutoExecute;
        boolean m_bidPastLimit, m_askPastLimit;
        boolean m_preOpenBid, m_preOpenAsk;
        double m_minTick;
        String m_bboExch;
        int m_snapshotPermissions;
        int m_bidMask, m_askMask;
        int m_futuresOpenInterest;
        int m_avgOptVolume;
        int m_shorTableShares;

        TopRow(AbstractTableModel model, String description, MarketDataPanel parentPanel) {
            m_model = model;
            m_description = description;
            m_parentPanel = parentPanel;
        }

        public String change() {
            return m_close == 0 ? null : fmtPct((m_last - m_close) / m_close);
        }

        @Override
        public void tickPrice(TickType tickType, double price, TickAttrib attribs) {
            if (m_marketDataType.equalsIgnoreCase(MarketDataType.getField(MarketDataType.REALTIME)) &&
                    (tickType == TickType.DELAYED_BID ||
                            tickType == TickType.DELAYED_ASK ||
                            tickType == TickType.DELAYED_LAST ||
                            tickType == TickType.DELAYED_CLOSE ||
                            tickType == TickType.DELAYED_OPEN)) {
                m_marketDataType = MarketDataType.getField(MarketDataType.DELAYED);
            }

            switch (tickType) {
                case BID, DELAYED_BID -> {
                    m_bid = price;
                    m_bidCanAutoExecute = attribs.canAutoExecute();
                    m_bidPastLimit = attribs.pastLimit();
                    m_preOpenBid = attribs.preOpen();
                }
                case ASK, DELAYED_ASK -> {
                    m_ask = price;
                    m_askCanAutoExecute = attribs.canAutoExecute();
                    m_askPastLimit = attribs.pastLimit();
                    m_preOpenAsk = attribs.preOpen();
                }
                case LAST, DELAYED_LAST -> m_last = price;
                case CLOSE, DELAYED_CLOSE -> m_close = price;
                case OPEN, DELAYED_OPEN -> m_open = price;
                default -> {
                }
            }
            m_model.fireTableDataChanged(); // should use a timer to be more efficient
        }

        @Override
        public void tickSize(TickType tickType, int size) {
            if (m_marketDataType.equalsIgnoreCase(MarketDataType.getField(MarketDataType.REALTIME)) &&
                    (tickType == TickType.DELAYED_BID_SIZE ||
                            tickType == TickType.DELAYED_ASK_SIZE ||
                            tickType == TickType.DELAYED_VOLUME)) {
                m_marketDataType = MarketDataType.getField(MarketDataType.DELAYED);
            }
            switch (tickType) {
                case BID_SIZE, DELAYED_BID_SIZE -> m_bidSize = size;
                case ASK_SIZE, DELAYED_ASK_SIZE -> m_askSize = size;
                case VOLUME, DELAYED_VOLUME -> m_volume = size;
                case FUTURES_OPEN_INTEREST -> m_futuresOpenInterest = size;
                case AVG_OPT_VOLUME -> m_avgOptVolume = size;
                case SHORTABLE_SHARES -> m_shorTableShares = size;
                default -> {
                }
            }
            m_model.fireTableDataChanged();
        }

        @Override
        public void tickString(TickType tickType, String value) {
            switch (tickType) {
                case LAST_TIMESTAMP, DELAYED_LAST_TIMESTAMP -> m_lastTime = Long.parseLong(value) * 1000;
                default -> {
                }
            }
        }

        @Override
        public void marketDataType(int marketDataType) {
            m_marketDataType = MarketDataType.getField(marketDataType);
            m_model.fireTableDataChanged();
        }

        @Override
        public void tickReqParams(int tickerId, double minTick, String bboExchange, int snapshotPermissions) {
            m_minTick = minTick;
            m_bboExch = bboExchange;
            m_snapshotPermissions = snapshotPermissions;

            m_parentPanel.addBboExch(bboExchange);
            m_model.fireTableDataChanged();
        }
    }
}
