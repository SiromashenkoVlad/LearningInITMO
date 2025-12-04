# Author = Siromashenko Vladislav Romanovich
# Group = P3109
# Date = 05.10.2025

import re


def word_withoun_end(word):
    triple_endings = '''(его|ого|ему|ому|ыми|ими)'''
    if bool(re.fullmatch(rf'\w+{triple_endings}', word)):
        return word[:len(word) - 3]
    return word[:len(word) - 2]


def change_adjectives(text, number):
    endings = '''(его|ого|ему|ому|ыми|ими|ий|ый|ий|им|ым|ем|ом|ая|яя|ой|ей|ую|юю|ой|ей|ое|ее|го|ых|их|ие)'''

    adjectives = re.findall(rf'\b([А-Яа-яЁё]+{endings})\b', text.lower()) # \w находит не толлько буквы, но и цифры
    cnt_adjs = {}
    pattern = ''
    replacement = ''
    for word in adjectives:
        word_without_end = word_withoun_end(word[0])
        cnt_adjs[word_without_end] = cnt_adjs.get(word_without_end, 0) + 1
        if cnt_adjs[word_without_end] == number:
            pattern = word_without_end
            replacement = word[0]
            break
    regex = re.compile(rf'\b({pattern}{endings})\b', re.IGNORECASE)

    def replace_with_case(match):
        orig = match.group()
        if orig.isupper():
            return replacement.upper()
        elif orig[0].isupper():
            return replacement.capitalize()
        else:
            return replacement.lower()

    result = regex.sub(replace_with_case, text)
    return result


test_cases = [
    {
        'Ввод': ['''Футбольный клуб «Реал Мадрид» является 15-кратным обладателем главного
футбольного европейского трофея – Лиги Чемпионов. Данный турнир организован
Союзом европейских футбольных ассоциаций (УЕФА). Идея о континентальном
футбольном турнире пришла к журналисту Габриэлю Ано в 1955 году.''', 1]
        ,
        'Вывод': '''Футбольного клуб «Реал Мадрид» является 15-кратным обладателем главного
футбольного европейского трофея – Лиги Чемпионов. Данный турнир организован
Союзом европейских футбольного ассоциаций (УЕФА). Идея о континентальном
футбольного турнире пришла к журналисту Габриэлю Ано в 1955 году.'''
    },
    {
        'Ввод': ['Маленький мальчик держал маленькую кошку и смотрел на маленького котёнка.', 3]
        ,
        'Вывод': 'Маленького мальчик держал маленького кошку и смотрел на маленького котёнка.'
    },
    {
        'Ввод': ['Красная площадь — одно из самых известных мест Москвы. Красной она становится на закате.', 2],
        'Вывод': 'Красной площадь — одно из самых известных мест Москвы. Красной она становится на закате.'
    },
    {
        'Ввод': ['Большой театр стоит рядом с красивым зданием. Большого внимания требует большая сцена.', 1],
        'Вывод': 'Большой театр стоит рядом с красивым зданием. Большой внимания требует большой сцена.'
    },
    {
        'Ввод': ['В ВЫСОКИХ горах мы встретили ВЫСОКИЕ ели и ВЫСОКИЙ водопад.', 2],
        'Вывод': 'В ВЫСОКИЕ горах мы встретили ВЫСОКИЕ ели и ВЫСОКИЕ водопад.'
    }
]

for test in test_cases:
    result = change_adjectives(*test['Ввод'])
    print(f"Ввод: {test['Ввод'][0]}")
    print(f"Вывод: {repr(result)}")
    print(f'Программа отработала корректно: {result == test['Вывод']}\n')
