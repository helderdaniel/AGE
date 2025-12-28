package clientDemos.mooshak.finalval;

import had.test.IRunnable;
import had.test.Tests;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


/**
 * @author hdaniel@ualg.pt
 * @version 202505011705
 */
class TestX {

    /*
     * Mooshak like black box tests
     */

    IRunnable[] tests = {
            new TestX025(),
            new TestX050(),
            new TestX075(),
            new TestX100(),
            new TestX200()
    };

    @Test
    public void test() throws IOException {
        for (IRunnable test : tests) {
            String testClassName = test.getClass().getSimpleName();
            String testFolder = "/storage/OneDrive-ualg/Works/Aulas/00-Disciplinas/01-Novas/Poo/POO2024-25/pratica/LabXX-age/labX-valFinal/mooshak/" +
                                testClassName;
            Tests tester = new Tests(new File(testFolder), "input", "output");
            tester.test(test, null);
        }
    }

}

