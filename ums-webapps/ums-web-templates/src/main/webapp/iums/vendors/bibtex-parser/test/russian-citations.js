import test from 'ava';

const bibtexParse = require('../bibtexParse');

const input = `
  @article{nazarenko2009new,
    title={A new genus and species of dryophthorid weevils (Coleoptera, Dryophthoridae: Stromboscerinae) from the Rovno amber},
    author={Nazarenko, V Yu and Perkovsky, EE},
    journal={Paleontological Journal},
    volume={43},
    number={9},
    pages={1097--1100},
    year={2009},
    publisher={SP MAIK Nauka/Interperiodica}
  }

  @article{nazarenko2011new,
    title={A new species of the genus Caulophilus Woll.(Coleoptera: Curculionidae: Cossoninae) from the Rovno amber},
    author={Nazarenko, V Yu and Legalov, AA and Perkovsky, EE},
    journal={Paleontological Journal},
    volume={45},
    number={3},
    pages={287--290},
    year={2011},
    publisher={SP MAIK Nauka/Interperiodica}
  }

  @article{назаренко2007долгоносикообразные,
    title={Долгоносикообразные жуки (Coleoptera, Curculionoidea) водоемов г. Киева},
    author={Назаренко, ВЮ},
    journal={VII з’їзд Укр. ентомол. т--ва, Ніжин},
    pages={14--18},
    year={2007}
  }

  @inproceedings{назаренко2012долгоносикообразные,
    title={ДОЛГОНОСИКООБРАЗНЫЕ ЖУКИ (COLEOPTERA, CURCULIONOIDEA) ПРИБРЕЖНЫХ БИОТОПОВ ОСТРОВА ОБОЛОНСКОЙ КОСЫ (г. КИЕВ)},
    author={Назаренко, Виталий Юрьевич},
    booktitle={ПРОБЛЕМЫ ИЗУЧЕНИЯ КРАЕВЫХ СТРУКТУР БИОЦЕНОЗОВ},
    pages={189--193},
    year={2012},
    organization={Изд-во Сарат. ун-та}
  }

  @inproceedings{назаренко2012долгоносикообразные,
    title={ДОЛГОНОСИКООБРАЗНЫЕ ЖУКИ (COLEOPTERA, CURCULIONOIDEA) ОСТРОВА ДИКОГО (Г. КИЕВ)},
    author={Назаренко, Виталий Юрьевич},
    booktitle={БИОРАЗНООБРАЗИЕ И УСТОЙЧИВОЕ РАЗВИТИЕ},
    pages={218--221},
    year={2012}
  }

  @inproceedings{назаренко2010водные,
    title={Водные жуки-долгоносики (Insecta: Coleoptera, Curculionoidea) Днепровских островов г. Киева},
    author={Назаренко, В. Ю.},
    booktitle={Экология водных беспозвоночных},
    pages={210--211},
    year={2010},
    organization={Принтхаус}
  }

  @article{пучков2013предварительный,
    title={Предварительный обзор колеоптерофауны (Coleoptera) Казантипского природного заповедника},
    author={Пучков, АВ and Маркина, ТЮ and Назаренко, ВЮ and Петренко, АА and Прохоров, АВ and Черней, ЛС},
    journal={Наукові записки Державного природознавчого музею},
    number={29},
    pages={129--135},
    year={2013},
    publisher={Національна академія наук України}
  }

`;

const output = [ { citationKey: 'nazarenko2009new',
    entryType: 'article',
    entryTags:
     { title: 'A new genus and species of dryophthorid weevils (Coleoptera, Dryophthoridae: Stromboscerinae) from the Rovno amber',
       author: 'Nazarenko, V Yu and Perkovsky, EE',
       journal: 'Paleontological Journal',
       volume: '43',
       number: '9',
       pages: '1097--1100',
       year: '2009',
       publisher: 'SP MAIK Nauka/Interperiodica' } },
  { citationKey: 'nazarenko2011new',
    entryType: 'article',
    entryTags:
     { title: 'A new species of the genus Caulophilus Woll.(Coleoptera: Curculionidae: Cossoninae) from the Rovno amber',
       author: 'Nazarenko, V Yu and Legalov, AA and Perkovsky, EE',
       journal: 'Paleontological Journal',
       volume: '45',
       number: '3',
       pages: '287--290',
       year: '2011',
       publisher: 'SP MAIK Nauka/Interperiodica' } },
  { citationKey: 'назаренко2007долгоносикообразные',
    entryType: 'article',
    entryTags:
     { title: 'Долгоносикообразные жуки (Coleoptera, Curculionoidea) водоемов г. Киева',
       author: 'Назаренко, ВЮ',
       journal: 'VII з’їзд Укр. ентомол. т--ва, Ніжин',
       pages: '14--18',
       year: '2007' } },
  { citationKey: 'назаренко2012долгоносикообразные',
    entryType: 'inproceedings',
    entryTags:
     { title: 'ДОЛГОНОСИКООБРАЗНЫЕ ЖУКИ (COLEOPTERA, CURCULIONOIDEA) ПРИБРЕЖНЫХ БИОТОПОВ ОСТРОВА ОБОЛОНСКОЙ КОСЫ (г. КИЕВ)',
       author: 'Назаренко, Виталий Юрьевич',
       booktitle: 'ПРОБЛЕМЫ ИЗУЧЕНИЯ КРАЕВЫХ СТРУКТУР БИОЦЕНОЗОВ',
       pages: '189--193',
       year: '2012',
       organization: 'Изд-во Сарат. ун-та' } },
  { citationKey: 'назаренко2012долгоносикообразные',
    entryType: 'inproceedings',
    entryTags:
     { title: 'ДОЛГОНОСИКООБРАЗНЫЕ ЖУКИ (COLEOPTERA, CURCULIONOIDEA) ОСТРОВА ДИКОГО (Г. КИЕВ)',
       author: 'Назаренко, Виталий Юрьевич',
       booktitle: 'БИОРАЗНООБРАЗИЕ И УСТОЙЧИВОЕ РАЗВИТИЕ',
       pages: '218--221',
       year: '2012' } },
  { citationKey: 'назаренко2010водные',
    entryType: 'inproceedings',
    entryTags:
     { title: 'Водные жуки-долгоносики (Insecta: Coleoptera, Curculionoidea) Днепровских островов г. Киева',
       author: 'Назаренко, В. Ю.',
       booktitle: 'Экология водных беспозвоночных',
       pages: '210--211',
       year: '2010',
       organization: 'Принтхаус' } },
  { citationKey: 'пучков2013предварительный',
    entryType: 'article',
    entryTags:
     { title: 'Предварительный обзор колеоптерофауны (Coleoptera) Казантипского природного заповедника',
       author: 'Пучков, АВ and Маркина, ТЮ and Назаренко, ВЮ and Петренко, АА and Прохоров, АВ and Черней, ЛС',
       journal: 'Наукові записки Державного природознавчого музею',
       number: '29',
       pages: '129--135',
       year: '2013',
       publisher: 'Національна академія наук України' } } ]
test('Russian should work', t => t.deepEqual(output, bibtexParse.toJSON(input)));
