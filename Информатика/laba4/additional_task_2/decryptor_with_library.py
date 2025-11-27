import tomllib
from pathlib import Path


def get_file_from_parent(filename):
    return Path(__file__).parent.parent / filename

def deserialization_toml(filename):
    with open(get_file_from_parent(filename), 'rb') as f:
        config = tomllib.load(f)
        return config
