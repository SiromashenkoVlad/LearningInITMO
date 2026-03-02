package client.readArguments;

import client.Console.Console;
import client.Interrogator;
import common.Exceptions.IncorrectDataInput;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeRead {
    public LocalDateTime read(Console console, Interrogator interrogator){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        for (int i = 0; i < 5; ++i){
            console.print("Введите дату и время вашего рождения (например, 28.02.2026 15:30): ");
            String input = interrogator.getUserScanner().nextLine();

            try {
                LocalDateTime dateTime = LocalDateTime.parse(input, formatter);
                return dateTime;
            } catch (Exception e) {
                console.println("Ошибка! Неверный формат даты.");
            }
        }
        throw new IncorrectDataInput("Ошибка в вводе даты");
    }
}
