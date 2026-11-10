#include <stdio.h>

extern void print_string(const char* a);
extern void world();


int main() {
    print_string("hello");
    world();
}
