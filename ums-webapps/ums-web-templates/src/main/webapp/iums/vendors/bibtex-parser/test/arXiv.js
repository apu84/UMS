import test from 'ava';

const bibtexParse = require('../bibtexParse');

const input = `@ARTICLE{2009PhLA..373.2301C,
	author = {{Chru{\'s}ci{\'n}ski}, D. and {Kossakowski}, A.},
	title = "{Geometry of quantum states: New construction of positive maps}",
	journal = {Physics Letters A},
	archivePrefix = "arXiv",
	eprint = {0902.0885},
	primaryClass = "quant-ph",
	year = 2009,
	month = jun,
	volume = 373,
	pages = {2301-2305},
	doi = {10.1016/j.physleta.2009.04.068},
	adsurl = {http://adsabs.harvard.edu/abs/2009PhLA..373.2301C},
	adsnote = {Provided by the SAO/NASA Astrophysics Data System}
}`

const output = [ { citationKey: '2009PhLA..373.2301C',
    entryType: 'ARTICLE',
    entryTags:
     { author: '{Chru{\'s}ci{\'n}ski}, D. and {Kossakowski}, A.',
       title: '{Geometry of quantum states: New construction of positive maps}',
       journal: 'Physics Letters A',
       archivePrefix: 'arXiv',
       eprint: '0902.0885',
       primaryClass: 'quant-ph',
       year: '2009',
       month: 'jun',
       volume: '373',
       pages: '2301-2305',
       doi: '10.1016/j.physleta.2009.04.068',
       adsurl: 'http://adsabs.harvard.edu/abs/2009PhLA..373.2301C',
       adsnote: 'Provided by the SAO/NASA Astrophysics Data System' } } ]

test('arXiv should parse', t => t.deepEqual(output, bibtexParse.toJSON(input)));
