/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package io.horizon.trader.transport.enums;

@org.apache.avro.specific.AvroGenerated
public enum TdxTrdDirection implements org.apache.avro.generic.GenericEnumSymbol<TdxTrdDirection> {
    INVALID, LONG, SHORT;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"TdxTrdDirection\",\"namespace\":\"io.horizon.trader.transport.enums\",\"symbols\":[\"INVALID\",\"LONG\",\"SHORT\"]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }
}
