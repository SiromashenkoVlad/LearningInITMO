package client.readArguments;

import client.console.Console;
import client.Interrogator;

public interface Reader {
    Object read(Console console, Interrogator interrogator);
}
