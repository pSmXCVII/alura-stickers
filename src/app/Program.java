package app;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

import utils.ConfigProps;
import utils.JsonParser;

public class Program {
    public static void main(String[] args) throws Exception {

        // System.setOut(new PrintStream(System.out, true, "UTF-8"));
        // Fazer conexão HTTP e obter os filmes Top 250
        var api = new ConfigProps();
        var address = URI.create(api.getPropValue("url"));

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(address).GET().build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        var body = response.body();

        // Extrair dados necessários (Título, Poster e Classificação)
        var parser = new JsonParser();
        List<Map<String, String>> moviesList = parser.parse(body);

        // Exibir/Manipular os dados
        for (Map<String,String> movie : moviesList) {
            System.out.print("Título: ");
            System.out.print("\033[1m");
            System.out.print(movie.get("title"));
            System.out.println("\033[0m");
            System.out.print("Poster: ");
            System.out.print("\033[1m");
            System.out.print(movie.get("image"));
            System.out.println("\033[0m");
            var rating = (int) Math.round(Double.parseDouble(movie.get("imDbRating")));
            System.out.print("Classificação: ");
            if (rating <= 7) {
                System.out.print("\033[48;2;255;160;122m\033[30m");
            } else if (rating >= 9) {
                System.out.print("\033[48;2;0;100;0m\033[97m");
            } else {
                System.out.print("\033[48;2;255;255;204m\033[30m");
            }
            System.out.printf(movie.get("imDbRating").replace('.', ','));
            System.out.println("\033[0m");
            for (int i = 0; i < rating; i++) {
                System.out.print("⭐");
            }
            System.out.println();
            System.out.println("--------------------------------------------------------");

        }
    }
}
