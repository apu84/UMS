module ums {

    class PublicationInformation {

        public static $inject = [
            'registrarConstants',
            '$q',
            'notify',
            'countryService',
            'employeeInformationService',
            '$stateParams'];


        private publication: IPublicationInformationModel[] = [];
        private bulkPublication: IPublicationInformationModel[] = [];
        private publicationTypes: ICommon[] = [];
        private countries: ICommon[] = [];
        readonly userId: string = "";
        private stateParams: any;
        private enableEdit: boolean[] = [false];
        private editMode: boolean[] = [false];
        private enableEditButton: boolean = false;
        private showLoader: boolean = true;
        private showLoaderWhileBibParsing: boolean = false;
        private showSaveButton: boolean = false;
        private bibInput: string = "";
        private parsedBib: any;


        private months: any = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
        private notKey: any = [',', '{', '}', ' ', '='];
        private pos: number = 0;
        private input: string = "";
        private entries: any = new Array();
        private currentEntry: any = "";

        constructor(private registrarConstants: any,
                    private $q: ng.IQService,
                    private notify: Notify,
                    private countryService: CountryService,
                    private employeeInformationService: EmployeeInformationService,
                    private $stateParams: any) {

            this.publication = [];
            this.stateParams = $stateParams;
            this.publicationTypes = this.registrarConstants.publicationTypes;
            this.userId = this.stateParams.id;
            this.enableEditButton = this.stateParams.edit;
            this.countryService.getAll().then((countries: any) => {
                this.countries = countries;
                this.get();
            });
        }

        public submit(index: number): void {
            this.convertToJson(this.publication[index]).then((json: any) => {
                if (!this.publication[index].id) {
                    this.create(json, index);
                }
                else {
                    this.update(json, index);
                }
            });
        }


        private create(json: any, index: number) {
            this.employeeInformationService.savePublicationInformation(json).then((data: any) => {
                this.publication[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private update(json: any, index: number) {
            this.employeeInformationService.updatePublicationInformation(json).then((data: any) => {
                this.publication[index] = data;
                this.enableEdit[index] = false;
            }).catch((reason: any) => {
                this.enableEdit[index] = true;
            });
        }

        private updateBib(index: number) {
            console.log(this.bulkPublication[index]);
            this.activeEditButtonBib(index, false);
        }

        private get(): void {
            this.showLoader = true;
            this.publication = [];
            this.employeeInformationService.getPublicationInformation(this.userId).then((academicInformation: any) => {
                if (academicInformation) {
                    this.publication = academicInformation;
                }
                else {
                    this.publication = [];
                }
                this.showLoader = false;
            });
        }

        public delete(index: number): void {
            if (this.publication[index].id) {
                this.employeeInformationService.deletePublicationInformation(this.publication[index].id).then((data: any) => {
                    this.publication.splice(index, 1);
                });
            }
            else {
                this.publication.splice(index, 1);
            }
        }

        public deleteBib(index: number): void {
            this.bulkPublication.splice(index, 1);

        }

        public activeEditButton(index: number, canEdit: boolean): void {
            this.enableEdit[index] = canEdit;
        }

        public activeEditButtonBib(index: number, canEdit: boolean): void {
            console.log(index + " " + canEdit);
            this.editMode[index] = canEdit;
            console.log(this.editMode);
        }

        public addNew(): void {
            let publicationEntry: IPublicationInformationModel;
            publicationEntry = {
                id: "",
                employeeId: this.userId,
                publicationTitle: "",
                publicationType: null,
                publicationInterestGenre: "",
                publicationWebLink: "",
                publisherName: "",
                dateOfPublication: null,
                publicationISSN: "",
                publicationIssue: "",
                publicationVolume: "",
                publicationJournalName: "",
                publicationCountry: null,
                status: "0",
                publicationPages: "",
                appliedOn: "",
                actionTakenOn: "",
                rowNumber: null
            };
            this.publication.push(publicationEntry);
        }

        private addNewByBib(bulkBib: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            this.bulkPublication = [];
            for (let i = 0; i < bulkBib.length; i++) {
                let publicationBibEntry: IPublicationInformationModel;
                publicationBibEntry = {
                    id: "",
                    employeeId: this.userId,
                    publicationTitle: bulkBib[i].entryTags.title,
                    publicationType: bulkBib[i].entryTags.entryType == 'article' ? this.publicationTypes[1] :
                        bulkBib[i].entryTags.entryType == 'book' ? this.publicationTypes[2] : this.publicationTypes[3],
                    publicationInterestGenre: "",
                    publicationWebLink: bulkBib[i].entryTags.url,
                    publisherName: "",
                    dateOfPublication: Number(bulkBib[i].entryTags.year),
                    publicationISSN:
                    bulkBib[i].entryTags.issn,
                    publicationIssue: "",
                    publicationVolume:
                    bulkBib[i].entryTags.volume,
                    publicationJournalName: "",
                    publicationCountry: null,
                    status: "0",
                    publicationPages: bulkBib[i].entryTags.pages,
                    appliedOn: "",
                    actionTakenOn: "",
                    rowNumber: null
                };
                this.bulkPublication.push(publicationBibEntry);
            }
            defer.resolve(this.bulkPublication);
            return defer.promise;
        }

        private convertToJson(object: any): ng.IPromise<any> {
            let defer = this.$q.defer();
            let JsonObject = {};
            JsonObject['entries'] = object;
            defer.resolve(JsonObject);
            return defer.promise;
        }


        private parseBib(): void {
            this.showLoaderWhileBibParsing = true;
            this.parsedBib = this.toJSON(this.bibInput);
            if (this.parsedBib == undefined || this.parsedBib == null || this.parsedBib.length == 0) {
                this.notify.error("Error while parsing. Unknown format.");
                this.showSaveButton = false;
            }
            else {
                this.addNewByBib(this.parsedBib).then((result: any) => {
                    this.showSaveButton = true;
                });
            }
            this.showLoaderWhileBibParsing = false;
        }

        public validateBibInput(): void {
            let flag = 0;
            let errorIndex = [];
            for (let i = 0; i < this.bulkPublication.length; i++) {
                if (this.bulkPublication[i].publicationTitle && this.bulkPublication[i].publicationType && this.bulkPublication[i].dateOfPublication) {
                    //save operation
                }
                else {
                    flag = 1;
                    errorIndex.push(i + 1);
                }
            }

            if (flag == 0) {
                this.convertToJson(this.bulkPublication).then((json: any) => {
                    this.employeeInformationService.saveBibPublicationInformation(json).then((resolve: any) => {
                        this.get();
                        this.bulkPublication = [];
                        this.bibInput = "";
                        this.parsedBib = [];
                        this.showLoaderWhileBibParsing = false;
                        this.showSaveButton = false;
                        $('#importBibtex').modal('hide');
                    });
                });
            }
            else {
                let st = "";
                for (let j = 0; j < errorIndex.length; j++) {
                    st = errorIndex[j] + ",";
                }
                this.notify.error("Found error(s) in Sl [" + st + "], Please fill required fields properly");
            }
        }


        // TODO: Need to create separate service for Bib Parsing.

        private setInput(t) {
            this.input = t;
        }

        private getEntries() {
            return this.entries;
        }

        private isWhitespace(s) {
            return (s == ' ' || s == '\r' || s == '\t' || s == '\n');
        }

        private match(s?, canCommentOut?) {
            if (canCommentOut == undefined || canCommentOut == null)
                canCommentOut = true;
            this.skipWhitespace(canCommentOut);
            if (this.input.substring(this.pos, this.pos + s.length) == s) {
                this.pos += s.length;
            } else {
                this.notify.error("Token mismatch: match expected " + s + ", found "
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

        private matchAt() {
            while (this.input.length > this.pos && this.input[this.pos] != '@') {
                this.pos++;
            }
            if (this.input[this.pos] == '@') {
                return true;
            }
            return false;
        }


        private skipWhitespace(canCommentOut) {
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
                        this.notify.error("Unterminated value: value_braces");
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
                    this.notify.error("Unterminated value: value_comment ");
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
                        this.notify.error("Unterminated value: value_quotes" + this.input.substring(start));
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

        private key(optional?) {
            var start = this.pos;
            while (true) {
                if (this.pos >= this.input.length) {
                    this.notify.error("Runaway key: key");
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
                return [key, val];
            } else {
                this.notify.error("Value expected, equals sign missing: key_equals_value" +
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

        private directive() {
            this.match("@");
            return "@" + this.key();
        }

        private preamble() {
            this.currentEntry = {};
            this.currentEntry['entryType'] = 'PREAMBLE';
            this.currentEntry['entry'] = this.value_comment();
            this.entries.push(this.currentEntry);
        }

        private comment() {
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

        private bibtex() {
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
            this.pos = 0;
            this.input = "";
            this.entries = new Array();
            this.currentEntry = "";
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
            for (var i in json) {
                out += "@" + json[i].entryType;
                out += '{';
                if (json[i].citationKey)
                    out += json[i].citationKey + entrysep;
                if (json[i].entry)
                    out += json[i].entry;
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

        // -----------------------------------------------------------
    }

    UMS.controller("PublicationInformation", PublicationInformation);
}