package io.horizon.trader.order;

import org.apache.avro.generic.GenericEnumSymbol;

public interface TdxProvider<T extends GenericEnumSymbol<T>> {

    T getTdxValue();

}
