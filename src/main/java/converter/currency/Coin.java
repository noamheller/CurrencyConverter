package converter.currency;

/**
 *
 * @author Noam Heller
 */
public abstract class Coin implements ICalcualte {
    public abstract double getValue();

    @Override
    public abstract double calcualte(double value);

}
