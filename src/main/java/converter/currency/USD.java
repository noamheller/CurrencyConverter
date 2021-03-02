package converter.currency;

import java.io.Serializable;
/**
 *
 * @author Noam Heller
 */

public class USD extends Coin implements Serializable {

    private final double value =3.52;

    /**
     * class getter
     * @return value
     */
    @Override
    public double getValue() {

        return value;
    }

    /**
     * calculates by currency value
     * @param input the amount of money to convert
     * @return convert value
     */
    @Override
    public double calcualte(double input) {

        return input * getValue();
    }
}
