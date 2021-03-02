package converter;

/**
 * @author Noam Heller
 */

public class Result {
    private double value;
    private String convertType;

    /**
     * constructor
     */
    public Result() {
    }

    /**
     * base on convert type, declares value of parameters
     *
     * @param value value of converting
     * @param convertType kind of converting that was done
     */
    public Result(double value, String convertType) {

        this.value = value;
        switch (convertType) {
            case ("1") -> this.convertType = "Dollars to Shekels";
            case ("2") -> this.convertType = "Shekels to Dollars";
            case ("3") -> this.convertType = "Euro to Shekels";
            default -> this.convertType = "null";
        }
    }

    /**
     * getter
     */
    public double getValue() {
        return value;
    }

    /**
     * getter
     */
    public String getConvertType() {
        return convertType;
    }

    /**
     * setter (in use while json serialization.)
     */
    public void setConvertType(String convertType) {
        switch (convertType) {
            case ("1") -> this.convertType = "Dollars to Shekels";
            case ("2") -> this.convertType = "Shekels to Dollars";
            case ("3") -> this.convertType = "Euro to Shekels";
            default -> this.convertType = "null";
        }
    }
}
