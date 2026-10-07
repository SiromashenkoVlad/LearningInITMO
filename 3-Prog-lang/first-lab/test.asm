
section .text

 
; Принимает код возврата и завершает текущий процесс
exit: 
    mov rax, 60
    syscall

; Принимает указатель на нуль-терминированную строку, возвращает её длину
string_length:
    sub         rsp, 8
    xor         rax, rax
    .loop:
        mov     rcx, [rdi+rax]
        cmp     byte [rdi+rax], 0
        je      .end
        inc     rax
        jmp     .loop
    .end:
        add     rsp, 8
        ret

; Принимает указатель на нуль-терминированную строку, выводит её в stdout
print_string:
    push    rdi
    call    string_length
    mov     rdx, rax
    pop     rsi
    mov     rax, 1
    mov     rdi, 1
    syscall
    ret

; Принимает код символа и выводит его в stdout
print_char:
    push    rdi
    mov     rdx, 1
    mov     rsi, rsp
    mov     rax, 1
    mov     rdi, 1
    syscall
    pop     rdi
    ret


; Переводит строку (выводит символ с кодом 0xA)
print_newline:
    sub     rsp, 8
    mov     rdi, 0x0A
    call    print_char
    add     rsp, 8
    ret


; Выводит беззнаковое 8-байтовое число в десятичном формате 
; Совет: выделите место в стеке и храните там результаты деления
; Не забудьте перевести цифры в их ASCII коды.
print_uint:
    push      rdx
    xor       rdx, rdx
    xor       r10, r10

    mov       rax, rdi
    mov       r9, rsp
    mov       r8,  10
    
    sub       rsp, 32
    mov       byte[rsp+23], 0
    lea       r11, [rsp+23]

    .loop:
        dec   r11
        div   r8
        add   rdx, 0x30
        mov   [r11], dl
        xor   rdx, rdx
        cmp   rax, 0
        je    .write_int
        jmp   .loop

    .write_int:
        mov   rdi, r11
        call  print_string
        add   rsp, 32
        pop   rdx
        ret




; Выводит знаковое 8-байтовое число в десятичном формате 
print_int:
    cmp       rdi, 0
    jl        .negative
    sub       rsp, 8
    call      print_uint
    jmp       .end
    .negative:
        push  rdi
        mov   rdi, 0x2d
        call  print_char
        pop   rdi
        neg   rdi
        sub   rsp, 8
        call  print_uint
    
    .end: 
        add   rsp, 8
        ret


; Принимает два указателя на нуль-терминированные строки, возвращает 1 если они равны, 0 иначе
string_equals:
    xor       r8, r8
    xor       r9, r9
    .loop:
        mov   r8b, byte[rdi]
        mov   r9b, byte[rsi]
        cmp   r8, r9
        jne   .not_eq
        cmp   r8, 0
        je    .eq
        inc   rdi
        inc   rsi
        jmp   .loop

    .not_eq:
        mov   rax, 0
        ret

    .eq:
        mov   rax, 1
        ret



; Читает один символ из stdin и возвращает его. Возвращает 0 если достигнут конец потока
read_char:
    sub       rsp, 1
    mov       byte [rsp], 0

    mov       rax, 0
    mov       rdi, 0
    mov       rsi, rsp
    mov       rdx, 1

    push      rcx
    push      r11
    syscall
    pop       r11
    pop       rcx
    
    cmp       rax, 1
    je        .success

    mov       rax, 0
    jmp       .end

    .success:
        xor   rax, rax
        mov   al, byte [rsp]
    .end:
        add   rsp, 1
        ret


; Принимает: адрес начала буфера, размер буфера
; Читает в буфер слово из stdin, пропуская пробельные символы в начале, .
; Пробельные символы это пробел 0x20, табуляция 0x9 и перевод строки 0xA.
; Останавливается и возвращает 0 если слово слишком большое для буфера
; При успехе возвращает адрес буфера в rax, длину слова в rdx.
; При неудаче возвращает 0 в rax
; Эта функция должна дописывать к слову нуль-терминатор

read_word:
    xor             r9, r9
    mov             r8, rdi
    sub             rsp, 8

    .loop:
        push        r8
        push        r9
        push        rdi
        push        rsi
        call        read_char
        pop         rsi
        pop         rdi
        pop         r9
        pop         r8

        cmp         rax, 0
        je          .end

        cmp         r9, 0
        jne         .check_end

        
        cmp         rax, 0x20
        je          .loop
        cmp         rax, 0x0A
        je          .loop
        cmp         rax, 0x09
        je          .loop

    .just_write:
        mov         byte [rdi], al
        inc         rdi
        inc         r9
        cmp         r9, rsi
        jl          .loop
        mov         rax, 0
        add         rsp, 8
        ret

    .check_end:
        cmp         rax, 0x20
        je          .end
        cmp         rax, 0x0A
        je          .end
        cmp         rax, 0x09
        je          .end
        jmp         .just_write


    .end:
        mov         byte[rdi], 0
        mov         rdx, r9
        mov         rax, r8
        add         rsp, 8
        ret

 

; Принимает указатель на строку, пытается
; прочитать из её начала беззнаковое число.
; Возвращает в rax: число, rdx : его длину в символах
; rdx = 0 если число прочитать не удалось
parse_uint:
    xor             rax, rax
    xor             rdx, rdx
    xor             r10, r10
    xor             r9, r9
    mov             r8, 10


    .loop:
        mov         r9b, byte [rdi+r10]
        cmp         r9, 0x30
        jl          .done
        cmp         r9, 0x39
        jg          .done

        sub         r9, 0x30
        mul         r8
        add         rax, r9

        inc         r10
        jmp         .loop

    .done:
        mov         rdx, r10
        ret



; Принимает указатель на строку, пытается
; прочитать из её начала знаковое число.
; Если есть знак, пробелы между ним и числом не разрешены.
; Возвращает в rax: число, rdx : его длину в символах (включая знак, если он был) 
; rdx = 0 если число прочитать не удалось
parse_int:
    xor             r9, r9
    mov             r9b, byte [rdi]
    cmp             r9, '-'
    je              .neg_dig
    sub             rsp, 8
    call            parse_uint
    add             rsp, 8
    ret
    .neg_dig:
        inc         rdi
        sub         rsp, 8
        call        parse_uint
        add         rsp, 8
        cmp         rdx, 0
        je          .end
        inc         rdx
        neg         rax
        .end:
            ret
        




; Принимает указатель на строку, указатель на буфер и длину буфера
; Копирует строку в буфер
; Возвращает длину строки если она умещается в буфер, иначе 0
 string_copy:
    push      rdi
    push      rsi
    push      rdx
    call      string_length
    pop       rdx
    pop       rsi
    pop       rdi
    
    cmp       rax, rdx
    jae       .too_big_string

    xor       r8, r8
    .loop:
        mov   r8b, byte[rdi]
        cmp   r8, 0
        je    .end
        mov   [rsi], r8b
        inc   rdi
        inc   rsi
        jmp   .loop
    .too_big_string:
        mov   rax, 0
        ret

    .end:
        mov   [rsi], 0
        ret


