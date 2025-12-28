package clientDemos.mooshak.finalval.chatgpt;

/**
 * @author hdaniel@ualg.pt
 * @version 202505022208
 */

/* Prompt para o copilot que usa as classes do Game Engine como pedido:
usando os métodos definidos nas classes no projeto: core.eng.Transform e core.eng.CollCircle
implementar em java: Input: 8 double representando as coordenadas x, y do ponto A,
o ângulo de rotação e o afastamento de que B está de A, o raio dos colisores centrados em A e B,
respetivamente, a distância a deslocar segundo o sentido da rotação, em cada passo, e o número de passos.

Output: 1 se os colisores estiverem em colisão; 0 caso contrário.

Exemplo Input: 1 1 180 1 1 1 0.3 6 output: 1
 */

import core.eng.CollCircle;
import core.eng.Transform;

public class B_CollisionCheckerWithGameObject {

    public static void main(String[] args) {
        // Input values
        double xA = 1; // x-coordinate of point A
        double yA = 1; // y-coordinate of point A
        double angle = 180; // rotation angle in degrees
        double distanceFromAtoB = 1; // distance of B from A
        double radiusA = 1; // radius of collider at A
        double radiusB = 1; // radius of collider at B
        double stepDistance = 0.3; // distance to move in each step
        int steps = 6; // number of steps

        // Convert angle to radians
        double angleRad = Math.toRadians(angle);

        // Create Transform for A and B
        Transform transformA = new Transform(xA, yA);
        CollCircle colliderA = new CollCircle(null, transformA, radiusA, "");

        double xB = xA + distanceFromAtoB * Math.cos(angleRad);
        double yB = yA + distanceFromAtoB * Math.sin(angleRad);
        Transform transformB = new Transform(xB, yB);
        CollCircle colliderB = new CollCircle(null, transformB, radiusB, "");

        // Check for collision during steps
        for (int i = 0; i < steps; i++) {
            if (colliderA.isColliding(colliderB)) {
                System.out.println(1); // Collision detected
                return;
            }

            // Move A in the direction of rotation
            transformA.move(stepDistance * Math.cos(angleRad), stepDistance * Math.sin(angleRad));
        }

        System.out.println(0); // No collision detected
    }
}