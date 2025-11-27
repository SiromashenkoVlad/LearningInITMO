# Author = Siromashenko Vladislav Romanovich
# Group = P3109
# Date = 05.10.2025

import re


def word_with_one_vowel(text):
    vowels = 'аеёиоуыэюя'
    words = re.findall(r'\b[а-яё]+\b', text.lower())

    result_words = []
    for word in words:
        unique_vowels = set(re.findall(rf'[{vowels}]+', word.lower())) # переделать поиск глассных, регулярка должна больше работы выполнять
        if len(unique_vowels) == 1:
            result_words.append(word)

    return list(sorted(list(set(result_words)), key=lambda x: (len(x), x)))


test_cases = [
    {
        'Ввод' : 'кот сон стол стул брат лес',
        'Вывод' : ['кот', 'лес', 'сон', 'брат', 'стол', 'стул']
    },
    {
        'Ввод' : 'молоко трава кокос',
        'Вывод' : ['кокос', 'трава', 'молоко']
    },
    {
        'Ввод' : 'окно трава дом поле вода рот',
        'Вывод' : ['дом', 'рот', 'окно', 'трава']
    },
    {
        'Ввод' : "ПрОрОК, 'бЛеСк' - ВСПЛЕСК; метро/фон? тон!",
        'Вывод' : ['тон', 'фон', 'блеск', 'пророк', 'всплеск']
    },
    {
        'Ввод': "мёд плёс тётя сёстры берёза акварель фотография",
        'Вывод': ['мёд', 'плёс']
    }
]


for test in test_cases:
    result = word_with_one_vowel(test['Ввод'])
    print(f"Ввод: {test['Ввод']}")
    print(f"Вывод: {result}")
    print(f'Программа отработала корректно: {result == test['Вывод']}\n')
