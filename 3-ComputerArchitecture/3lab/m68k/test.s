        .data
        .org 0x40
input_addr:           .word 0x80
output_addr:          .word 0x84
stack_start:          .word 0x300
buffer_start:         .word 0x00
buffer_length:        .word 0x40
ops_buffer_start:     .word 0x40
ops_buffer_end:       .word 0x80
priority_plus:        .byte 1
priority_minus:       .byte 1
priority_multiply:    .byte 2
priority_divide:      .byte 2
end_line:             .byte 10
zero:                 .byte '0'
nine:                 .byte '9'


        .text
        .org 0x88
type_of_operator:
    cmp.l                 '+', D7
    beq                   its_plus
    cmp.l                 '-', D7
    beq                   its_minus
    cmp.l                 '*', D7
    beq                   its_multiply
    cmp.l                 '/', D7
    beq                   its_divide
    move.l                0, D3
    rts
    its_plus:
        clr.l             D4
        add.b             (A2), D4
        jmp               its_operator
    its_minus:
        clr.l             D4
        add.b             1(A2), D4
        jmp               its_operator
    its_multiply:
        clr.l             D4
        add.b             2(A2), D4
        jmp               its_operator
    its_divide:
        clr.l             D4
        add.b             3(A2), D4
    its_operator:
        move.l            1, D3
        rts

check_for_operators:
            jsr         type_of_operator
            cmp.l       0, D3
            beq         next                       ; заменить на прыжок на следующий этап
            cycle_for_oper:
            movea.l     ops_buffer_start, A3
            cmp.l       (A3), D6
            beq         add_char
            move.l      (A3), D3
            cmp.l       '(', D3
            beq         add_char
            
            move.l      D4, D5
            movea.l      D7, A5
            move.l      (A3), D7
            jsr         type_of_operator
            cmp.l       D4, D5
            bgt         add_char
            movea.l     D6, A3
            move.l      (A3), D4
            move.b      D4, (A1)
            sub.l       4, D6
            movea.l     4(A2), A3
            move.b      (A3), (A1)
            jmp         cycle_for_oper
            add_char:
                movea.l D6, A3
                move.l  D7, (A3)
                add.l   4, D6
                rts                     ; заменить на jmp inc_D0



_start:
    movea.l               input_addr, A0
    movea.l               (A0), A0

    movea.l               output_addr, A1
    movea.l               (A1), A1

    movea.l               priority_plus, A2

    movea.l               buffer_start, A7
    move.l                (A7), D0

    movea.l               buffer_length, A7
    move.l                (A7), D1

    movea.l               end_line, A7
    move.b                (A7), D2

    movea.l               ops_buffer_start, A7
    move.b                (A7), D6
    
    movea.l               stack_start, A7
    movea.l               (A7), A7

    move.l                (A0), D7                  ; подготовка к тесту

    jsr                   check_for_operators
    
    next:
    move.l                D6, (A1)
    sub.l                 4, D6
    movea.l               D6, A3

    move.l                (A3), (A1)
    
    halt
