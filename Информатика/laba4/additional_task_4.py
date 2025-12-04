import time
import decryptor, serialization, additional_task_3
from additional_task_2.decryptor_with_library import *
from additional_task_2.serialization_with_library import *


start_time = time.time()
for _ in range(100):
    data = decryptor.deserialization_toml('shedule.toml')
decryptor_time = time.time()

for _ in range(100):
    serialization.save_yaml_to_file(data, 'data.yaml')
serialization_time = time.time()

for _ in range(100):
    config = deserialization_toml('shedule.toml')
good_decr_time = time.time()

for _ in range(100):
    serialization_toml_to_yaml(config)
good_ser_time = time.time()

for _ in range(100):
    additional_task_3.save_xml_to_file(data, 'res.xml')
time_xml = time.time()

print((decryptor_time - start_time))
print((serialization_time - decryptor_time))
print((good_decr_time - serialization_time))
print((good_ser_time - good_decr_time))
print((time_xml - good_ser_time))