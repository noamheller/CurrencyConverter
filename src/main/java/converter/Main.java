package converter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import converter.currency.Coin;
import converter.currency.CoinFactory;
import converter.currency.Coins;

/**
 * This is a currency converter program in java.
 * program will convert currency between converter USD (United States Dollars),
 * converter ILS (Israeli Shekel) and converter EUR (the official currency of the European Union).
 *
 * @author Noam Heller
 */

public class Main {

    /**
     * check if users choice is valid (1/2/3)
     *
     * @param usersChoice represents users choice of converter.
     * @return true if valid, false if not.
     */
    private boolean isValid(String usersChoice){
        return usersChoice.equals("1") || usersChoice.equals("2") || usersChoice.equals("3");
    }

    /**
     * asks user to make a choice,if valid returns users choice, if not - let him try again.
     *
     * @return users choice
     */
    private String converterChoice(){

        Scanner in = new Scanner(System.in);
        System.out.println("please choose an option 1/2/3");
        System.out.println("1. Dollars to Shekels");
        System.out.println("2. Shekels to Dollars");
        System.out.println("3. Euro to Shekels");
        String userConvertChoice = in.next();
        while (!isValid(userConvertChoice)){
            System.out.println("Invalid Choice, please try again: ");
            userConvertChoice = in.next();
        }
            return userConvertChoice;
        }

    /**
     * asks user to enter amount of money to convert.
     * catches exception of invalid input, let user choose again until input is value.
     *
     * @return amount of money user entered
     */
    private double valueToConvert(){

        Scanner in = new Scanner(System.in);
        double amount = 0;
        try {
            System.out.println("please enter an amount to convert: ");
            String stringAmount = in.next();
            amount = Double.parseDouble(stringAmount);
            return amount;
        } catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("invalid input, numeric value needed. please try again: ");
            amount= valueToConvert();
            }
        return amount;
    }

    /**
     * choose to continue converting or not.
     * y for yes (returns true), n for no (returns false).
     * confirms input is valid
     *
     * @return true to continue, false to end
     */
    private boolean startOver () {

        Scanner in = new Scanner(System.in);
        System.out.println("do you want to start over? Y/N");
        System.out.println(" Y. yes");
        System.out.println(" N. no");
        String isContinue = in.next();
        while ((!isContinue.equals("Y")) && (!isContinue.equals("y")) &&
                (!isContinue.equals("N")) && (!isContinue.equals("n"))) {
            System.out.println("Invalid Choice, please try again: ");
            isContinue = in.next();
        }
        if (isContinue.equals("Y") || isContinue.equals("y")) {
            return true;
        } else return false;
    }

    /**
     * gets an amount to convert from dollars to shekels
     * prints the results
     *
     * @param toConvert how many dollars to convert
     * @return the value after converting
     */
    private double USDtoILS(double toConvert){

        Coin usd = CoinFactory.getCoinInstance(Coins.USD);
        assert usd != null;
        double value = usd.calcualte(toConvert);
        System.out.println(toConvert + " dollars equals to " + value + " shekels");
        return value;
    }

    /**
     * gets an amount to convert from shekels to dollars
     * prints the results
     *
     * @param toConvert how many dollars to convert
     * @return the value after converting
     */
    private double ILStoUSD (double toConvert){

        Coin ils = CoinFactory.getCoinInstance(Coins.ILS);
        double value = ils.calcualte(toConvert);
        System.out.println(toConvert + " shekels equals to " + value + " dollars");
        return value;
    }

    /**
     * gets an amount to convert from euro to shekels
     * prints the results
     *
     * @param toConvert how many dollars to convert
     * @return the value after converting
     */
    private double EURtoILS (double toConvert){

        Coin eur = CoinFactory.getCoinInstance(Coins.EUR);
        double value = eur.calcualte(toConvert);
        System.out.println(toConvert + " euros equals to " + value + " shekels");
        return value;
    }

    /**
     * writes value list into a text file, and opens it.
     *
     * @param valuesList result type array list with users history of converting.
     */
    private void resultFileWriter (ArrayList<Result> valuesList ){

        PrintWriter writer = null;
        File resultsFile = new File("/Users/noamheller/Documents/NOAM/WORK/Automation" +
                " Course/Project1/src/main/java/converter/results.txt");
        try {

            writer = new PrintWriter(resultsFile, StandardCharsets.UTF_8);
            writer.println("your converts history: ");
            for (Result i : valuesList) {
                writer.println(i.getValue() + " , " + i.getConvertType());
            }
            writer.println("thanks for using our currency converter!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert writer != null;
        writer.flush();
        writer.close();
        try {
            java.awt.Desktop.getDesktop().open(resultsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * main method
     */
        public static void main (String[]args)  {

            System.out.println("welcome to currency converter! ");
            Main main = new Main();
            double input;
            double value;
            String choice;
            boolean doContinue = true;
            ArrayList<Result> valuesList = new ArrayList<Result>();
            /**
             *declaring variables
             */

            while (doContinue) {
                /**
                 * while user willing to continue converting, he can do the whole process
                 * (choose converter kind -> choose value to convert -> choose to continue)
                 */
                //continue doing converts
                choice = main.converterChoice();
                if (choice.equals("1")) {
                    /**
                     *converting Dollars to Shekels
                     */
                    input = main.valueToConvert();
                    value= main.USDtoILS(input);
                    valuesList.add(new Result(value, choice));
                } else if (choice.equals("2")) {
                    /**
                     *converting shekels to dollars
                     */
                    input = main.valueToConvert();
                    value = main.ILStoUSD(input);
                    valuesList.add(new Result(value, choice));
                } else if (choice.equals("3")) {
                    /**
                     * converting euro to shekels
                     */
                    input = main.valueToConvert();
                    value = main.EURtoILS(input);
                    valuesList.add(new Result(value, choice));
                }
                doContinue = main.startOver();
            }

            /**
             * at the end of converting, results file opens automatically
             * @throws exception
             */

            System.out.println("your converts history: ");
            for (Result i : valuesList) {
                System.out.println(i.getValue() + " , " + i.getConvertType());
            }
            System.out.println("thanks for using our currency converter!");
            main.resultFileWriter(valuesList);

        }
    }
