/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package io.horizon.market.transport.outbound;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;
import java.util.Optional;
/** * 行情订阅回报 */
@org.apache.avro.specific.AvroGenerated
public class MarketDataSubscribeReply extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 5586703587655395917L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"MarketDataSubscribeReply\",\"namespace\":\"io.horizon.market.transport.outbound\",\"doc\":\"* 行情订阅回报\",\"fields\":[{\"name\":\"status\",\"type\":{\"type\":\"enum\",\"name\":\"SubscribeStatus\",\"namespace\":\"io.horizon.market.transport.enums\",\"doc\":\"* 行情订阅状态\",\"symbols\":[\"Succeed\",\"PartSucceed\",\"Failed\"]}},{\"name\":\"successInstrumentCodes\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}},{\"name\":\"failInstrumentCodes\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}},{\"name\":\"msg\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<MarketDataSubscribeReply> ENCODER =
      new BinaryMessageEncoder<MarketDataSubscribeReply>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<MarketDataSubscribeReply> DECODER =
      new BinaryMessageDecoder<MarketDataSubscribeReply>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<MarketDataSubscribeReply> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<MarketDataSubscribeReply> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<MarketDataSubscribeReply> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<MarketDataSubscribeReply>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this MarketDataSubscribeReply to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a MarketDataSubscribeReply from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a MarketDataSubscribeReply instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static MarketDataSubscribeReply fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private io.horizon.market.transport.enums.SubscribeStatus status;
  private java.util.List<java.lang.String> successInstrumentCodes;
  private java.util.List<java.lang.String> failInstrumentCodes;
  private java.lang.String msg;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public MarketDataSubscribeReply() {}

  /**
   * All-args constructor.
   * @param status The new value for status
   * @param successInstrumentCodes The new value for successInstrumentCodes
   * @param failInstrumentCodes The new value for failInstrumentCodes
   * @param msg The new value for msg
   */
  public MarketDataSubscribeReply(io.horizon.market.transport.enums.SubscribeStatus status, java.util.List<java.lang.String> successInstrumentCodes, java.util.List<java.lang.String> failInstrumentCodes, java.lang.String msg) {
    this.status = status;
    this.successInstrumentCodes = successInstrumentCodes;
    this.failInstrumentCodes = failInstrumentCodes;
    this.msg = msg;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return status;
    case 1: return successInstrumentCodes;
    case 2: return failInstrumentCodes;
    case 3: return msg;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: status = (io.horizon.market.transport.enums.SubscribeStatus)value$; break;
    case 1: successInstrumentCodes = (java.util.List<java.lang.String>)value$; break;
    case 2: failInstrumentCodes = (java.util.List<java.lang.String>)value$; break;
    case 3: msg = value$ != null ? value$.toString() : null; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'status' field.
   * @return The value of the 'status' field.
   */
  public io.horizon.market.transport.enums.SubscribeStatus getStatus() {
    return status;
  }

  /**
   * Gets the value of the 'status' field as an Optional&lt;io.horizon.market.transport.enums.SubscribeStatus&gt;.
   * @return The value wrapped in an Optional&lt;io.horizon.market.transport.enums.SubscribeStatus&gt;.
   */
  public Optional<io.horizon.market.transport.enums.SubscribeStatus> getOptionalStatus() {
    return Optional.<io.horizon.market.transport.enums.SubscribeStatus>ofNullable(status);
  }

  /**
   * Sets the value of the 'status' field.
   * @param value the value to set.
   */
  public MarketDataSubscribeReply setStatus(io.horizon.market.transport.enums.SubscribeStatus value) {
    this.status = value;
    return this;
  }

  /**
   * Gets the value of the 'successInstrumentCodes' field.
   * @return The value of the 'successInstrumentCodes' field.
   */
  public java.util.List<java.lang.String> getSuccessInstrumentCodes() {
    return successInstrumentCodes;
  }

  /**
   * Gets the value of the 'successInstrumentCodes' field as an Optional&lt;java.util.List<java.lang.String>&gt;.
   * @return The value wrapped in an Optional&lt;java.util.List<java.lang.String>&gt;.
   */
  public Optional<java.util.List<java.lang.String>> getOptionalSuccessInstrumentCodes() {
    return Optional.<java.util.List<java.lang.String>>ofNullable(successInstrumentCodes);
  }

  /**
   * Sets the value of the 'successInstrumentCodes' field.
   * @param value the value to set.
   */
  public MarketDataSubscribeReply setSuccessInstrumentCodes(java.util.List<java.lang.String> value) {
    this.successInstrumentCodes = value;
    return this;
  }

  /**
   * Gets the value of the 'failInstrumentCodes' field.
   * @return The value of the 'failInstrumentCodes' field.
   */
  public java.util.List<java.lang.String> getFailInstrumentCodes() {
    return failInstrumentCodes;
  }

  /**
   * Gets the value of the 'failInstrumentCodes' field as an Optional&lt;java.util.List<java.lang.String>&gt;.
   * @return The value wrapped in an Optional&lt;java.util.List<java.lang.String>&gt;.
   */
  public Optional<java.util.List<java.lang.String>> getOptionalFailInstrumentCodes() {
    return Optional.<java.util.List<java.lang.String>>ofNullable(failInstrumentCodes);
  }

  /**
   * Sets the value of the 'failInstrumentCodes' field.
   * @param value the value to set.
   */
  public MarketDataSubscribeReply setFailInstrumentCodes(java.util.List<java.lang.String> value) {
    this.failInstrumentCodes = value;
    return this;
  }

  /**
   * Gets the value of the 'msg' field.
   * @return The value of the 'msg' field.
   */
  public java.lang.String getMsg() {
    return msg;
  }

  /**
   * Gets the value of the 'msg' field as an Optional&lt;java.lang.String&gt;.
   * @return The value wrapped in an Optional&lt;java.lang.String&gt;.
   */
  public Optional<java.lang.String> getOptionalMsg() {
    return Optional.<java.lang.String>ofNullable(msg);
  }

  /**
   * Sets the value of the 'msg' field.
   * @param value the value to set.
   */
  public MarketDataSubscribeReply setMsg(java.lang.String value) {
    this.msg = value;
    return this;
  }

  /**
   * Creates a new MarketDataSubscribeReply RecordBuilder.
   * @return A new MarketDataSubscribeReply RecordBuilder
   */
  public static io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder newBuilder() {
    return new io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder();
  }

  /**
   * Creates a new MarketDataSubscribeReply RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new MarketDataSubscribeReply RecordBuilder
   */
  public static io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder newBuilder(io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder other) {
    if (other == null) {
      return new io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder();
    } else {
      return new io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder(other);
    }
  }

  /**
   * Creates a new MarketDataSubscribeReply RecordBuilder by copying an existing MarketDataSubscribeReply instance.
   * @param other The existing instance to copy.
   * @return A new MarketDataSubscribeReply RecordBuilder
   */
  public static io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder newBuilder(io.horizon.market.transport.outbound.MarketDataSubscribeReply other) {
    if (other == null) {
      return new io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder();
    } else {
      return new io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder(other);
    }
  }

  /**
   * RecordBuilder for MarketDataSubscribeReply instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<MarketDataSubscribeReply>
    implements org.apache.avro.data.RecordBuilder<MarketDataSubscribeReply> {

    private io.horizon.market.transport.enums.SubscribeStatus status;
    private java.util.List<java.lang.String> successInstrumentCodes;
    private java.util.List<java.lang.String> failInstrumentCodes;
    private java.lang.String msg;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.successInstrumentCodes)) {
        this.successInstrumentCodes = data().deepCopy(fields()[1].schema(), other.successInstrumentCodes);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.failInstrumentCodes)) {
        this.failInstrumentCodes = data().deepCopy(fields()[2].schema(), other.failInstrumentCodes);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.msg)) {
        this.msg = data().deepCopy(fields()[3].schema(), other.msg);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
    }

    /**
     * Creates a Builder by copying an existing MarketDataSubscribeReply instance
     * @param other The existing instance to copy.
     */
    private Builder(io.horizon.market.transport.outbound.MarketDataSubscribeReply other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.status)) {
        this.status = data().deepCopy(fields()[0].schema(), other.status);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.successInstrumentCodes)) {
        this.successInstrumentCodes = data().deepCopy(fields()[1].schema(), other.successInstrumentCodes);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.failInstrumentCodes)) {
        this.failInstrumentCodes = data().deepCopy(fields()[2].schema(), other.failInstrumentCodes);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.msg)) {
        this.msg = data().deepCopy(fields()[3].schema(), other.msg);
        fieldSetFlags()[3] = true;
      }
    }

    /**
      * Gets the value of the 'status' field.
      * @return The value.
      */
    public io.horizon.market.transport.enums.SubscribeStatus getStatus() {
      return status;
    }

    /**
      * Gets the value of the 'status' field as an Optional&lt;io.horizon.market.transport.enums.SubscribeStatus&gt;.
      * @return The value wrapped in an Optional&lt;io.horizon.market.transport.enums.SubscribeStatus&gt;.
      */
    public Optional<io.horizon.market.transport.enums.SubscribeStatus> getOptionalStatus() {
      return Optional.<io.horizon.market.transport.enums.SubscribeStatus>ofNullable(status);
    }

    /**
      * Sets the value of the 'status' field.
      * @param value The value of 'status'.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder setStatus(io.horizon.market.transport.enums.SubscribeStatus value) {
      validate(fields()[0], value);
      this.status = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'status' field has been set.
      * @return True if the 'status' field has been set, false otherwise.
      */
    public boolean hasStatus() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'status' field.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder clearStatus() {
      status = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'successInstrumentCodes' field.
      * @return The value.
      */
    public java.util.List<java.lang.String> getSuccessInstrumentCodes() {
      return successInstrumentCodes;
    }

    /**
      * Gets the value of the 'successInstrumentCodes' field as an Optional&lt;java.util.List<java.lang.String>&gt;.
      * @return The value wrapped in an Optional&lt;java.util.List<java.lang.String>&gt;.
      */
    public Optional<java.util.List<java.lang.String>> getOptionalSuccessInstrumentCodes() {
      return Optional.<java.util.List<java.lang.String>>ofNullable(successInstrumentCodes);
    }

    /**
      * Sets the value of the 'successInstrumentCodes' field.
      * @param value The value of 'successInstrumentCodes'.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder setSuccessInstrumentCodes(java.util.List<java.lang.String> value) {
      validate(fields()[1], value);
      this.successInstrumentCodes = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'successInstrumentCodes' field has been set.
      * @return True if the 'successInstrumentCodes' field has been set, false otherwise.
      */
    public boolean hasSuccessInstrumentCodes() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'successInstrumentCodes' field.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder clearSuccessInstrumentCodes() {
      successInstrumentCodes = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'failInstrumentCodes' field.
      * @return The value.
      */
    public java.util.List<java.lang.String> getFailInstrumentCodes() {
      return failInstrumentCodes;
    }

    /**
      * Gets the value of the 'failInstrumentCodes' field as an Optional&lt;java.util.List<java.lang.String>&gt;.
      * @return The value wrapped in an Optional&lt;java.util.List<java.lang.String>&gt;.
      */
    public Optional<java.util.List<java.lang.String>> getOptionalFailInstrumentCodes() {
      return Optional.<java.util.List<java.lang.String>>ofNullable(failInstrumentCodes);
    }

    /**
      * Sets the value of the 'failInstrumentCodes' field.
      * @param value The value of 'failInstrumentCodes'.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder setFailInstrumentCodes(java.util.List<java.lang.String> value) {
      validate(fields()[2], value);
      this.failInstrumentCodes = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'failInstrumentCodes' field has been set.
      * @return True if the 'failInstrumentCodes' field has been set, false otherwise.
      */
    public boolean hasFailInstrumentCodes() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'failInstrumentCodes' field.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder clearFailInstrumentCodes() {
      failInstrumentCodes = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'msg' field.
      * @return The value.
      */
    public java.lang.String getMsg() {
      return msg;
    }

    /**
      * Gets the value of the 'msg' field as an Optional&lt;java.lang.String&gt;.
      * @return The value wrapped in an Optional&lt;java.lang.String&gt;.
      */
    public Optional<java.lang.String> getOptionalMsg() {
      return Optional.<java.lang.String>ofNullable(msg);
    }

    /**
      * Sets the value of the 'msg' field.
      * @param value The value of 'msg'.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder setMsg(java.lang.String value) {
      validate(fields()[3], value);
      this.msg = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'msg' field has been set.
      * @return True if the 'msg' field has been set, false otherwise.
      */
    public boolean hasMsg() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'msg' field.
      * @return This builder.
      */
    public io.horizon.market.transport.outbound.MarketDataSubscribeReply.Builder clearMsg() {
      msg = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public MarketDataSubscribeReply build() {
      try {
        MarketDataSubscribeReply record = new MarketDataSubscribeReply();
        record.status = fieldSetFlags()[0] ? this.status : (io.horizon.market.transport.enums.SubscribeStatus) defaultValue(fields()[0]);
        record.successInstrumentCodes = fieldSetFlags()[1] ? this.successInstrumentCodes : (java.util.List<java.lang.String>) defaultValue(fields()[1]);
        record.failInstrumentCodes = fieldSetFlags()[2] ? this.failInstrumentCodes : (java.util.List<java.lang.String>) defaultValue(fields()[2]);
        record.msg = fieldSetFlags()[3] ? this.msg : (java.lang.String) defaultValue(fields()[3]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<MarketDataSubscribeReply>
    WRITER$ = (org.apache.avro.io.DatumWriter<MarketDataSubscribeReply>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<MarketDataSubscribeReply>
    READER$ = (org.apache.avro.io.DatumReader<MarketDataSubscribeReply>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeEnum(this.status.ordinal());

    long size0 = this.successInstrumentCodes.size();
    out.writeArrayStart();
    out.setItemCount(size0);
    long actualSize0 = 0;
    for (java.lang.String e0: this.successInstrumentCodes) {
      actualSize0++;
      out.startItem();
      out.writeString(e0);
    }
    out.writeArrayEnd();
    if (actualSize0 != size0)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size0 + ", but element count was " + actualSize0 + ".");

    long size1 = this.failInstrumentCodes.size();
    out.writeArrayStart();
    out.setItemCount(size1);
    long actualSize1 = 0;
    for (java.lang.String e1: this.failInstrumentCodes) {
      actualSize1++;
      out.startItem();
      out.writeString(e1);
    }
    out.writeArrayEnd();
    if (actualSize1 != size1)
      throw new java.util.ConcurrentModificationException("Array-size written was " + size1 + ", but element count was " + actualSize1 + ".");

    out.writeString(this.msg);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.status = io.horizon.market.transport.enums.SubscribeStatus.values()[in.readEnum()];

      long size0 = in.readArrayStart();
      java.util.List<java.lang.String> a0 = this.successInstrumentCodes;
      if (a0 == null) {
        a0 = new SpecificData.Array<java.lang.String>((int)size0, SCHEMA$.getField("successInstrumentCodes").schema());
        this.successInstrumentCodes = a0;
      } else a0.clear();
      SpecificData.Array<java.lang.String> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a0 : null);
      for ( ; 0 < size0; size0 = in.arrayNext()) {
        for ( ; size0 != 0; size0--) {
          java.lang.String e0 = (ga0 != null ? ga0.peek() : null);
          e0 = in.readString();
          a0.add(e0);
        }
      }

      long size1 = in.readArrayStart();
      java.util.List<java.lang.String> a1 = this.failInstrumentCodes;
      if (a1 == null) {
        a1 = new SpecificData.Array<java.lang.String>((int)size1, SCHEMA$.getField("failInstrumentCodes").schema());
        this.failInstrumentCodes = a1;
      } else a1.clear();
      SpecificData.Array<java.lang.String> ga1 = (a1 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a1 : null);
      for ( ; 0 < size1; size1 = in.arrayNext()) {
        for ( ; size1 != 0; size1--) {
          java.lang.String e1 = (ga1 != null ? ga1.peek() : null);
          e1 = in.readString();
          a1.add(e1);
        }
      }

      this.msg = in.readString();

    } else {
      for (int i = 0; i < 4; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.status = io.horizon.market.transport.enums.SubscribeStatus.values()[in.readEnum()];
          break;

        case 1:
          long size0 = in.readArrayStart();
          java.util.List<java.lang.String> a0 = this.successInstrumentCodes;
          if (a0 == null) {
            a0 = new SpecificData.Array<java.lang.String>((int)size0, SCHEMA$.getField("successInstrumentCodes").schema());
            this.successInstrumentCodes = a0;
          } else a0.clear();
          SpecificData.Array<java.lang.String> ga0 = (a0 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a0 : null);
          for ( ; 0 < size0; size0 = in.arrayNext()) {
            for ( ; size0 != 0; size0--) {
              java.lang.String e0 = (ga0 != null ? ga0.peek() : null);
              e0 = in.readString();
              a0.add(e0);
            }
          }
          break;

        case 2:
          long size1 = in.readArrayStart();
          java.util.List<java.lang.String> a1 = this.failInstrumentCodes;
          if (a1 == null) {
            a1 = new SpecificData.Array<java.lang.String>((int)size1, SCHEMA$.getField("failInstrumentCodes").schema());
            this.failInstrumentCodes = a1;
          } else a1.clear();
          SpecificData.Array<java.lang.String> ga1 = (a1 instanceof SpecificData.Array ? (SpecificData.Array<java.lang.String>)a1 : null);
          for ( ; 0 < size1; size1 = in.arrayNext()) {
            for ( ; size1 != 0; size1--) {
              java.lang.String e1 = (ga1 != null ? ga1.peek() : null);
              e1 = in.readString();
              a1.add(e1);
            }
          }
          break;

        case 3:
          this.msg = in.readString();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










