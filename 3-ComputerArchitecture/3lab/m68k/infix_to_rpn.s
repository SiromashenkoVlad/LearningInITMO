        .data
        .org 0xc8
input_addr:           .word 0x80
output_addr:          .word 0x84
stack_start:          .word 0x1000
buffer_start:         .word 0x00
buffer_length:        .word 0x40
ops_buffer_start:     .word 0x40
ops_buffer_end:       .word 0x80
input_line_start:     .word 0x88
input_line_end:       .word 0xc8
error_input_message:  .word 0xcccccccc

priority_plus:        .byte 1
priority_minus:       .byte 1
priority_multiply:    .byte 2
priority_divide:      .byte 2

end_line:             .byte 10
zero:                 .byte '0'
nine:                 .byte '9'
space_char:           .byte ' '


        .text
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


cycle_write_operators:
    cmp.l                 0, D2
    beq                   without_space
    move.l                0, D2
    move.l                ' ', (A1)+
    add.l                 4, D0

    without_space:
    movea.l               ops_buffer_start, A3
    move.l                (A3), D4
    
    sub.l                 4, D6
    cycle:
        cmp.l             D4, D6
        blt               end

        movea.l           D6, A3

        clr.l             D4
        cmp.b             '(', (A3)
        beq               return_error
        
        movea.l           ops_buffer_start, A6
        move.l            (A6), D4

        clr.l             D3
        move.b            (A3), D3
        move.l            D3, (A1)+
        add.l             4, D0
        sub.l             4, D6
            
        move.b            7(A2), D3
        move.l            D3, (A1)+
        add.l             4, D0
        jmp               cycle
 

loop:
        clr.l             D7
        move.b            (A0), D7
        add.l             1, D1
        movea.l           D1, A0

        clr.l             D3
        move.b            4(A2), D3
        cmp.l             D3, D7
        beq               cycle_write_operators
        move.l            ' ', D3
        cmp.l             D3, D7
        beq               space
        
        move.b            5(A2), D3
        cmp.l             D3, D7
        blt               check_for_operators
        
        move.b            6(A2), D3
        cmp.l             D3, D7
        bgt               check_for_operators

        move.l            D7, (A1)+
        


        add.l             4, D0
        add.l             1, D2
        jmp               inc_D0

        check_for_operators:
            cmp.l         0, D2
            beq           operator_with_space
            move.l        0, D2
            move.l        ' ', (A1)+
            add.l         4, D0

            operator_with_space:
            jsr           type_of_operator
            cmp.l         0, D3
            beq           check_lscob
            move.l        D4, D5

            cycle_for_oper:
            movea.l       ops_buffer_start, A3
            cmp.l         (A3), D6
            beq           add_char

            move.l        D6, D3
            sub.l         4, D3
            movea.l       D3, A3

            move.l        (A3), D3
            cmp.b         '(', D3
            beq           add_char
            
            move.l        D7, D2
            move.l        (A3), D7
            jsr           type_of_operator
            move.l        D2, D7
            cmp.l         D4, D5
            bgt           add_char

            sub.l         4, D6
            movea.l       D6, A3
            clr.l         D4
            move.b        (A3), D4
            move.l        D4, (A1)+
            add.l         4, D0
            move.b        7(A2), D4
            move.l        D4, (A1)+
            add.l         4, D0
            jmp           cycle_for_oper
            add_char:
                movea.l   D6, A3
                move.l    D7, (A3)
                add.l     4, D6
                clr.l     D2
                jmp       inc_D0                    


        check_lscob:
            cmp.b         '(', D7
            bne           check_rscob
            jmp           add_char

        check_rscob:
            move.l        1, D4
            cmp.b         ')', D7
            bne           return_error

            cycle_for_rscop:
            movea.l       ops_buffer_start, A3
            cmp.l         (A3), D6
            beq           check_for_empty_stack

            move.l        D6, D3
            sub.l         4, D3
            movea.l       D3, A3
            move.l        (A3), D4

            cmp.l         '(', D4
            beq           check_for_empty_stack
            clr.l         D4
            move.b        (A3), D4
            move.l        D4, (A1)+
            add.l         4, D0
            move.b        7(A2), D4
            move.l        D4, (A1)+
            


            add.l         4, D0
            sub.l         4, D6
            jmp           cycle_for_rscop

            check_for_empty_stack:
                movea.l   ops_buffer_start, A3
                move.l    (A3), D3
                
                move.l    1, D4
                cmp.l     D3, D6
                beq       return_error
                sub.l     4, D6
                jmp       inc_D0
            

        return_error:
            cmp.l         1, D4
            bne           dont_read_input_for_end
            clr.l         D7
            move.b        (A0), D7


            add.l         1, D1
            movea.l       D1, A0

            clr.l         D3
            move.b        4(A2), D3
            cmp.l         D3, D7
            bne           return_error
            dont_read_input_for_end:
            move.l        -1, D4
            move.l        D4, (A5)
            halt

        space:
            cmp.l         0, D2
            beq           inc_D0
            move.l        0, D2
            move.l        ' ', (A1)+
            add.l         4, D0

        inc_D0:
            cmp.l         D0, (A4)
            ble           return_error
            jmp           loop

        end:
            rts
        

read_line:
    clr.l                 D4
    movea.l               input_line_start, A3
    move.l                (A3), D0
    movea.l               (A3), A3

    movea.l               input_line_end, A4
    move.l                (A4), D1



    read_cycle:
        movea.l           input_line_end, A4
        move.l            (A4), D1


        cmp.l             D0, D1
        ble               error_end
        move.l            (A0), D2
        move.b            D2, (A3)
        add.l             1, D0
        movea.l           D0, A3
        
        cmp.b             D2, 4(A2)
        
        beq               end_reading
        jmp               read_cycle

    error_end:
        movea.l           error_input_message, A0
        move.l            (A0), D4
        move.l            D4, (A5)
        move.l            1, D4

    end_reading:
        rts
           
_start:
    movea.l               input_addr, A0
    movea.l               (A0), A0

    movea.l               priority_plus, A2

    movea.l               output_addr, A5
    movea.l               (A5), A5

    movea.l               stack_start, A7
    movea.l               (A7), A7

    jsr                   read_line

    cmp.l                 1, D4
    beq                   end_programm

    clr.l                 D2

    movea.l               input_line_start, A0
    movea.l               (A0), A0

    movea.l               buffer_start, A1
    movea.l               (A1), A1

    movea.l               priority_plus, A2

    movea.l               buffer_length, A4
    
    movea.l               buffer_start, A6
    move.l                (A6), D0

    movea.l               input_line_start, A6
    move.l                (A6), D1

    movea.l               ops_buffer_start, A6
    move.b                (A6), D6


    jsr                   loop

    movea.l               buffer_start, A0
    move.l                (A0), D1

    sub.l                 4, D0
cycle_output:
    cmp.l                 D1, D0
    ble                   end_programm
    movea.l               D1, A3
    move.l                (A3), (A5)
    add.l                 4, D1
    jmp                   cycle_output
    
end_programm:
    halt



