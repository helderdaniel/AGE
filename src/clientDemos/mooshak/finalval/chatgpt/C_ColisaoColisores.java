package clientDemos.mooshak.finalval.chatgpt;

/**
 * @author hdaniel@ualg.pt
 * @version 202505022158
 */

/*
resposta do chatGPT à prompt "implementar em java ... com o input, output e exemplo dos slides":

Para implementar a funcionalidade que verifica se dois colisores estão em colisão em Java,
você pode utilizar o código abaixo. O programa lê as coordenadas de um ponto A, um ângulo de rotação,
a distância do ponto B em relação a A e os raios dos colisores centrados em A e B.
Em seguida, ele determina se os colisores estão em colisão.
 */
import java.util.Scanner;

public class C_ColisaoColisores {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Entrada das coordenadas e parâmetros
        System.out.print("Digite as coordenadas x e y do ponto A: ");
        double xA = scanner.nextDouble();
        double yA = scanner.nextDouble();

        System.out.print("Digite o ângulo de rotação (em graus): ");
        double angulo = scanner.nextDouble();

        System.out.print("Digite o afastamento de B a A: ");
        double afastamento = scanner.nextDouble();

        System.out.print("Digite o raio do colisor em A: ");
        double raioA = scanner.nextDouble();

        System.out.print("Digite o raio do colisor em B: ");
        double raioB = scanner.nextDouble();

        // Conversão do ângulo para radianos
        double anguloRad = Math.toRadians(angulo);

        // Cálculo das coordenadas do ponto B
        double xB = xA + afastamento * Math.cos(anguloRad);
        double yB = yA + afastamento * Math.sin(anguloRad);

        // Cálculo da distância entre os centros dos colisores
        double distancia = Math.sqrt(Math.pow(xB - xA, 2) + Math.pow(yB - yA, 2));

        // Verificação de colisão
        if (distancia <= (raioA + raioB)) {
            System.out.println(1); // Colisão
        } else {
            System.out.println(0); // Sem colisão
        }

        scanner.close();
    }
}

