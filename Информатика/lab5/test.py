import pandas as pd
from tabulate import tabulate

data = pd.read_excel('lab5.ods', engine='odf', usecols='A:C,E,G:Y', nrows=16, header=None)
df = pd.DataFrame(data)
df = df.fillna('')


def combine_g_to_y(row):
    values = []
    for i in range(6, 25):
        if str(row[i]).strip():
            values.append('.' if row[i] == '.' else str(int(row[i])))
    return ' '.join(values)


df[25] = df.apply(combine_g_to_y, axis=1)
df = df.drop(columns=list(range(6, 25)))

# ANSI коды цветов для границ
BORDER_COLOR = '\033[96m'  # Голубой
RESET = '\033[0m'


def create_colored_bordered_table(df):
    # Сначала создаем обычную таблицу
    table_str = tabulate(df, tablefmt='fancy_grid', showindex=False)
    lines = table_str.split('\n')

    # Раскрашиваем ВСЕ границы
    for i in range(len(lines)):
        # Заменяем символы границ и добавляем цвет
        colored_line = lines[i]
        # Раскрашиваем все символы границ
        colored_line = colored_line.replace('╒', f'{BORDER_COLOR}╒{RESET}')
        colored_line = colored_line.replace('╕', f'{BORDER_COLOR}╕{RESET}')
        colored_line = colored_line.replace('╘', f'{BORDER_COLOR}╘{RESET}')
        colored_line = colored_line.replace('╛', f'{BORDER_COLOR}╛{RESET}')
        colored_line = colored_line.replace('│', f'{BORDER_COLOR}│{RESET}')
        colored_line = colored_line.replace('┃', f'{BORDER_COLOR}┃{RESET}')
        colored_line = colored_line.replace('─', f'{BORDER_COLOR}─{RESET}')
        colored_line = colored_line.replace('═', f'{BORDER_COLOR}═{RESET}')
        colored_line = colored_line.replace('┼', f'{BORDER_COLOR}┼{RESET}')
        colored_line = colored_line.replace('┬', f'{BORDER_COLOR}┬{RESET}')
        colored_line = colored_line.replace('╤', f'{BORDER_COLOR}╤{RESET}')
        colored_line = colored_line.replace('┴', f'{BORDER_COLOR}┴{RESET}')
        colored_line = colored_line.replace('╧', f'{BORDER_COLOR}╧{RESET}')
        colored_line = colored_line.replace('├', f'{BORDER_COLOR}├{RESET}')
        colored_line = colored_line.replace('┤', f'{BORDER_COLOR}┤{RESET}')
        colored_line = colored_line.replace('┌', f'{BORDER_COLOR}┌{RESET}')
        colored_line = colored_line.replace('┐', f'{BORDER_COLOR}┐{RESET}')
        colored_line = colored_line.replace('└', f'{BORDER_COLOR}└{RESET}')
        colored_line = colored_line.replace('┘', f'{BORDER_COLOR}┘{RESET}')
        lines[i] = colored_line

    return '\n'.join(lines)


custom_table = create_colored_bordered_table(df)
print(custom_table)