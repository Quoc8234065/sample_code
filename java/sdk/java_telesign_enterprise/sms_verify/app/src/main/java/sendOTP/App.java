package sendOTP;

import com.telesign.RestClient;
import com.telesign.Util;
import com.telesign.enterprise.VerifyClient;
import java.util.HashMap;
import java.util.Scanner;


public class App {
    
    public static void main(String[] args) {
        // Replace the defaults below with your Telesign authentication credentials or pull them from environment variables.
        String customerId = System.getenv().getOrDefault("CUSTOMER_ID", "FFFFFFFF-EEEE-DDDD-1234-AB1234567890");
        String apiKey = System.getenv().getOrDefault("API_KEY", "ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw=="); 
        
        // Set the default below to your test phone number or pull it from an environment variable. 
        // In your production code, update the phone number dynamically for each transaction.
        String phoneNumber = System.getenv().getOrDefault("PHONE_NUMBER", "11234567890");
 
        // Generate one-time passcode (OTP) and add it to request parameters.
        String verifyCode = Util.randomWithNDigits(5);
        HashMap<String, String> params = new HashMap<>();
        params.put("verify_code", verifyCode);

        try {
            // Instantiate a verification client object.
            VerifyClient verifyClient = new VerifyClient(customerId, apiKey);
            
            // Make the request and capture the response.
            RestClient.TelesignResponse telesignResponse = verifyClient.sms(phoneNumber, params);

            // Display the response body in the console for debugging purposes. 
            // In your production code, you would likely remove this.
            System.out.println("\n" + "Response HTTP status:" + telesignResponse.statusCode);
            System.out.println("Response body:" + telesignResponse.body + "\n");
            
            // Display prompt to enter asserted OTP in the console.
            // In your production code, you would instead collect the asserted OTP from the end-user.
            System.out.println("Please enter the verification code you were sent:");
            Scanner s = new Scanner(System.in);
            String code = s.next();

            // Determine if the asserted OTP matches your original OTP, and resolve the login attempt accordingly. 
            // You can simulate this by reporting whether the codes match.
            if (verifyCode.equalsIgnoreCase(code)) {
              System.out.println("Your code is correct.");
            } else {
              System.out.println("Your code is incorrect.");
            }
        } catch (Exception e) {
          System.out.println((char)27 + "[31m" + "\nAn exception occurred.\nERROR: " + e.getMessage());
        }
    }
}