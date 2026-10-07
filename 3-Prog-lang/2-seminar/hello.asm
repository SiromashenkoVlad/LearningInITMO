section .text
global _start



g:                        ; Это метка начала функции exit
    ret
f:
    call g
    mov  rax, 60             ; Это функция exit
    xor  rdi, rdi
    syscall 

_start:
     call f
     
