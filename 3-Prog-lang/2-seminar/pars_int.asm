.data

message:      db   'a1jkb2kj4kn6', 0
length:       db   0

.text
global _start


print_string:
    mov  rdx, rsi
    mov  rsi, rdi
    mov  rax, 1
    mov  rdi, 1
    syscall


_start:
    
