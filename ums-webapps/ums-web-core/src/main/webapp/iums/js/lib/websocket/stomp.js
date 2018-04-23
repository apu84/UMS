// Generated by CoffeeScript 1.12.6
/*
   Stomp Over WebSocket http://www.jmesnil.net/stomp-websocket/doc/ | Apache License V2.0

   Copyright (C) 2010-2013 [Jeff Mesnil](http://jmesnil.net/)
   Copyright (C) 2012 [FuseSource, Inc.](http://fusesource.com)
   Copyright (C) 2017 [Deepak Kumar](https://www.kreatio.com)
 */
(function () {
    var e, t, n, i, r = {}.hasOwnProperty, s = [].slice;
    e = {LF: "\n", NULL: "\0"};
    n = function () {
        var t;

        function n(e, t, n, i) {
            this.command = e;
            this.headers = t != null ? t : {};
            this.body = n != null ? n : "";
            this.escapeHeaderValues = i != null ? i : false
        }

        n.prototype.toString = function () {
            var t, i, s, o, c;
            t = [this.command];
            o = this.headers["content-length"] === false ? true : false;
            if (o) {
                delete this.headers["content-length"]
            }
            s = this.headers;
            for (i in s) {
                if (!r.call(s, i)) continue;
                c = s[i];
                if (this.escapeHeaderValues && this.command !== "CONNECT" && this.command !== "CONNECTED") {
                    t.push(i + ":" + n.frEscape(c))
                } else {
                    t.push(i + ":" + c)
                }
            }
            if (this.body && !o) {
                t.push("content-length:" + n.sizeOfUTF8(this.body))
            }
            t.push(e.LF + this.body);
            return t.join(e.LF)
        };
        n.sizeOfUTF8 = function (e) {
            if (e) {
                return encodeURI(e).match(/%..|./g).length
            } else {
                return 0
            }
        };
        t = function (t, i) {
            var r, s, o, c, a, u, f, l, h, p, d, g, b, m, v, y, _, S;
            if (i == null) {
                i = false
            }
            c = t.search(RegExp("" + e.LF + e.LF));
            a = t.substring(0, c).split(e.LF);
            o = a.shift();
            u = {};
            S = function (e) {
                return e.replace(/^\s+|\s+$/g, "")
            };
            m = a.reverse();
            for (h = 0, g = m.length; h < g; h++) {
                b = m[h];
                l = b.indexOf(":");
                if (i && o !== "CONNECT" && o !== "CONNECTED") {
                    u[S(b.substring(0, l))] = n.frUnEscape(S(b.substring(l + 1)))
                } else {
                    u[S(b.substring(0, l))] = S(b.substring(l + 1))
                }
            }
            r = "";
            _ = c + 2;
            if (u["content-length"]) {
                d = parseInt(u["content-length"]);
                r = ("" + t).substring(_, _ + d)
            } else {
                s = null;
                for (f = p = v = _, y = t.length; v <= y ? p < y : p > y; f = v <= y ? ++p : --p) {
                    s = t.charAt(f);
                    if (s === e.NULL) {
                        break
                    }
                    r += s
                }
            }
            return new n(o, u, r, i)
        };
        n.unmarshall = function (n, i) {
            var r, s, o, c;
            if (i == null) {
                i = false
            }
            s = n.split(RegExp("" + e.NULL + e.LF + "*"));
            c = {frames: [], partial: ""};
            c.frames = function () {
                var e, n, o, c;
                o = s.slice(0, -1);
                c = [];
                for (e = 0, n = o.length; e < n; e++) {
                    r = o[e];
                    c.push(t(r, i))
                }
                return c
            }();
            o = s.slice(-1)[0];
            if (o === e.LF || o.search(RegExp("" + e.NULL + e.LF + "*$")) !== -1) {
                c.frames.push(t(o, i))
            } else {
                c.partial = o
            }
            return c
        };
        n.marshall = function (t, i, r, s) {
            var o;
            o = new n(t, i, r, s);
            return o.toString() + e.NULL
        };
        n.frEscape = function (e) {
            return ("" + e).replace(/\\/g, "\\\\").replace(/\r/g, "\\r").replace(/\n/g, "\\n").replace(/:/g, "\\c")
        };
        n.frUnEscape = function (e) {
            return ("" + e).replace(/\\r/g, "\r").replace(/\\n/g, "\n").replace(/\\c/g, ":").replace(/\\\\/g, "\\")
        };
        return n
    }();
    t = function () {
        var t;

        function r(e) {
            this.ws_fn = function () {
                var t;
                t = e();
                t.binaryType = "arraybuffer";
                return t
            };
            this.reconnect_delay = 0;
            this.counter = 0;
            this.connected = false;
            this.heartbeat = {outgoing: 1e4, incoming: 1e4};
            this.maxWebSocketFrameSize = 16 * 1024;
            this.subscriptions = {};
            this.partialData = ""
        }

        r.prototype.debug = function (e) {
            var t;
            return typeof window !== "undefined" && window !== null ? (t = window.console) != null ? t.log(e) : void 0 : void 0
        };
        t = function () {
            if (Date.now) {
                return Date.now()
            } else {
                return (new Date).valueOf
            }
        };
        r.prototype._transmit = function (e, t, i) {
            var r;
            r = n.marshall(e, t, i, this.escapeHeaderValues);
            if (typeof this.debug === "function") {
                this.debug(">>> " + r)
            }
            while (true) {
                if (r.length > this.maxWebSocketFrameSize) {
                    this.ws.send(r.substring(0, this.maxWebSocketFrameSize));
                    r = r.substring(this.maxWebSocketFrameSize);
                    if (typeof this.debug === "function") {
                        this.debug("remaining = " + r.length)
                    }
                } else {
                    return this.ws.send(r)
                }
            }
        };
        r.prototype._setupHeartbeat = function (n) {
            var r, s, o, c, a, u;
            if ((r = n.version) !== i.VERSIONS.V1_1 && r !== i.VERSIONS.V1_2) {
                return
            }
            s = function () {
                var e, t, i, r;
                i = n["heart-beat"].split(",");
                r = [];
                for (e = 0, t = i.length; e < t; e++) {
                    u = i[e];
                    r.push(parseInt(u))
                }
                return r
            }(), c = s[0], o = s[1];
            if (!(this.heartbeat.outgoing === 0 || o === 0)) {
                a = Math.max(this.heartbeat.outgoing, o);
                if (typeof this.debug === "function") {
                    this.debug("send PING every " + a + "ms")
                }
                this.pinger = i.setInterval(a, function (t) {
                    return function () {
                        t.ws.send(e.LF);
                        return typeof t.debug === "function" ? t.debug(">>> PING") : void 0
                    }
                }(this))
            }
            if (!(this.heartbeat.incoming === 0 || c === 0)) {
                a = Math.max(this.heartbeat.incoming, c);
                if (typeof this.debug === "function") {
                    this.debug("check PONG every " + a + "ms")
                }
                return this.ponger = i.setInterval(a, function (e) {
                    return function () {
                        var n;
                        n = t() - e.serverActivity;
                        if (n > a * 2) {
                            if (typeof e.debug === "function") {
                                e.debug("did not receive server activity for the last " + n + "ms")
                            }
                            return e.ws.close()
                        }
                    }
                }(this))
            }
        };
        r.prototype._parseConnect = function () {
            var e, t, n, i, r;
            e = 1 <= arguments.length ? s.call(arguments, 0) : [];
            r = {};
            if (e.length < 2) {
                throw"Connect requires at least 2 arguments"
            }
            if (e[1] instanceof Function) {
                r = e[0], n = e[1], i = e[2], t = e[3]
            } else {
                switch (e.length) {
                    case 6:
                        r.login = e[0], r.passcode = e[1], n = e[2], i = e[3], t = e[4], r.host = e[5];
                        break;
                    default:
                        r.login = e[0], r.passcode = e[1], n = e[2], i = e[3], t = e[4]
                }
            }
            return [r, n, i, t]
        };
        r.prototype.connect = function () {
            var e, t;
            e = 1 <= arguments.length ? s.call(arguments, 0) : [];
            this.escapeHeaderValues = false;
            t = this._parseConnect.apply(this, e);
            this.headers = t[0], this.connectCallback = t[1], this.errorCallback = t[2], this.closeEventCallback = t[3];
            this._active = true;
            return this._connect()
        };
        r.prototype._connect = function () {
            var r, s, o;
            o = this.headers;
            s = this.errorCallback;
            r = this.closeEventCallback;
            if (typeof this.debug === "function") {
                this.debug("Opening Web Socket...")
            }
            this.ws = this.ws_fn();
            this.ws.onmessage = function (r) {
                return function (o) {
                    var c, a, u, f, l, h, p, d, g, b, m, v;
                    f = typeof ArrayBuffer !== "undefined" && o.data instanceof ArrayBuffer ? (c = new Uint8Array(o.data), typeof r.debug === "function" ? r.debug("--- got data length: " + c.length) : void 0, function () {
                        var e, t, n;
                        n = [];
                        for (e = 0, t = c.length; e < t; e++) {
                            a = c[e];
                            n.push(String.fromCharCode(a))
                        }
                        return n
                    }().join("")) : o.data;
                    r.serverActivity = t();
                    if (f === e.LF) {
                        if (typeof r.debug === "function") {
                            r.debug("<<< PONG")
                        }
                        return
                    }
                    if (typeof r.debug === "function") {
                        r.debug("<<< " + f)
                    }
                    v = n.unmarshall(r.partialData + f, r.escapeHeaderValues);
                    r.partialData = v.partial;
                    b = v.frames;
                    for (h = 0, p = b.length; h < p; h++) {
                        l = b[h];
                        switch (l.command) {
                            case"CONNECTED":
                                if (typeof r.debug === "function") {
                                    r.debug("connected to server " + l.headers.server)
                                }
                                r.connected = true;
                                r.version = l.headers.version;
                                if (r.version === i.VERSIONS.V1_2) {
                                    r.escapeHeaderValues = true
                                }
                                if (!r._active) {
                                    r.disconnect();
                                    return
                                }
                                r._setupHeartbeat(l.headers);
                                if (typeof r.connectCallback === "function") {
                                    r.connectCallback(l)
                                }
                                break;
                            case"MESSAGE":
                                m = l.headers.subscription;
                                g = r.subscriptions[m] || r.onreceive;
                                if (g) {
                                    u = r;
                                    if (r.version === i.VERSIONS.V1_2) {
                                        d = l.headers["ack"]
                                    } else {
                                        d = l.headers["message-id"]
                                    }
                                    l.ack = function (e) {
                                        if (e == null) {
                                            e = {}
                                        }
                                        return u.ack(d, m, e)
                                    };
                                    l.nack = function (e) {
                                        if (e == null) {
                                            e = {}
                                        }
                                        return u.nack(d, m, e)
                                    };
                                    g(l)
                                } else {
                                    if (typeof r.debug === "function") {
                                        r.debug("Unhandled received MESSAGE: " + l)
                                    }
                                }
                                break;
                            case"RECEIPT":
                                if (l.headers["receipt-id"] === r.closeReceipt) {
                                    r.ws.onclose = null;
                                    r.ws.close();
                                    r._cleanUp();
                                    if (typeof r._disconnectCallback === "function") {
                                        r._disconnectCallback()
                                    }
                                } else {
                                    if (typeof r.onreceipt === "function") {
                                        r.onreceipt(l)
                                    }
                                }
                                break;
                            case"ERROR":
                                if (typeof s === "function") {
                                    s(l)
                                }
                                break;
                            default:
                                if (typeof r.debug === "function") {
                                    r.debug("Unhandled frame: " + l)
                                }
                        }
                    }
                }
            }(this);
            this.ws.onclose = function (e) {
                return function (t) {
                    var n;
                    n = "Whoops! Lost connection to " + e.ws.url;
                    if (typeof e.debug === "function") {
                        e.debug(n)
                    }
                    if (typeof r === "function") {
                        r(t)
                    }
                    e._cleanUp();
                    if (typeof s === "function") {
                        s(n)
                    }
                    return e._schedule_reconnect()
                }
            }(this);
            return this.ws.onopen = function (e) {
                return function () {
                    if (typeof e.debug === "function") {
                        e.debug("Web Socket Opened...")
                    }
                    o["accept-version"] = i.VERSIONS.supportedVersions();
                    o["heart-beat"] = [e.heartbeat.outgoing, e.heartbeat.incoming].join(",");
                    return e._transmit("CONNECT", o)
                }
            }(this)
        };
        r.prototype._schedule_reconnect = function () {
            if (this.reconnect_delay > 0) {
                if (typeof this.debug === "function") {
                    this.debug("STOMP: scheduling reconnection in " + this.reconnect_delay + "ms")
                }
                return this._reconnector = setTimeout(function (e) {
                    return function () {
                        if (e.connected) {
                            return typeof e.debug === "function" ? e.debug("STOMP: already connected") : void 0
                        } else {
                            if (typeof e.debug === "function") {
                                e.debug("STOMP: attempting to reconnect")
                            }
                            return e._connect()
                        }
                    }
                }(this), this.reconnect_delay)
            }
        };
        r.prototype.disconnect = function (e, t) {
            var n;
            if (t == null) {
                t = {}
            }
            this._disconnectCallback = e;
            this._active = false;
            if (this.connected) {
                if (!t.receipt) {
                    t.receipt = "close-" + this.counter++
                }
                this.closeReceipt = t.receipt;
                try {
                    return this._transmit("DISCONNECT", t)
                } catch (e) {
                    n = e;
                    return typeof this.debug === "function" ? this.debug("Ignoring error during disconnect", n) : void 0
                }
            }
        };
        r.prototype._cleanUp = function () {
            if (this._reconnector) {
                clearTimeout(this._reconnector)
            }
            this.connected = false;
            this.subscriptions = {};
            this.partial = "";
            if (this.pinger) {
                i.clearInterval(this.pinger)
            }
            if (this.ponger) {
                return i.clearInterval(this.ponger)
            }
        };
        r.prototype.send = function (e, t, n) {
            if (t == null) {
                t = {}
            }
            if (n == null) {
                n = ""
            }
            t.destination = e;
            return this._transmit("SEND", t, n)
        };
        r.prototype.subscribe = function (e, t, n) {
            var i;
            if (n == null) {
                n = {}
            }
            if (!n.id) {
                n.id = "sub-" + this.counter++
            }
            n.destination = e;
            this.subscriptions[n.id] = t;
            this._transmit("SUBSCRIBE", n);
            i = this;
            return {
                id: n.id, unsubscribe: function (e) {
                    return i.unsubscribe(n.id, e)
                }
            }
        };
        r.prototype.unsubscribe = function (e, t) {
            if (t == null) {
                t = {}
            }
            delete this.subscriptions[e];
            t.id = e;
            return this._transmit("UNSUBSCRIBE", t)
        };
        r.prototype.begin = function (e) {
            var t, n;
            n = e || "tx-" + this.counter++;
            this._transmit("BEGIN", {transaction: n});
            t = this;
            return {
                id: n, commit: function () {
                    return t.commit(n)
                }, abort: function () {
                    return t.abort(n)
                }
            }
        };
        r.prototype.commit = function (e) {
            return this._transmit("COMMIT", {transaction: e})
        };
        r.prototype.abort = function (e) {
            return this._transmit("ABORT", {transaction: e})
        };
        r.prototype.ack = function (e, t, n) {
            if (n == null) {
                n = {}
            }
            if (this.version === i.VERSIONS.V1_2) {
                n["id"] = e
            } else {
                n["message-id"] = e
            }
            n.subscription = t;
            return this._transmit("ACK", n)
        };
        r.prototype.nack = function (e, t, n) {
            if (n == null) {
                n = {}
            }
            if (this.version === i.VERSIONS.V1_2) {
                n["id"] = e
            } else {
                n["message-id"] = e
            }
            n.subscription = t;
            return this._transmit("NACK", n)
        };
        return r
    }();
    i = {
        VERSIONS: {
            V1_0: "1.0", V1_1: "1.1", V1_2: "1.2", supportedVersions: function () {
                return "1.2,1.1,1.0"
            }
        }, client: function (e, n) {
            var r;
            if (n == null) {
                n = ["v10.stomp", "v11.stomp", "v12.stomp"]
            }
            r = function () {
                var t;
                t = i.WebSocketClass || WebSocket;
                return new t(e, n)
            };
            return new t(r)
        }, over: function (e) {
            var n;
            n = typeof e === "function" ? e : function () {
                return e
            };
            return new t(n)
        }, Frame: n
    };
    i.setInterval = function (e, t) {
        return setInterval(t, e)
    };
    i.clearInterval = function (e) {
        return clearInterval(e)
    };
    if (typeof exports !== "undefined" && exports !== null) {
        exports.Stomp = i
    }
    if (typeof window !== "undefined" && window !== null) {
        window.Stomp = i
    } else if (!exports) {
        self.Stomp = i
    }
}).call(this);