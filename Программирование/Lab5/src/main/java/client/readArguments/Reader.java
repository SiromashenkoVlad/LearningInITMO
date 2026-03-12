package client.readArguments;

import client.Console.Console;
import client.Interrogator;

public interface Reader {
    Object read(Console console, Interrogator interrogator);
}
