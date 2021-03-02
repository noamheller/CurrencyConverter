package converter.currency;

public class CoinFactory {
    /**
     * factory of coins
     * @param coin USD/ILS/EUR
     * @return new (specific) coin
     */
    public static Coin getCoinInstance(Coins coin){
        switch (coin){
            case USD:
                return new USD();
            case ILS:
                return new ILS();
            case EUR:
                return new EUR();
        }
        return null;
    }
}
