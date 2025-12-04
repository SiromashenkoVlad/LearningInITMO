import decryptor


def serialize_to_yaml(data, indent_size=2, current_indent=0):
    if data is None:
        return "null"
    elif isinstance(data, bool):
        return "true" if data else "false"
    elif isinstance(data, (int, float)):
        return str(data)
    elif isinstance(data, str):
        return _serialize_string(data)
    elif isinstance(data, list):
        return _serialize_list(data, indent_size, current_indent)
    elif isinstance(data, dict):
        return _serialize_dict(data, indent_size, current_indent)
    else:
        return _serialize_string(str(data))


def _serialize_string(s):
    if any(char in s for char in ['\n', '\r', '\t', '\\', '"']):
        return f"{_escape_string(s)}"
    return s


def _escape_string(s):
    escapes = {
        '\n': '\\n',
        '\r': '\\r',
        '\t': '\\t',
        '"': '\"',
        '\\': '\\\\'
    }
    result = []
    for char in s:
        result.append(escapes.get(char, char))
    return ''.join(result)


def _serialize_list(lst, indent_size, current_indent):
    if not lst:
        return "[]"

    lines = []
    indent = " " * current_indent
    next_indent = current_indent + indent_size

    for item in lst:
        if isinstance(item, (dict, list)) and item:
            serialized_item = serialize_to_yaml(item, indent_size, next_indent)
            lines.append(f"{indent}- {serialized_item.strip()}")
        else:
            serialized_item = serialize_to_yaml(item, indent_size, 0)
            lines.append(f"{indent}- {serialized_item}")

    return "\n".join(lines)


def _serialize_dict(dct, indent_size, current_indent):
    if not dct:
        return "{}"

    lines = []
    indent = " " * current_indent
    next_indent = current_indent + indent_size

    for key, value in dct.items():
        key_str = str(key)

        if isinstance(value, (dict, list)) and value:
            lines.append(f"{indent}{key_str}:")
            serialized_value = serialize_to_yaml(value, indent_size, next_indent)
            lines.append(serialized_value)
        else:
            serialized_value = serialize_to_yaml(value, indent_size, 0)
            lines.append(f"{indent}{key_str}: {serialized_value}")

    return "\n".join(lines)


def save_yaml_to_file(data, filepath, indent_size=2):
    yaml_content = serialize_to_yaml(data, indent_size)
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(yaml_content)
    return yaml_content


data = decryptor.deserialization_toml('shedule.toml')
yaml_output = serialize_to_yaml(data)
save_yaml_to_file(data, 'data.yaml')
