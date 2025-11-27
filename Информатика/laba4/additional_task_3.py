import decryptor


def to_xml(data, root_tag='root', indent_size=2, current_indent=0):
    space = " " * current_indent

    if data is None:
        return f"{space}<{root_tag}></{root_tag}>"

    elif isinstance(data, (str, int, float, bool)):
        escaped_value = _escape_xml(str(data))
        return f"{space}<{root_tag}>{escaped_value}</{root_tag}>"

    elif isinstance(data, list):
        if not data:
            return f"{space}<{root_tag}></{root_tag}>"

        lines = []
        for item in enumerate(data):
            lines.append(to_xml(item, 'item', indent_size, current_indent))

        return "\n".join(lines)

    elif isinstance(data, dict):
        # Словари - вложенные элементы
        if not data:
            return f"{space}<{root_tag}></{root_tag}>"

        lines = [f"{space}<{root_tag}>"]
        next_indent = current_indent + indent_size

        for key, value in data.items():
            # Обрабатываем ключи для валидных XML имен
            valid_key = _make_valid_xml_name(str(key))

            if isinstance(value, (dict, list)) and value:
                # Сложные значения - рекурсивный вызов
                lines.append(to_xml(value, valid_key, indent_size, next_indent))
            else:
                # Простые значения
                if value is None:
                    lines.append(f"{' ' * next_indent}<{valid_key}></{valid_key}>")
                else:
                    escaped_value = _escape_xml(str(value))
                    lines.append(f"{' ' * next_indent}<{valid_key}>{escaped_value}</{valid_key}>")

        lines.append(f"{space}</{root_tag}>")
        return "\n".join(lines)

    else:
        # Другие типы
        escaped_value = _escape_xml(str(data))
        return f"{space}<{root_tag}>{escaped_value}</{root_tag}>"


def _escape_xml(text):
    """Экранирование XML специальных символов"""
    escapes = {
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&apos;'
    }

    result = []
    for char in text:
        result.append(escapes.get(char, char))
    return ''.join(result)


def _make_valid_xml_name(name):
    if not name:
        return 'item'

    # Разрешенные символы в XML именах
    valid_chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-.:"

    result = []
    first_char = True

    for char in name:
        if char in valid_chars:
            if first_char and char in '0123456789-.:':
                result.append('_')
            result.append(char)
            first_char = False
        else:
            result.append('_')

    if not result:
        return 'item'

    result_str = ''.join(result)
    if result_str.lower().startswith('xml'):
        result_str = '_' + result_str

    return result_str


def save_xml_to_file(data, filepath, root_tag='root', indent_size=2, encoding='utf-8'):
    xml_content = to_xml(data, root_tag, indent_size)
    full_xml = f'<?xml version="1.0" encoding="{encoding}"?>\n{xml_content}'

    with open(filepath, 'w', encoding=encoding) as f:
        f.write(full_xml)

    return full_xml


print(to_xml(decryptor.deserialization_toml('shedule.toml')))