from pprint import pprint


def open_toml_file(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        content = f.readlines()
    return content


def parse_toml_array_advanced(array_str):
    """Улучшенный парсер массивов с валидацией"""
    try:
        return _parse_array_recursive(array_str.strip(), 0)
    except Exception as e:
        raise ValueError(f"Invalid array syntax: {array_str}") from e


def _parse_array_recursive(element, depth):
    """Рекурсивно парсит вложенные массивы"""

    # Базовый случай: если это не массив, парсим как простое значение
    if not (element.startswith('[') and element.endswith(']')):
        return pars_toml_value(element)

    # Рекурсивный случай: массив
    content = element[1:-1].strip()
    if not content:
        return []  # Пустой массив

    elements = []
    current = ''
    current_depth = 0
    in_string = False
    string_char = None
    escape = False

    for char in content + ',':
        if escape:
            current += char
            escape = False
            continue

        if char == '\\' and in_string:
            escape = True
            current += char
            continue

        if char in ['"', "'"] and not in_string:
            in_string = True
            string_char = char
            current += char
        elif char == string_char and in_string:
            in_string = False
            string_char = None
            current += char
        elif char == '[' and not in_string:
            current_depth += 1
            current += char
        elif char == ']' and not in_string:
            current_depth -= 1
            current += char
        elif char == ',' and not in_string and current_depth == 0:
            if current.strip():
                parsed_element = _parse_array_recursive(current.strip(), depth + 1)
                elements.append(parsed_element)
            current = ''
        else:
            current += char

    # Добавляем последний элемент
    if current.strip():
        parsed_element = _parse_array_recursive(current.strip(), depth + 1)
        elements.append(parsed_element)

    return elements


def pars_toml_value(value):
    if value.startswith('[') and value.endswith(']'):
        return parse_toml_array_advanced(value)

    if (value.startswith('"""') and value.endswith('"""')) or \
            (value.startswith("'''") and value.endswith("'''")):
        return value[3:-3]

    if value.startswith('"') and value.endswith('"'):
        content = value[1:-1]

        escape_map = {
            '\\\\': '\\',
            '\\"': '"',
            '\\n': '\n',
            '\\t': '\t',
            '\\r': '\r',
            '\\b': '\b',
            '\\f': '\f',
        }

        for escaped, unescaped in escape_map.items():
            content = content.replace(escaped, unescaped)

        return content

    if value.startswith("'") and value.endswith("'"):
        return value[1:-1]

    if value.lower() == 'true':
        return True
    if value.lower() == 'false':
        return False

    if not value or value.lower() == 'null':
        return None

    try:
        if '.' in value:
            return float(value)
        return int(value)
    except ValueError:
        raise ValueError('Ну, значениям что-то происходит.Удачи!')


def make_way(way, our_dict):
    current_place = our_dict
    for part in way:
        if part not in current_place:
            current_place[part] = {}
        current_place = current_place[part]

    return current_place


def deserialization_toml(file):
    deserialization_dict = {}
    current_place = deserialization_dict
    multi_line_str_ones_quotes = False
    multi_line_str_double_quotes = False
    key_before_multiline_str = ''
    string_in_quotes = ''

    content = open_toml_file(file)
    for st in content:
        st = st.strip()
        if not st:  # скип пустой строки
            continue

        # Обработка multi-line строк
        elif multi_line_str_ones_quotes and st.endswith("'''"):
            print(1)
            string_in_quotes += '\n' + st[:-3]
            multi_line_str_ones_quotes = False
            current_place[key_before_multiline_str] = string_in_quotes
            string_in_quotes = ''

        elif multi_line_str_double_quotes and st.endswith('"""'):
            print(2)
            string_in_quotes += '\n' + st[:-3]
            multi_line_str_double_quotes = False
            current_place[key_before_multiline_str] = string_in_quotes
            string_in_quotes = ''

        elif multi_line_str_double_quotes or multi_line_str_ones_quotes:
            print(3)
            string_in_quotes += '\n' + st

        # обработка коментов должна идти раньше других обработок
        elif '#' in st:  # Пока я бросаю все комменты в мусор(не забудь это исправить)
            st, comment = st.split('#', 1)
            st = st.strip()
            comment = comment.strip()

        # обработка элементов массива
        elif st.startswith('[[') and st.endswith(']]'):
            st = st[2:-2].strip()
            way = st.split('.')
            current_place = make_way(way[:-1], deserialization_dict)
            last_folder = way[-1]
            if last_folder in current_place:
                current_place[last_folder].append({})

            else:
                current_place[last_folder] = [{}]
            current_place = current_place[last_folder][-1]
        # Вызов ошибок
        elif st.startswith('[[') and not st.endswith(']]'):
            raise TypeError("Некорректное задание массива")

        # обработка поддеревьев
        elif st.startswith('[') and st.endswith(']'):
            st = st[1:-1].strip()
            way = st.split('.')
            current_place = make_way(way, deserialization_dict)
        # Вызов ошибок
        elif st.startswith('[') and not st.endswith(']'):
            raise TypeError("Некорректное задание массива")

        # работа с заданием значений переменных
        elif '=' in st:  # Не рассматривается случай многострочных массивов, ещё нужны многострочные строки
            key, value = st.split('=', 1)
            key = key.strip()
            value = value.strip()
            if (value.startswith('"""') and not value.endswith('"""')) or \
            (value.startswith("'''") and not value.endswith("'''")):
                if value[:3] == '"""':
                    multi_line_str_double_quotes = True
                    string_in_quotes = value[3:]
                    key_before_multiline_str = key
                else:
                    multi_line_str_ones_quotes = True
                    string_in_quotes = value[3:]
                    key_before_multiline_str = key
            else:
                value = pars_toml_value(value)  # ещё не все прописал
                current_place[key] = value

    #pprint(deserialization_dict)
    return deserialization_dict


#deserialization_toml('shedule.toml')
