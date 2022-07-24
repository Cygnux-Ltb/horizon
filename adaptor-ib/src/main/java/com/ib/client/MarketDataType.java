/* Copyright (C) 2019 Interactive Brokers LLC. All rights reserved. This code is subject to the terms
 * and conditions of the IB API Non-Commercial License or the IB API Commercial License, as applicable. */

package com.ib.client;

public class MarketDataType {
    // constants - market data types
    public static final int REALTIME   = 1;
    public static final int FROZEN     = 2;
    public static final int DELAYED    = 3;
    public static final int DELAYED_FROZEN = 4;

    private static final String REALTIME_STR = "Real-Time";
    private static final String FROZEN_STR = "Frozen";
    private static final String DELAYED_STR = "Delayed";
    private static final String DELAYED_FROZEN_STR = "Delayed-Frozen";
    private static final String UNKNOWN_STR = "Unknown";

    public static String getField( int marketDataType) {
        return switch (marketDataType) {
            case REALTIME -> REALTIME_STR;
            case FROZEN -> FROZEN_STR;
            case DELAYED -> DELAYED_STR;
            case DELAYED_FROZEN -> DELAYED_FROZEN_STR;
            default -> UNKNOWN_STR;
        };
    }

    public static int getField( String marketDataTypeStr) {
        return switch (marketDataTypeStr) {
            case REALTIME_STR -> REALTIME;
            case FROZEN_STR -> FROZEN;
            case DELAYED_STR -> DELAYED;
            case DELAYED_FROZEN_STR -> DELAYED_FROZEN;
            default -> Integer.MAX_VALUE;
        };
    }

    public static String[] getFields(){
    	int totalFields = MarketDataType.class.getFields().length;
    	String [] fields = new String[totalFields];
    	for (int i = 0; i < totalFields; i++){
    		fields[i] = MarketDataType.getField(i + 1);
    	}
    	return fields;
    }
}
