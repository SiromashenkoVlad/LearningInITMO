    .data
output_addr:          .word  0x84  ; Output address where the result should be stored
start_stack:          .word  0x1000

    .text
_start:
    lui               t0, %hi(output_addr)
    addi              t0, t0, %lo(output_addr)
    lw                t0, 0(t0)

    lui               sp, %hi(start_stack)
    addi              sp, sp, %lo(start_stack)
    sub               sp, sp, t2
    addi              t2, t2, 0x123
    sw                t2, 0(sp)
    sw                sp, 0(t0)
    sw                t2, 0(t0)
    halt

