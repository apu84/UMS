module ums{
    export class BibtexParserService{
        private months: any = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
        private notKey: any = [',','{','}',' ','='];
        private pos: number = 0;
        private input: string = "";
        private entries: any = new Array();
        private currentEntry: any = "";

        constructor() {}

        private setInput(t) {
            this.input = t;
        }

        private getEntries(){
            return this.entries;
        }

        private isWhitespace(s){
            return (s == ' ' || s == '\r' || s == '\t' || s == '\n');
        }

        private match(s?, canCommentOut?){
            if (canCommentOut == undefined || canCommentOut == null)
                canCommentOut = true;
            this.skipWhitespace(canCommentOut);
            if (this.input.substring(this.pos, this.pos + s.length) == s) {
                this.pos += s.length;
            } else {
                throw TypeError("Token mismatch: match expected " + s + ", found "
                    + this.input.substring(this.pos));
            }
            this.skipWhitespace(canCommentOut);
        }
        
        private tryMatch(s?, canCommentOut?) {
            if (canCommentOut == undefined || canCommentOut == null)
                canCommentOut = true;
            this.skipWhitespace(canCommentOut);
            if (this.input.substring(this.pos, this.pos + s.length) == s) {
                return true;
            } else {
                return false;
            }
            //this.skipWhitespace(canCommentOut);
        }

        private matchAt(){
            while (this.input.length > this.pos && this.input[this.pos] != '@') {
                this.pos++;
            }
            if (this.input[this.pos] == '@') {
                return true;
            }
            return false;
        }


        private skipWhitespace(canCommentOut){
            while (this.isWhitespace(this.input[this.pos])) {
                this.pos++;
            }
            if (this.input[this.pos] == "%" && canCommentOut == true) {
                while (this.input[this.pos] != "\n") {
                    this.pos++;
                }
                this.skipWhitespace(canCommentOut);
            }
        }

        private value_braces() {
            var bracecount = 0;
            this.match("{", false);
            var start = this.pos;
            var escaped = false;
            while (true) {
                if (!escaped) {
                    if (this.input[this.pos] == '}') {
                        if (bracecount > 0) {
                            bracecount--;
                        } else {
                            var end = this.pos;
                            this.match("}", false);
                            return this.input.substring(start, end);
                        }
                    } else if (this.input[this.pos] == '{') {
                        bracecount++;
                    } else if (this.pos >= this.input.length - 1) {
                        throw TypeError("Unterminated value: value_braces");
                    }
                }
                if (this.input[this.pos] == '\\' && escaped == false)
                    escaped = true;
                else
                    escaped = false;
                this.pos++;
            }
        }


        private value_comment() {
            var str = '';
            var brcktCnt = 0;
            while (!(this.tryMatch("}", false) && brcktCnt == 0)) {
                str = str + this.input[this.pos];
                if (this.input[this.pos] == '{')
                    brcktCnt++;
                if (this.input[this.pos] == '}')
                    brcktCnt--;
                if (this.pos >= this.input.length - 1) {
                    throw TypeError("Unterminated value: value_comment ");
                }
                this.pos++;
            }
            return str;
        }

        private value_quotes() {
            this.match('"', false);
            var start = this.pos;
            var escaped = false;
            while (true) {
                if (!escaped) {
                    if (this.input[this.pos] == '"') {
                        var end = this.pos;
                        this.match('"', false);
                        return this.input.substring(start, end);
                    } else if (this.pos >= this.input.length - 1) {
                        throw TypeError("Unterminated value: value_quotes" + this.input.substring(start));
                    }
                }
                if (this.input[this.pos] == '\\' && escaped == false)
                    escaped = true;
                else
                    escaped = false;
                this.pos++;
            }
        }

        private single_value() {
            var start = this.pos;
            if (this.tryMatch("{")) {
                return this.value_braces();
            } else if (this.tryMatch('"')) {
                return this.value_quotes();
            } else {
                var k = this.key();
                if (k.match("^[0-9]+$"))
                    return k;
                else if (this.months.indexOf(k.toLowerCase()) >= 0)
                    return k.toLowerCase();
                else
                    throw "Value expected: single_value" + this.input.substring(start) + ' for key: ' + k;

            }
        }

        private value() {
            var values = [];
            values.push(this.single_value());
            while (this.tryMatch("#")) {
                this.match("#");
                values.push(this.single_value());
            }
            return values.join("");
        };

        private key(optional?){
            var start = this.pos;
            while (true) {
                if (this.pos >= this.input.length) {
                    throw TypeError("Runaway key: key");
                }
                // а-яА-Я is Cyrillic
                //console.log(this.input[this.pos]);
                if (this.notKey.indexOf(this.input[this.pos]) >= 0) {
                    if (optional && this.input[this.pos] != ',') {
                        this.pos = start;
                        return null;
                    }
                    return this.input.substring(start, this.pos);
                } else {
                    this.pos++;
                }
            }
        }


        private key_equals_value() {
            var key = this.key();
            if (this.tryMatch("=")) {
                this.match("=");
                var val = this.value();
                key = key.trim();
                return [ key, val ];
            } else {
                throw TypeError("Value expected, equals sign missing: key_equals_value" +
                    this.input.substring(this.pos));
            }
        }

        private key_value_list() {
            var kv = this.key_equals_value();
            this.currentEntry['entryTags'] = {};
            this.currentEntry['entryTags'][kv[0]] = kv[1];
            while (this.tryMatch(",")) {
                this.match(",");
                // fixes problems with commas at the end of a list
                if (this.tryMatch("}")) {
                    break;
                }
                kv = this.key_equals_value();
                this.currentEntry['entryTags'][kv[0]] = kv[1];
            }
        };

        private entry_body(d) {
            this.currentEntry = {};
            this.currentEntry['citationKey'] = this.key(true);
            this.currentEntry['entryType'] = d.substring(1);
            if (this.currentEntry['citationKey'] != null) {
                this.match(",");
            }
            this.key_value_list();
            this.entries.push(this.currentEntry);
        }

        private directive(){
            this.match("@");
            return "@" + this.key();
        }

        private preamble(){
            this.currentEntry = {};
            this.currentEntry['entryType'] = 'PREAMBLE';
            this.currentEntry['entry'] = this.value_comment();
            this.entries.push(this.currentEntry);
        }

        private comment(){
            this.currentEntry = {};
            this.currentEntry['entryType'] = 'COMMENT';
            this.currentEntry['entry'] = this.value_comment();
            this.entries.push(this.currentEntry);
        }

        private entry(d) {
            this.entry_body(d);
        }

        private alernativeCitationKey() {
            this.entries.forEach(function (entry) {
                if (!entry.citationKey && entry.entryTags) {
                    entry.citationKey = '';
                    if (entry.entryTags.author) {
                        entry.citationKey += entry.entryTags.author.split(',')[0] += ', ';
                    }
                    entry.citationKey += entry.entryTags.year;
                }
            });
        }

        private bibtex(){
            while (this.matchAt()) {
                var d = this.directive();
                this.match("{");
                if (d.toUpperCase() == "@STRING") {
                    //this.string();
                } else if (d.toUpperCase() == "@PREAMBLE") {
                    this.preamble();
                } else if (d.toUpperCase() == "@COMMENT") {
                    this.comment();
                } else {
                    this.entry(d);
                }
                this.match("}");
            }
            this.alernativeCitationKey();
        };

        public toJSON(bibtex: string) {
            this.setInput(bibtex);
            this.bibtex();
            return this.entries;
        };

        public toBibtex(json, compact) {
            if (compact === undefined) compact = true;
            var out = '';
            var entrysep = ',';
            var indent = '';
            if (!compact) {
                entrysep = ',\n';
                indent = '    ';
            }
            for ( var i in json) {
                out += "@" + json[i].entryType;
                out += '{';
                if (json[i].citationKey)
                    out += json[i].citationKey + entrysep;
                if (json[i].entry)
                    out += json[i].entry ;
                if (json[i].entryTags) {
                    var tags = indent;
                    for (var jdx in json[i].entryTags) {
                        if (tags.trim().length != 0)
                            tags += entrysep + indent;
                        tags += jdx + (compact ? '={' : ' = {') +
                            json[i].entryTags[jdx] + '}';
                    }
                    out += tags;
                }
                out += compact ? '}\n' : '\n}\n\n';
            }
            return out;
        }
    }

    UMS.service("bibtexParserService", BibtexParserService);
}