    .data

input_addr:      .word  0x80               ; Input address where the number 'n' is stored
output_addr:     .word  0x84               ; Output address where the result should be stored

    .text
    .org 0x88
_start:
    lui                   t0, %hi(input_addr)             ; int * input_addr_const = 0x00;
    addi                  t0, t0, %lo(input_addr)         ; // t0 <- 0x00;

    lw                    t0, 0(t0)         ; t0 - адрес чтения данных
    lw                    t1, 0(t0)         ; t1 - количество данных
    xor                   a0, a0, a0        ; обнуление a0 - нашей переменной для младшей суммы
    xor                   a1, a1, a1        ; обнуление a1 - нашей переменной для старшей суммы
    xor                   t4, t4, t4
    addi                  t4, t4, 1         ; переменная для вычитания оставшейся длины
    xor                   t5, t5, t5        ; переменная для сравнения итоговой суммы младшего слова с исходным значением


    cycle_reading:
        beqz              t1, end_reading
        lw                t2, 0(t0)
        srai              t3, t2, 31

        add               t5, a0, t2
        sub               t1, t1, t4
        bgtu              a0, t5, plus_carry
        add               a0, a0, t2
        add               a1, a1, t3
        j                 cycle_reading
        plus_carry:
            add           a0, a0, t2
            add           a1, a1, t3
            addi          a1, a1, 1
            j             cycle_reading


    end_reading:
        lui               t0, %hi(output_addr)            ; int * output_addr_const = 0x04;
        addi              t0, t0, %lo(output_addr)        ; // t0 <- 0x04;

        lw                t0, 0(t0)                       ; int output_addr = *output_addr_const;

        sw                a1, 0(t0)                       ; *output_addr_const = acc;
        sw                a0, 0(t0)

        halt

