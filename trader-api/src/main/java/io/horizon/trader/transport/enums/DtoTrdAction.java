/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.horizon.trader.transport.enums;
@org.apache.avro.specific.AvroGenerated
public enum DtoTrdAction implements org.apache.avro.generic.GenericEnumSymbol<DtoTrdAction> {
  INVALID, OPEN, CLOSE, CLOSE_TODAY, CLOSE_YESTERDAY  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"DtoTrdAction\",\"namespace\":\"io.horizon.trader.transport.enums\",\"symbols\":[\"INVALID\",\"OPEN\",\"CLOSE\",\"CLOSE_TODAY\",\"CLOSE_YESTERDAY\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
