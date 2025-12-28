package clientDemos.mooshak.finalval.chatgpt;

/**
 * @author hdaniel@ualg.pt
 * @version 202505022148
 */
import java.util.Scanner;

/*
resposta do chatGPT à prompt "implementar em java ... com o input, output e exemplo dos slides":

Para implementar a funcionalidade desejada em Java, você pode seguir o código abaixo.
Este programa lê as coordenadas de um ponto A, um ângulo de rotação e uma distância de deslocamento,
e calcula as novas coordenadas após a operação.
*/

public class D_DeslocamentoPonto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada das coordenadas e parâmetros
        System.out.print("Digite as coordenadas x e y do ponto A: ");
        double x = scanner.nextDouble();
        double y = scanner.nextDouble();

        System.out.print("Digite o ângulo de rotação (em graus): ");
        double angulo = scanner.nextDouble();

        System.out.print("Digite a distância a deslocar: ");
        double distancia = scanner.nextDouble();

        // Conversão do ângulo para radianos
        double anguloRad = Math.toRadians(angulo);

        // Cálculo das novas coordenadas
        double novoX = x + distancia * Math.cos(anguloRad);
        double novoY = y + distancia * Math.sin(anguloRad);

        // Saída formatada
        System.out.printf("(%,.2f; %,.2f)\n", novoX, novoY);

        scanner.close();
    }
}

