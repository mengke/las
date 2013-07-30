//
// Autogenerated by Thrift Compiler (0.9.0)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//
var Thrift = require('thrift').Thrift;
var ttypes = module.exports = {};
ttypes.UserType = {
'ALL' : 0,
'ANONYMOUS' : 1,
'LOGIN' : 2
};
ttypes.HouseType = {
'ALL' : 0,
'SELL' : 1,
'RENT' : 2
};
HvRecord = module.exports.HvRecord = function(args) {
  this.houseCode = null;
  this.houseType = null;
  this.visitDttm = null;
  if (args) {
    if (args.houseCode !== undefined) {
      this.houseCode = args.houseCode;
    }
    if (args.houseType !== undefined) {
      this.houseType = args.houseType;
    }
    if (args.visitDttm !== undefined) {
      this.visitDttm = args.visitDttm;
    }
  }
};
HvRecord.prototype = {};
HvRecord.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.houseCode = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.houseType = input.readByte();
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.I64) {
        this.visitDttm = input.readI64();
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

HvRecord.prototype.write = function(output) {
  output.writeStructBegin('HvRecord');
  if (this.houseCode !== null && this.houseCode !== undefined) {
    output.writeFieldBegin('houseCode', Thrift.Type.STRING, 1);
    output.writeString(this.houseCode);
    output.writeFieldEnd();
  }
  if (this.houseType !== null && this.houseType !== undefined) {
    output.writeFieldBegin('houseType', Thrift.Type.BYTE, 2);
    output.writeByte(this.houseType);
    output.writeFieldEnd();
  }
  if (this.visitDttm !== null && this.visitDttm !== undefined) {
    output.writeFieldBegin('visitDttm', Thrift.Type.I64, 3);
    output.writeI64(this.visitDttm);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

UvsRecord = module.exports.UvsRecord = function(args) {
  this.userCode = null;
  this.userType = null;
  this.hvRecords = null;
  if (args) {
    if (args.userCode !== undefined) {
      this.userCode = args.userCode;
    }
    if (args.userType !== undefined) {
      this.userType = args.userType;
    }
    if (args.hvRecords !== undefined) {
      this.hvRecords = args.hvRecords;
    }
  }
};
UvsRecord.prototype = {};
UvsRecord.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRING) {
        this.userCode = input.readString();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.userType = input.readByte();
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.LIST) {
        var _size0 = 0;
        var _rtmp34;
        this.hvRecords = [];
        var _etype3 = 0;
        _rtmp34 = input.readListBegin();
        _etype3 = _rtmp34.etype;
        _size0 = _rtmp34.size;
        for (var _i5 = 0; _i5 < _size0; ++_i5)
        {
          var elem6 = null;
          elem6 = new ttypes.HvRecord();
          elem6.read(input);
          this.hvRecords.push(elem6);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

UvsRecord.prototype.write = function(output) {
  output.writeStructBegin('UvsRecord');
  if (this.userCode !== null && this.userCode !== undefined) {
    output.writeFieldBegin('userCode', Thrift.Type.STRING, 1);
    output.writeString(this.userCode);
    output.writeFieldEnd();
  }
  if (this.userType !== null && this.userType !== undefined) {
    output.writeFieldBegin('userType', Thrift.Type.BYTE, 2);
    output.writeByte(this.userType);
    output.writeFieldEnd();
  }
  if (this.hvRecords !== null && this.hvRecords !== undefined) {
    output.writeFieldBegin('hvRecords', Thrift.Type.LIST, 3);
    output.writeListBegin(Thrift.Type.STRUCT, this.hvRecords.length);
    for (var iter7 in this.hvRecords)
    {
      if (this.hvRecords.hasOwnProperty(iter7))
      {
        iter7 = this.hvRecords[iter7];
        iter7.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

ttypes.VERSION = '1.0.0';