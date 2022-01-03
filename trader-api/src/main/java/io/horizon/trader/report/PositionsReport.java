/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.horizon.trader.report;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

/** * 持仓回报 */
@org.apache.avro.specific.AvroGenerated
public class PositionsReport extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 4556260804214886810L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PositionsReport\",\"namespace\":\"io.horizon.trader.report\",\"doc\":\"* 持仓回报\",\"fields\":[{\"name\":\"epochMillis\",\"type\":\"long\"},{\"name\":\"investorId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"instrumentCode\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"qty\",\"type\":\"int\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<PositionsReport> ENCODER =
      new BinaryMessageEncoder<PositionsReport>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<PositionsReport> DECODER =
      new BinaryMessageDecoder<PositionsReport>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<PositionsReport> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<PositionsReport> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<PositionsReport> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<PositionsReport>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this PositionsReport to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a PositionsReport from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a PositionsReport instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static PositionsReport fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private long epochMillis;
  private java.lang.String investorId;
  private java.lang.String instrumentCode;
  private int qty;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public PositionsReport() {}

  /**
   * All-args constructor.
   * @param epochMillis The new value for epochMillis
   * @param investorId The new value for investorId
   * @param instrumentCode The new value for instrumentCode
   * @param qty The new value for qty
   */
  public PositionsReport(java.lang.Long epochMillis, java.lang.String investorId, java.lang.String instrumentCode, java.lang.Integer qty) {
    this.epochMillis = epochMillis;
    this.investorId = investorId;
    this.instrumentCode = instrumentCode;
    this.qty = qty;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return epochMillis;
    case 1: return investorId;
    case 2: return instrumentCode;
    case 3: return qty;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: epochMillis = (java.lang.Long)value$; break;
    case 1: investorId = value$ != null ? value$.toString() : null; break;
    case 2: instrumentCode = value$ != null ? value$.toString() : null; break;
    case 3: qty = (java.lang.Integer)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'epochMillis' field.
   * @return The value of the 'epochMillis' field.
   */
  public long getEpochMillis() {
    return epochMillis;
  }


  /**
   * Sets the value of the 'epochMillis' field.
   * @param value the value to set.
   */
  public void setEpochMillis(long value) {
    this.epochMillis = value;
  }

  /**
   * Gets the value of the 'investorId' field.
   * @return The value of the 'investorId' field.
   */
  public java.lang.String getInvestorId() {
    return investorId;
  }


  /**
   * Sets the value of the 'investorId' field.
   * @param value the value to set.
   */
  public void setInvestorId(java.lang.String value) {
    this.investorId = value;
  }

  /**
   * Gets the value of the 'instrumentCode' field.
   * @return The value of the 'instrumentCode' field.
   */
  public java.lang.String getInstrumentCode() {
    return instrumentCode;
  }


  /**
   * Sets the value of the 'instrumentCode' field.
   * @param value the value to set.
   */
  public void setInstrumentCode(java.lang.String value) {
    this.instrumentCode = value;
  }

  /**
   * Gets the value of the 'qty' field.
   * @return The value of the 'qty' field.
   */
  public int getQty() {
    return qty;
  }


  /**
   * Sets the value of the 'qty' field.
   * @param value the value to set.
   */
  public void setQty(int value) {
    this.qty = value;
  }

  /**
   * Creates a new PositionsReport RecordBuilder.
   * @return A new PositionsReport RecordBuilder
   */
  public static io.horizon.trader.report.PositionsReport.Builder newBuilder() {
    return new io.horizon.trader.report.PositionsReport.Builder();
  }

  /**
   * Creates a new PositionsReport RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new PositionsReport RecordBuilder
   */
  public static io.horizon.trader.report.PositionsReport.Builder newBuilder(io.horizon.trader.report.PositionsReport.Builder other) {
    if (other == null) {
      return new io.horizon.trader.report.PositionsReport.Builder();
    } else {
      return new io.horizon.trader.report.PositionsReport.Builder(other);
    }
  }

  /**
   * Creates a new PositionsReport RecordBuilder by copying an existing PositionsReport instance.
   * @param other The existing instance to copy.
   * @return A new PositionsReport RecordBuilder
   */
  public static io.horizon.trader.report.PositionsReport.Builder newBuilder(io.horizon.trader.report.PositionsReport other) {
    if (other == null) {
      return new io.horizon.trader.report.PositionsReport.Builder();
    } else {
      return new io.horizon.trader.report.PositionsReport.Builder(other);
    }
  }

  /**
   * RecordBuilder for PositionsReport instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PositionsReport>
    implements org.apache.avro.data.RecordBuilder<PositionsReport> {

    private long epochMillis;
    private java.lang.String investorId;
    private java.lang.String instrumentCode;
    private int qty;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.horizon.trader.report.PositionsReport.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.epochMillis)) {
        this.epochMillis = data().deepCopy(fields()[0].schema(), other.epochMillis);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.investorId)) {
        this.investorId = data().deepCopy(fields()[1].schema(), other.investorId);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.instrumentCode)) {
        this.instrumentCode = data().deepCopy(fields()[2].schema(), other.instrumentCode);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.qty)) {
        this.qty = data().deepCopy(fields()[3].schema(), other.qty);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing PositionsReport instance
     * @param other The existing instance to copy.
     */
    private Builder(io.horizon.trader.report.PositionsReport other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.epochMillis)) {
        this.epochMillis = data().deepCopy(fields()[0].schema(), other.epochMillis);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.investorId)) {
        this.investorId = data().deepCopy(fields()[1].schema(), other.investorId);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.instrumentCode)) {
        this.instrumentCode = data().deepCopy(fields()[2].schema(), other.instrumentCode);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.qty)) {
        this.qty = data().deepCopy(fields()[3].schema(), other.qty);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'epochMillis' field.
      * @return The value.
      */
    public long getEpochMillis() {
      return epochMillis;
    }


    /**
      * Sets the value of the 'epochMillis' field.
      * @param value The value of 'epochMillis'.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder setEpochMillis(long value) {
      validate(fields()[0], value);
      this.epochMillis = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'epochMillis' field has been set.
      * @return True if the 'epochMillis' field has been set, false otherwise.
      */
    public boolean hasEpochMillis() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'epochMillis' field.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder clearEpochMillis() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'investorId' field.
      * @return The value.
      */
    public java.lang.String getInvestorId() {
      return investorId;
    }


    /**
      * Sets the value of the 'investorId' field.
      * @param value The value of 'investorId'.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder setInvestorId(java.lang.String value) {
      validate(fields()[1], value);
      this.investorId = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'investorId' field has been set.
      * @return True if the 'investorId' field has been set, false otherwise.
      */
    public boolean hasInvestorId() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'investorId' field.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder clearInvestorId() {
      investorId = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'instrumentCode' field.
      * @return The value.
      */
    public java.lang.String getInstrumentCode() {
      return instrumentCode;
    }


    /**
      * Sets the value of the 'instrumentCode' field.
      * @param value The value of 'instrumentCode'.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder setInstrumentCode(java.lang.String value) {
      validate(fields()[2], value);
      this.instrumentCode = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'instrumentCode' field has been set.
      * @return True if the 'instrumentCode' field has been set, false otherwise.
      */
    public boolean hasInstrumentCode() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'instrumentCode' field.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder clearInstrumentCode() {
      instrumentCode = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'qty' field.
      * @return The value.
      */
    public int getQty() {
      return qty;
    }


    /**
      * Sets the value of the 'qty' field.
      * @param value The value of 'qty'.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder setQty(int value) {
      validate(fields()[3], value);
      this.qty = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'qty' field has been set.
      * @return True if the 'qty' field has been set, false otherwise.
      */
    public boolean hasQty() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'qty' field.
      * @return This builder.
      */
    public io.horizon.trader.report.PositionsReport.Builder clearQty() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public PositionsReport build() {
      try {
        PositionsReport record = new PositionsReport();
        record.epochMillis = fieldSetFlags()[0] ? this.epochMillis : (java.lang.Long) defaultValue(fields()[0]);
        record.investorId = fieldSetFlags()[1] ? this.investorId : (java.lang.String) defaultValue(fields()[1]);
        record.instrumentCode = fieldSetFlags()[2] ? this.instrumentCode : (java.lang.String) defaultValue(fields()[2]);
        record.qty = fieldSetFlags()[3] ? this.qty : (java.lang.Integer) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<PositionsReport>
    WRITER$ = (org.apache.avro.io.DatumWriter<PositionsReport>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<PositionsReport>
    READER$ = (org.apache.avro.io.DatumReader<PositionsReport>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeLong(this.epochMillis);

    out.writeString(this.investorId);

    out.writeString(this.instrumentCode);

    out.writeInt(this.qty);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.epochMillis = in.readLong();

      this.investorId = in.readString();

      this.instrumentCode = in.readString();

      this.qty = in.readInt();

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.epochMillis = in.readLong();
          break;

        case 1:
          this.investorId = in.readString();
          break;

        case 2:
          this.instrumentCode = in.readString();
          break;

        case 3:
          this.qty = in.readInt();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










