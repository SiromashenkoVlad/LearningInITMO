    .data
buffer:               .byte '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_'

offset:               .word  0x20
left_lower_border:    .word  0x61
right_lower_border:   .word  0x7A
input_addr:           .word  0x80; Input address where the number 'n' is stored
output_addr:          .word  0x84; Output address where the result should be stored
buffer_addr:          .word  0x00
buffer_start:         .word  0x00
buffer_end:           .word  0x20
end_read_character:   .word  0x0A
step_for_buffer:      .word  0x01
input_too_big:        .word  0xcccccccc


    .text
_start:
    load              input_addr
    load_acc
    sub               end_read_character
    beqz              end_reading
    add               end_read_character
    jmp               check_borders_upper_words
make_bufer_step:
    load_addr         buffer_addr
    add               step_for_buffer
    sub               buffer_end
    bgez              not_in_domain
    add               buffer_end
    store_addr        buffer_addr
    jmp               _start


    .data

    .text
    .org 0x88
end_reading:
    add               c_end_character
    store_ind         buffer_addr
    load_addr         buffer_start
    store_addr        buffer_addr
    jmp               output_cycle


check_borders_upper_words:
    sub               left_lower_border
    bltz              write_char_after_left_check
    add               left_lower_border
    
    sub               right_lower_border
    bgtz              write_char_after_right_check
    add               right_lower_border

    sub               offset


save_char:
    store_ind         buffer_addr
    jmp               make_bufer_step


write_char_after_left_check:
    add               left_lower_border
    jmp               save_char


write_char_after_right_check:
    add               right_lower_border
    jmp               save_char


output_cycle:
    load_addr         buffer_addr
    load_acc
    and               mask_for_reading
    beqz              end_programm

    store_ind         output_addr

    load_addr         buffer_addr
    add               step_for_buffer
    store_addr        buffer_addr
    jmp               output_cycle


end_programm:
    load_addr         buffer_start
    store_addr        buffer_addr
    halt


not_in_domain:
    load_addr         input_too_big
    store_ind         output_addr
    halt

    .data
end_c_string:         .word  0x00
mask_for_reading:     .word  0x000000ff
c_end_character:      .word  0x5f5f5f00

