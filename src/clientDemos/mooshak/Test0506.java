package clientDemos.mooshak;

import org.junit.jupiter.api.Test;
import had.test.Tests;

import java.io.File;
import java.io.IOException;


/**
 * @author hdaniel@ualg.pt
 * @version 202503171105
 */
class Test0506 {

    /*
     * Mooshak like black box tests
     */

    //Test 05 Problem L
    @Test
    public void testL() throws IOException {
        Tests tester = new Tests(new File("/storage/OneDrive-ualg/Works/Aulas/00-Disciplinas/01-Novas/Poo/POO2024-25/pratica/LabXX-age/lab5-transform/mooshak/L"),
                "input", "output");
        tester.test(new Test05L(), null);
    }

    //Test 05 Problem M
    @Test
    public void testM() throws IOException {
        Tests tester = new Tests(new File("/storage/OneDrive-ualg/Works/Aulas/00-Disciplinas/01-Novas/Poo/POO2024-25/pratica/LabXX-age/lab5-transform/mooshak/M"),
                "input", "output");
        tester.test(new Test05M(), null);
    }

    //Test 06 Problem N
    @Test
    public void testN() throws IOException {
        Tests tester = new Tests(new File("/storage/OneDrive-ualg/Works/Aulas/00-Disciplinas/01-Novas/Poo/POO2024-25/pratica/LabXX-age/lab6-collisions/mooshak/N"),
                "input", "output");
        tester.test(new Test06N(), null);
    }
}

