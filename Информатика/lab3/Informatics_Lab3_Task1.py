# Author = Siromashenko Vladislav Romanovich
# Group = P3109
# Date = 05.10.2025

import re


# def check_count_vowels(text):
#     return len(re.findall(rf'[{vowels}]', text)) # переделать поиск глассных, регулярка должна больше работы выполнять
#
#
# def new_check_count_vowels(text):
#     return len(re.findall(rf'[{vowels}]', text))
#
#
# def check_haiku(text):
#     lines = [line.strip() for line in text.split('/')]
#
#     if len(lines) != 3:
#         return "Не хайку. Должно быть 3 строки."
#
#     syllable_counts = [check_count_vowels(line) for line in lines]
#
#     if syllable_counts == [5, 7, 5]:
#         return "Хайку!"
#     else:
#         return "Не хайку."


vow = 'аеёиоуыэюяАЕЁИОУЫЭЮЯ'

def check_haiku(text):
    if re.fullmatch(rf"\s*([^/]*)\s*/\s*([^/]*)\s*/\s*([^/]*)\s*", text):
        vow_5 = f"([^/{vow}]*)[{vow}]" * 5 + f"([^/{vow}]*)"
        vow_7 = f"([^/{vow}]*)[{vow}]" * 7 + f"([^/{vow}]*)"
        if re.fullmatch(rf"{vow_5}/{vow_7}/{vow_5}", text):
            return "Хайку!"
        else:
            return "Не хайку."
    else:
        return "Не хайку. Должно быть 3 строки."


test_cases = [
    {
        "Ввод": "Старый пруд. / Прыгнула в воду лягушка, / Всплеск в тишине.",
        "Вывод": "Не хайку."
    },
    {
        "Ввод": "Весенний дождь / Бесконечные струи",
        "Вывод": "Не хайку. Должно быть 3 строки."
    },
    {
        "Ввод": "Вишня цветёт / Под ветром лепестки кружат / Ночь",
        "Вывод": "Не хайку."
    },
    {
        "Ввод": "Пчёлочки. Жужжат...,? / Собирают усердно / Цветёт сад весной!",
        "Вывод": "Хайку!"
    },
    {
        "Ввод": "   / Луна над озером светит   / Отражение в воде   ",
        "Вывод": "Не хайку."
    }
]

for test in test_cases:
    result = check_haiku(test['Ввод'])
    print(f"Ввод: {test}")
    print(f"Вывод: {result}")
    print(f'Программа отработала корректно: {result == test['Вывод']}\n')
