import time
import decryptor, serialization, additional_task_3
from additional_task_2 import *


start_time = time.time()

data = decryptor.deserialization_toml('shedule.toml')
decryptor_time = time.time()

serialization.save_yaml_to_file(data, 'data.yaml')
serialization_time = time.time()

