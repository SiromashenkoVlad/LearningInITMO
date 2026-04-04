package org.example;

import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(run("https://openrouter.ai/api/v1/chat/completions"));
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }

    static String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String instruction = """
                Далее, с новой строки следует запрос пользователя. В ответ на все запросы ты всегда должен возвращать только JSON представление объекта вида "\\s*(\\{.*?\\})\\s*", без дополнительных объяснений, комментариев, пояснений, форматирования, маркапов и т.п. Ответ должен всегда десериализоваться в объект. Все символы должны быть в utf8 и не должны быть URL Encoded. в качестве ответа, который соответствует классу ResponseFromModel json shema этого класса:
                        Пояснение по status:
                        FINISH — можно показывать пользователю результат
                        EXTRA_DATA_REQUEST — нужно запросить доп. данные
                        CONFIRM — требуется подтверждение
                        EXECUTE — нужно вызвать функцию
                        NOOP — нет подходящего действия

                        {
                          "type": "object",
                          "properties": {
                            "status": {
                              "type": "string",
                              "enum": ["FINISH", "EXTRA_DATA_REQUEST", "CONFIRM", "EXECUTE", "NOOP"]
                            },
                            "answer": {
                              "type": "string"
                            }
                          },
                          "required": ["status", "answer"]
                        }""";

        BodyRequest bodyRequest = new BodyRequest("xiaomi/mimo-v2-flash", instruction, List.of(
                new Message("developer", instruction),
                new Message("user", "Ты видишь мою инструкцию?")));


        String json = new Serialization().serialize(bodyRequest);

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + System.getenv("OPENROUTER_API_KEY"))
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            System.out.println(jsonResponse);
            ChatResponse chatResponse = new DeserializationChatResponse().deserialization(jsonResponse);
            return chatResponse.choices().get(0).message().content();
        }
    }
}