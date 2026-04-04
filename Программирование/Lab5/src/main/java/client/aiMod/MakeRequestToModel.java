package client.aiMod;

import client.aiMod.communicateWithModel.*;
import client.aiMod.jsoncatch.Deserialization;
import client.aiMod.jsoncatch.Serialization;
import com.fasterxml.jackson.core.JsonParseException;
import okhttp3.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MakeRequestToModel {
    private static final OkHttpClient client = new OkHttpClient();

    public ResponseFromModel execute(String zapros) throws IOException, JsonParseException {
        String instruction = Files.readString(Path.of("instruction.txt"));
        BodyRequest bodyRequest = new BodyRequest("xiaomi/mimo-v2-flash", instruction, List.of(
                new MyMessage("developer", instruction),
                new MyMessage("user", zapros)));


        String json = new Serialization().serialize(bodyRequest);
        Request request = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .header("Authorization", "Bearer " + System.getenv("OPENROUTER_API_KEY"))
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        try (Response response = client.newCall(request).execute()) {
            Deserialization deserialization = new Deserialization();

            String jsonResponse = response.body().string();

            System.out.println(jsonResponse);
            ChatResponse chatResponse = new Deserialization().deserialization(jsonResponse, ChatResponse.class);

            String content = chatResponse.choices().get(0).messageFromModel().content();
            return deserialization.deserialization(content, ResponseFromModel.class);
        } catch (SocketTimeoutException e){
            System.out.println("Время выполнения запроса истекло. Попробуйте ещё раз");
            return new ResponseFromModel(AiStatus.ERROR, "", null);
        }
    }
}
