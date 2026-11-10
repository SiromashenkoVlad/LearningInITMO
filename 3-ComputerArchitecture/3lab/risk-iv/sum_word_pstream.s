            .data
            .org 0x88
input_addr:               .word  0x80               ; Input address where the number 'n' is stored
output_addr:              .word  0x84               ; Output address where the result should be stored
start_stack:              .word  0x154


            .text
_start:
    lui                   t0, %hi(start_stack)
    addi                  t0, t0, %lo(start_stack)
    lw                    sp, 0(t0)

    lui                   a0, %hi(input_addr)
    addi                  a0, a0, %lo(input_addr)
    lw                    a0, 0(a0)         ; a0 - адрес чтения данных
    lw                    a1, 0(a0)         ; a1 - количество данных
    
    xor                   t1, t1, t1        ; обнуление t1 - переменная для очищения места в стеке для вычисления суммы
    xor                   a2, a2, a2        ; обнуление a2 - переменная со смещением
    addi                  a2, a2, 4
    sub                   sp, sp, a2
    sw                    t1, 0(sp)
    sub                   sp, sp, a2
    sw                    t1, 0(sp)


    xor                   t4, t4, t4
    addi                  t4, t4, 1         ; переменная для вычитания оставшейся длины
    xor                   t5, t5, t5        ; переменная для сравнения итоговой суммы младшего слова с исходным значением
    
    jal                   ra, cycle_reading


    lui                   t0, %hi(output_addr)
    addi                  t0, t0, %lo(output_addr)
    lw                    t0, 0(t0)

    sw                    a1, 0(t0)
    sw                    a0, 0(t0)
    halt
   



    ;t2 - переменная для загрузки добавляемого слова
    ;
    cycle_reading:
        beqz              a1, ret

        lw                t0, 0(sp)
        lw                t1, 4(sp)

        lw                t2, 0(a0)
        srai              t3, t2, 31

        add               t5, t0, t2
        sub               a1, a1, t4
        bltu              t5, t0, plus_carry
        add               t0, t0, t2
        add               t1, t1, t3
        j                 update_stack
        plus_carry:
            add           t0, t0, t2
            add           t1, t1, t3
            addi          t1, t1, 1
        update_stack:
            sw            t0, 0(sp)
            sw            t1, 4(sp)
            j             cycle_reading
        ret:
            lw            a0, 0(sp)
            lw            a1, 4(sp)
            add           sp, sp, a2
            add           sp, sp, a2
            jalr          zero, ra, 0


