
import converter.Main;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author Noam Heller
 */

public class Tests {
    @Test(priority =1)
    private void valueTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Main privateObj = new Main();
        Method privateMethod = Main.class.getDeclaredMethod("valueToConvert");
        privateMethod.setAccessible(true);
        String input = "1";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Assert.assertEquals(1.0, privateMethod.invoke(privateObj));
    }


    @Test(priority =2)
    private void resultAssertionTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double valueToConvert = 1;
        Main privateObj = new Main();
        Method privateMethod = Main.class.getDeclaredMethod("USDtoILS",double.class);
        privateMethod.setAccessible(true);
        Assert.assertEquals(3.52, privateMethod.invoke(privateObj, valueToConvert));
    }

    @Test(priority =3)
    private void resultFileTest() throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(new File("/Users/noamheller/Documents" +
                "/NOAM/WORK/Automation Course/Project1/src/main/java/converter/results.txt")));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + " \n");
        }
        System.out.println(sb);
    }
}
