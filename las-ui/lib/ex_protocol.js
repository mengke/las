var util = require('util');
var Thrift = require('thrift').Thrift;

var Wrapper = exports.Wrapper = function(service_name, protocol) {

    var TCompositeProtocol = function(trans, strictRead, strictWrite) {
        protocol.call(this, trans, strictRead, strictWrite);
    }
    util.inherits(TCompositeProtocol, protocol);

    TCompositeProtocol.prototype.writeMessageBegin = function(name, type, seqid) {

        if (type == Thrift.MessageType.CALL || type == Thrift.MessageType.ONEWAY)
            TCompositeProtocol.super_.prototype.writeMessageBegin.call(this, service_name + ":" + name, type, seqid);
        else
            TCompositeProtocol.super_.prototype.writeMessageBegin.call(this, name, type, seqid);
    }

    return TCompositeProtocol;
}

var TComposite = exports.TComposite = function() {
    this.seqid = 0;
}

TComposite.prototype.createClient = function(service_name, cls, connection) {
    if (cls.Client) {
        cls = cls.Client;
    }
    var self = this;
    cls.prototype.new_seqid = function() {
        self.seqid += 1;
        return self.seqid;
    }

    var client = new cls(new connection.transport(undefined, function(buf) {
        connection.write(buf);
    }), new Wrapper(service_name, connection.protocol));


    // TODO clean this up
    connection.client = client;

    return client;
}
