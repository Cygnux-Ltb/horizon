/**
 * Autogenerated by Avro
 * <p>
 * DO NOT EDIT DIRECTLY
 */
package io.horizon.trader.transport.enums;

@org.apache.avro.specific.AvroGenerated
public enum TdxOrdStatus implements org.apache.avro.generic.GenericEnumSymbol<TdxOrdStatus> {
    INVALID, PENDING_NEW, NEW, NEW_REJECTED, PARTIALLY_FILLED, FILLED, PENDING_CANCEL, CANCELED, CANCEL_REJECTED, PENDING_REPLACE, REPLACED, SUSPENDED, UNPROVIDED;
    public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"TdxOrdStatus\",\"namespace\":\"io.horizon.trader.transport.enums\",\"symbols\":[\"INVALID\",\"PENDING_NEW\",\"NEW\",\"NEW_REJECTED\",\"PARTIALLY_FILLED\",\"FILLED\",\"PENDING_CANCEL\",\"CANCELED\",\"CANCEL_REJECTED\",\"PENDING_REPLACE\",\"REPLACED\",\"SUSPENDED\",\"UNPROVIDED\"]}");

    public static org.apache.avro.Schema getClassSchema() {
        return SCHEMA$;
    }

    public org.apache.avro.Schema getSchema() {
        return SCHEMA$;
    }
}
