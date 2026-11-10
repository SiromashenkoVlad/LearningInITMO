package main.java;

import com.fastcgi.FCGIInterface;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Main {
  public static void main(String[] args) {
    var fcgiInterface = new FCGIInterface();
    Set<Double> xset = Set.of(-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0);
    Set<Double> rset = Set.of(1.0, 1.5, 2.0, 2.5, 3.0);
    BigDecimal minusThree = BigDecimal.valueOf(-3.0);
    BigDecimal five = BigDecimal.valueOf(5.0);

    while (fcgiInterface.FCGIaccept() >= 0) {
      long startTime = System.nanoTime();
      try{
        String body = readRequestBody();

        Map<String, String> params = parseBody(body);

        if (!params.containsKey("x") | !params.containsKey("y") | !params.containsKey("r")){
          String error = """
        {
          "status" : "error",
          "message" : "Передайте все данные из формы"
          }
        """;
          System.out.print(error);
          continue;
        }
        try {
//          Double x = Double.parseDouble(params.get("x"));
//          Double y = Double.parseDouble(params.get("y"));
//          Double r = Double.parseDouble(params.get("r"));

          BigDecimal x = new BigDecimal(params.get("x"));
          BigDecimal y = new BigDecimal(params.get("y"));
          BigDecimal r = new BigDecimal(params.get("r"));

          if (!xset.contains(x.doubleValue())
                  | !(y.compareTo(minusThree) > 0 && y.compareTo(five) < 0)
                  | !rset.contains(r.doubleValue())){
            String error = """
          {
          "status" : "error",
          "message" : "Передайте данные в соответсвии с областью допустимых значений"
          }
          """;
            System.out.print(error);
            continue;
          }

          boolean status = checkHit(x, y, r);

          long endTyme = System.nanoTime();
          double executeTime = (endTyme - startTime) / 1000000.0;
          String currentTime = java.time.ZonedDateTime.now(java.time.ZoneId.of("Europe/Moscow"))
                  .format(java.time.format.DateTimeFormatter.ofPattern("dd:MM:yyyy HH:mm:ss"));
          String answerJson = """
          {
            "status" : "ok",
            "x" : %.1f,
            "y" : %.1f,
            "r" : %.1f,
            "hit" : %b,
            "executionTime" : "%.4f",
            "currentTime" : "%s"
          }
          """;
          answerJson = String.format(java.util.Locale.US, answerJson, x, y, r, status, executeTime, currentTime);
          System.out.print(answerJson);
        } catch (NumberFormatException e){
          String error = """
        {
          "status" : "error",
          "message" : "%s"
          }
        """.formatted("Прошу передавать числа в форму");
          System.out.print(error);
        }
      } catch (IOException e) {
          String error = """
        {
          "status" : "error",
          "message" : "%s"}
        """.formatted(e.getMessage());
        System.out.print(error);
      }
    }
  }

  private static boolean checkHit(BigDecimal x, BigDecimal y, BigDecimal r) {
    BigDecimal zero = BigDecimal.ZERO;
    if (x.compareTo(zero) >= 0 && y.compareTo(zero) >= 0 &&
            x.compareTo(r) <= 0 && y.compareTo(r) <= 0) {
      return true;
    }
    else if (x.compareTo(zero) < 0 && y.compareTo(zero) >= 0) {
      BigDecimal xSquared = x.multiply(x);
      BigDecimal ySquared = y.multiply(y);
      BigDecimal rSquared = r.multiply(r);
      if (xSquared.add(ySquared).compareTo(rSquared) <= 0) {
        return true;
      }
    }
    else if (x.compareTo(zero) <= 0 && y.compareTo(zero) < 0) {
      BigDecimal rDiv2 = r.divide(new BigDecimal("2"), BigDecimal.ROUND_HALF_UP);
      BigDecimal rightSide = x.negate().subtract(rDiv2);

      if (y.compareTo(rightSide) >= 0) {
        return true;
      }
    }
    return false;
  }


  private static Map<String, String> parseBody(String body){
    Map<String, String> params = new HashMap<>();
    String[] paramsWithoutSplit = body.split("&");
    for (int i = 0; i < paramsWithoutSplit.length; i++){
      String[] oneParam = paramsWithoutSplit[i].split("=");
      String key = java.net.URLDecoder.decode(oneParam[0], StandardCharsets.UTF_8);
      String val = java.net.URLDecoder.decode(oneParam[1], StandardCharsets.UTF_8);
      params.put(key, val);
    }
    return params;
  }


  private static String readRequestBody() throws IOException {
    FCGIInterface.request.inStream.fill();
    var contentLength = FCGIInterface.request.inStream.available();
    var buffer = ByteBuffer.allocate(contentLength);
    var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0,
        contentLength);
    var requestBodyRaw = new byte[readBytes];
    buffer.get(requestBodyRaw);
    buffer.clear();
    return new String(requestBodyRaw, StandardCharsets.UTF_8);
  }
}
