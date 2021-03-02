package converter.api;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import converter.Result;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a currency converter program in java using a REST API.
 * program will convert currency between converter USD (United States Dollars),
 * converter ILS (Israeli Shekel) and converter EUR (the official currency of the European Union).
 *
 * @author Noam Heller
 * @see <a href="https://currencyscoop.com/">https://currencyscoop.com/</a>
 */


public class RestApi {

    /**
     * check if users choice is valid (1/2/3)
     *
     * @param usersChoice represents users choice of converter.
     * @return boolean true if valid, false if not.
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
     * getting conversion value from REST Api
     * serializing Json conversion value into a result obj
     * if connection to server unavailable / problem with json, using default rate.
     *
     * @returns result
     */
    private Result USDtoILS(double toConvert) {

        String responseStr = null;
        String json = null;
        HttpRequest request = null;
        HttpResponse<String> response =null;
        Result result = new Result();
        ObjectMapper objectMapper = new ObjectMapper();
        String toConvertStr= String.valueOf(toConvert);
        String api = "https://api.currencyscoop.com/v1/convert" +
                "?api_key=0f8fe60d9fa9226633238b2059d01de5&base=USD&to=ILS&amount="+toConvertStr;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(api)).build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseStr = response.body();
            responseStr = responseStr.substring(responseStr.lastIndexOf(":") + 1);
            responseStr = responseStr.substring(0, responseStr.indexOf("}"));
            System.out.println(toConvert + " dollars equals to " + responseStr + " shekels");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 3.52");
            responseStr= String.valueOf(3.52*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"1\"}";
            System.out.println(toConvert + " dollars equals to " + responseStr + " shekels");
            try {
                result = objectMapper.readValue(json, Result.class);
            } catch (JsonProcessingException ee) {
                ee.printStackTrace();
            }
            return result;
        }
        json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"1\"}";

        try {
            result = objectMapper.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 3.52");
            responseStr= String.valueOf(3.52*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"1\"}";
            System.out.println(toConvert + " dollars equals to " + responseStr + " shekels");
            return result;
        }
        return result;
    }
    /**
     * getting conversion value from REST Api
     * serializing Json conversion value into a result obj
     *if connection to server unavailable / problem with json, using default rate.
     *
     * @returns result
     */
    private Result ILStoUSD(double toConvert) {

        String responseStr = null;
        String json = null;
        HttpRequest request = null;
        HttpResponse<String> response =null;
        Result result = new Result();
        ObjectMapper objectMapper = new ObjectMapper();
        String toConvertStr= String.valueOf(toConvert);
        String api = "https://api.currencyscoop.com/v1/convert" +
                "?api_key=0f8fe60d9fa9226633238b2059d01de5&base=ILS&to=USD&amount="+toConvertStr;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(api)).build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseStr = response.body();
            responseStr = responseStr.substring(responseStr.lastIndexOf(":") + 1);
            responseStr = responseStr.substring(0, responseStr.indexOf("}"));
            System.out.println(toConvert + " shekels equals to " + responseStr + " dollars");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 0.28");
            responseStr= String.valueOf(0.28*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"2\"}";
            System.out.println(toConvert + " shekels equals to " + responseStr + " dollars");
            try {
                result = objectMapper.readValue(json, Result.class);
            } catch (JsonProcessingException ee) {
                ee.printStackTrace();
            }
            return result;
        }

        json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"2\"}";

        try {
            result = objectMapper.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 0.28");
            responseStr= String.valueOf(0.28*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"2\"}";
            System.out.println(toConvert + " shekels equals to " + responseStr + " dollars");
            return result;
        }
        return result;
    }

    /**
     * getting conversion value from REST Api
     * serializing Json conversion value into a result obj
     * if connection to server unavailable / problem with json, using default rate.
     *
     * @returns result
     */
    private Result EURtoILS(double toConvert) {

        String responseStr = null;
        String json = null;
        HttpRequest request = null;
        HttpResponse<String> response =null;
        Result result = new Result();
        ObjectMapper objectMapper = new ObjectMapper();
        String toConvertStr= String.valueOf(toConvert);
        String api = "https://api.currencyscoop.com/v1/convert" +
                "?api_key=0f8fe60d9fa9226633238b2059d01de5&base=EUR&to=ILS&amount="+toConvertStr;
        try {
            request = HttpRequest.newBuilder().uri(URI.create(api)).build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            responseStr = response.body();
            responseStr = responseStr.substring(responseStr.lastIndexOf(":") + 1);
            responseStr = responseStr.substring(0, responseStr.indexOf("}"));
            System.out.println(toConvert + " euros equals to " + responseStr + " shekels");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 4.23");
            responseStr= String.valueOf(4.23*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"3\"}";
            System.out.println(toConvert + " euros equals to " + responseStr + " shekels");
            try {
                result = objectMapper.readValue(json, Result.class);
            } catch (JsonProcessingException ee) {
                ee.printStackTrace();
            }
            return result;
        }
        json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"3\"}";

        try {
            result = objectMapper.readValue(json, Result.class);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("could not get rate from API using default rate: 4.23");
            responseStr= String.valueOf(4.23*toConvert);
            json = "{ \"value\":\"" + responseStr + "\", \"convertType\":\"3\"}";
            System.out.println(toConvert + " euros equals to " + responseStr + " shekels");
            return result;
        }
        return result;
    }

    /**
     * writes value list into a text file, and opens it.
     *
     * @param valuesList result type array list with users history of converting.
     */
    private void resultFileWriter (ArrayList<Result> valuesList ) {

        PrintWriter writer = null;
        File resultsFile = new File("/Users/noamheller/Documents/NOAM/WORK/Automation " +
                "Course/Project1/src/main/java/converter/api/resultsAPI.txt");
        try {

            writer = new PrintWriter(resultsFile, StandardCharsets.UTF_8);
            writer.println("your converts history: ");
            for (Result i : valuesList) {
                writer.println(i.getValue() + " , " + i.getConvertType());
            }
            writer.println("thanks for using our currency converter!");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("welcome to API currency converter! ");
        RestApi restApi = new RestApi();
        Main main = new Main();
        double input;
        String choice;
        boolean doContinue = true;
        Result result = new Result();
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
            choice = restApi.converterChoice();
            if (choice.equals("1")) {
                /**
                 *converting Dollars to Shekels
                 */
                input = restApi.valueToConvert();
                result = restApi.USDtoILS(input);
                valuesList.add(result);
            } else if (choice.equals("2")) {
                /**
                 *converting shekels to dollars
                 */
                input = restApi.valueToConvert();
                result = restApi.ILStoUSD(input);
                valuesList.add(result);
            } else if (choice.equals("3")) {
                /**
                 * converting euro to shekels
                 */
                input = restApi.valueToConvert();
                result = restApi.EURtoILS(input);
                valuesList.add(result);
            }
            doContinue = restApi.startOver();
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
        restApi.resultFileWriter(valuesList);
    }
}

