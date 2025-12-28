package clientDemos.mooshak.finalval.chatgpt;

/**
 * @author hdaniel@ualg.pt
 * @version 202505020033
 */
public class Ponto {
    private double x;
    private double y;
    private double raio;

    public Ponto(double x, double y, double raio) {
        this.x = x;
        this.y = y;
        this.raio = raio;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRaio() {
        return raio;
    }

    public void mover(double distancia, double angulo) {
        // Converte o ângulo de graus para radianos
        double radianos = Math.toRadians(angulo);
        // Atualiza as coordenadas do ponto
        this.x += distancia * Math.cos(radianos);
        this.y += distancia * Math.sin(radianos);
    }

    public boolean colide(Ponto outro) {
        // Verifica se os círculos colidem
        double dx = this.x - outro.getX();
        double dy = this.y - outro.getY();
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia <= (this.raio + outro.getRaio());
    }

    public static void main(String[] args) {
        // Exemplo de entrada: 1 1 180 1 1 1 0.3 6
        double x1 = 0; // Coordenada x do primeiro ponto
        double y1 = 0; // Coordenada y do primeiro ponto
        double angulo = -90; // Ângulo de rotação
        double afastamento = 8; // Afastamento do segundo ponto
        double raio1 = 1.8; // Raio do colisor do primeiro ponto
        double raio2 = 1; // Raio do colisor do segundo ponto
        double distanciaPorPasso = -1; // Distância a deslocar em cada passo
        int numeroDePassos = 10; // Número de passos

        // Criação dos pontos
        Ponto ponto1 = new Ponto(x1, y1, raio1);
        Ponto ponto2 = new Ponto(x1 + afastamento * Math.cos(Math.toRadians(angulo)),
                y1 + afastamento * Math.sin(Math.toRadians(angulo)),
                raio2);

        int colisaoCount = 0;

        // Mover o segundo ponto e verificar colisões
        for (int i = 0; i < numeroDePassos; i++) {
            ponto2.mover(distanciaPorPasso, angulo);
            if (ponto1.colide(ponto2)) {
                colisaoCount++;
            }
        }

        // Saída do número de colisões
        System.out.println(colisaoCount);
    }
}
