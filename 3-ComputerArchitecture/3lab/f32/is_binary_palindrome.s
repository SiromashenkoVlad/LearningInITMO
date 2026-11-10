    .data
    .org 0x88
input_addr:         .word  0x80
output_addr:        .word  0x84
cycles_loop:        .word  32



    .text
_start:
    @p input_addr  b! @b
    dup
    @p cycles_loop
    is_palindrom
    halt
    
is_palindrom:
    reverse ;
    check_for_palindrom:
        drop
        drop
        a
        xor
        if its_palindrome
        0
        print_answer ;

        its_palindrome:
        1
        print_answer:
          @p output_addr a! !
          ;

    reverse:
        dup
        if check_for_palindrom
        -1 +

        >r
        dup 1 and
        if smallest_bit_zero

        a 2*
        1 +
        a!

        end_of_step ;
        smallest_bit_zero:
            a 2* a!
        end_of_step:
        2/
        r>
        reverse ;
