ASM=nasm
ASMFLAGS=-f elf64 -i asm/
LD=ld

lib.o: asm/lib.asm
    $(ASM) $(ASMFLAGS) -o lib.o lib.asm

# b.o зависит от a.o но также и транзитивно от a.asm. 
# Не нужно указывать транзитивные зависимости, make их понимает

b.o: start.asm asm/lib.o
    $(ASM) $(ASMFLAGS) -o start.o start.asm

c.o: c.asm a.o
    $(ASM) $(ASMFLAGS) -o c.o c.asm

program: a.o b.o c.o
    $(LD) -o program a.o b.o c.o
