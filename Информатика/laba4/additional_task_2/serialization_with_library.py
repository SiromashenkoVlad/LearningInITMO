import yaml
from pathlib import Path


def get_file_from_parent(filename):
    return Path(__file__).parent.parent / filename


def serialization_toml_to_yaml(data):
    with open('data.yaml', 'w', encoding='utf-8') as f:
        yaml.dump(data, f, default_flow_style=False, allow_unicode=True)


#serialization_toml_to_yaml(get_file_from_parent('shedule.toml'))