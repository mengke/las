/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.easycloud.las.api.services.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRecommendations implements org.apache.thrift.TBase<UserRecommendations, UserRecommendations._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UserRecommendations");

  private static final org.apache.thrift.protocol.TField USER_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("userCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField RECOMMENDATIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("recommendations", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UserRecommendationsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UserRecommendationsTupleSchemeFactory());
  }

  public String userCode; // required
  public List<Recommendation> recommendations; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    USER_CODE((short)1, "userCode"),
    RECOMMENDATIONS((short)2, "recommendations");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // USER_CODE
          return USER_CODE;
        case 2: // RECOMMENDATIONS
          return RECOMMENDATIONS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private _Fields optionals[] = {_Fields.RECOMMENDATIONS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USER_CODE, new org.apache.thrift.meta_data.FieldMetaData("userCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.RECOMMENDATIONS, new org.apache.thrift.meta_data.FieldMetaData("recommendations", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Recommendation.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UserRecommendations.class, metaDataMap);
  }

  public UserRecommendations() {
  }

  public UserRecommendations(
    String userCode)
  {
    this();
    this.userCode = userCode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UserRecommendations(UserRecommendations other) {
    if (other.isSetUserCode()) {
      this.userCode = other.userCode;
    }
    if (other.isSetRecommendations()) {
      List<Recommendation> __this__recommendations = new ArrayList<Recommendation>();
      for (Recommendation other_element : other.recommendations) {
        __this__recommendations.add(new Recommendation(other_element));
      }
      this.recommendations = __this__recommendations;
    }
  }

  public UserRecommendations deepCopy() {
    return new UserRecommendations(this);
  }

  @Override
  public void clear() {
    this.userCode = null;
    this.recommendations = null;
  }

  public String getUserCode() {
    return this.userCode;
  }

  public UserRecommendations setUserCode(String userCode) {
    this.userCode = userCode;
    return this;
  }

  public void unsetUserCode() {
    this.userCode = null;
  }

  /** Returns true if field userCode is set (has been assigned a value) and false otherwise */
  public boolean isSetUserCode() {
    return this.userCode != null;
  }

  public void setUserCodeIsSet(boolean value) {
    if (!value) {
      this.userCode = null;
    }
  }

  public int getRecommendationsSize() {
    return (this.recommendations == null) ? 0 : this.recommendations.size();
  }

  public java.util.Iterator<Recommendation> getRecommendationsIterator() {
    return (this.recommendations == null) ? null : this.recommendations.iterator();
  }

  public void addToRecommendations(Recommendation elem) {
    if (this.recommendations == null) {
      this.recommendations = new ArrayList<Recommendation>();
    }
    this.recommendations.add(elem);
  }

  public List<Recommendation> getRecommendations() {
    return this.recommendations;
  }

  public UserRecommendations setRecommendations(List<Recommendation> recommendations) {
    this.recommendations = recommendations;
    return this;
  }

  public void unsetRecommendations() {
    this.recommendations = null;
  }

  /** Returns true if field recommendations is set (has been assigned a value) and false otherwise */
  public boolean isSetRecommendations() {
    return this.recommendations != null;
  }

  public void setRecommendationsIsSet(boolean value) {
    if (!value) {
      this.recommendations = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case USER_CODE:
      if (value == null) {
        unsetUserCode();
      } else {
        setUserCode((String)value);
      }
      break;

    case RECOMMENDATIONS:
      if (value == null) {
        unsetRecommendations();
      } else {
        setRecommendations((List<Recommendation>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case USER_CODE:
      return getUserCode();

    case RECOMMENDATIONS:
      return getRecommendations();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case USER_CODE:
      return isSetUserCode();
    case RECOMMENDATIONS:
      return isSetRecommendations();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UserRecommendations)
      return this.equals((UserRecommendations)that);
    return false;
  }

  public boolean equals(UserRecommendations that) {
    if (that == null)
      return false;

    boolean this_present_userCode = true && this.isSetUserCode();
    boolean that_present_userCode = true && that.isSetUserCode();
    if (this_present_userCode || that_present_userCode) {
      if (!(this_present_userCode && that_present_userCode))
        return false;
      if (!this.userCode.equals(that.userCode))
        return false;
    }

    boolean this_present_recommendations = true && this.isSetRecommendations();
    boolean that_present_recommendations = true && that.isSetRecommendations();
    if (this_present_recommendations || that_present_recommendations) {
      if (!(this_present_recommendations && that_present_recommendations))
        return false;
      if (!this.recommendations.equals(that.recommendations))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(UserRecommendations other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    UserRecommendations typedOther = (UserRecommendations)other;

    lastComparison = Boolean.valueOf(isSetUserCode()).compareTo(typedOther.isSetUserCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userCode, typedOther.userCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRecommendations()).compareTo(typedOther.isSetRecommendations());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRecommendations()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.recommendations, typedOther.recommendations);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("UserRecommendations(");
    boolean first = true;

    sb.append("userCode:");
    if (this.userCode == null) {
      sb.append("null");
    } else {
      sb.append(this.userCode);
    }
    first = false;
    if (isSetRecommendations()) {
      if (!first) sb.append(", ");
      sb.append("recommendations:");
      if (this.recommendations == null) {
        sb.append("null");
      } else {
        sb.append(this.recommendations);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (userCode == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'userCode' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class UserRecommendationsStandardSchemeFactory implements SchemeFactory {
    public UserRecommendationsStandardScheme getScheme() {
      return new UserRecommendationsStandardScheme();
    }
  }

  private static class UserRecommendationsStandardScheme extends StandardScheme<UserRecommendations> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UserRecommendations struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USER_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userCode = iprot.readString();
              struct.setUserCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // RECOMMENDATIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.recommendations = new ArrayList<Recommendation>(_list0.size);
                for (int _i1 = 0; _i1 < _list0.size; ++_i1)
                {
                  Recommendation _elem2; // required
                  _elem2 = new Recommendation();
                  _elem2.read(iprot);
                  struct.recommendations.add(_elem2);
                }
                iprot.readListEnd();
              }
              struct.setRecommendationsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, UserRecommendations struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.userCode != null) {
        oprot.writeFieldBegin(USER_CODE_FIELD_DESC);
        oprot.writeString(struct.userCode);
        oprot.writeFieldEnd();
      }
      if (struct.recommendations != null) {
        if (struct.isSetRecommendations()) {
          oprot.writeFieldBegin(RECOMMENDATIONS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.recommendations.size()));
            for (Recommendation _iter3 : struct.recommendations)
            {
              _iter3.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UserRecommendationsTupleSchemeFactory implements SchemeFactory {
    public UserRecommendationsTupleScheme getScheme() {
      return new UserRecommendationsTupleScheme();
    }
  }

  private static class UserRecommendationsTupleScheme extends TupleScheme<UserRecommendations> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UserRecommendations struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.userCode);
      BitSet optionals = new BitSet();
      if (struct.isSetRecommendations()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetRecommendations()) {
        {
          oprot.writeI32(struct.recommendations.size());
          for (Recommendation _iter4 : struct.recommendations)
          {
            _iter4.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UserRecommendations struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.userCode = iprot.readString();
      struct.setUserCodeIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.recommendations = new ArrayList<Recommendation>(_list5.size);
          for (int _i6 = 0; _i6 < _list5.size; ++_i6)
          {
            Recommendation _elem7; // required
            _elem7 = new Recommendation();
            _elem7.read(iprot);
            struct.recommendations.add(_elem7);
          }
        }
        struct.setRecommendationsIsSet(true);
      }
    }
  }

}

