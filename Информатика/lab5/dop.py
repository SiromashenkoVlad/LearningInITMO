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


def create_custom_bordered_table(df, color=None):
    table_str = tabulate(df, tablefmt='fancy_grid', showindex=False)
    lines = table_str.split('\n')
    for i in range(8, len(lines)):
        lines[i] = lines[i].replace('│', '┃')

    for i in range(8, len(lines), 24):
        lines[i] = lines[i].replace('─', '═')

    if color:
        colors = {
        'BLACK' : '\033[30m',
        'RED' : '\033[31m',
        'GREEN' : '\033[32m',
        'YELLOW': '\033[33m',
        'BLUE' : '\033[34m',
        'MAGENTA' : '\033[35m',
        'CYAN': '\033[36m',
        'WHITE': '\033[37m'
        }
        BORDER_COLOR = colors[color]
        RESET = '\033[0m'
        CHANGE_ITEM = '┃├┤┤├╛╘'
        lines[0] = ''.join([f'{BORDER_COLOR}{item}{RESET}' for item in lines[0]])
        lines[len(lines) - 1] = ''.join([f'{BORDER_COLOR}{item}{RESET}' for item in lines[len(lines) - 1]])
        for i in range(1, len(lines) - 1):
            colored_line = lines[i][:len(lines[i]) - 1] + f'{BORDER_COLOR}{lines[i][len(lines[i]) - 1]} {RESET}'
            colored_line = f'{BORDER_COLOR}{colored_line[0]}{RESET}' + colored_line[1:]
            lines[i] = colored_line



    return '\n'.join(lines)


custom_table = create_custom_bordered_table(df, 'GREEN')
print(custom_table)
