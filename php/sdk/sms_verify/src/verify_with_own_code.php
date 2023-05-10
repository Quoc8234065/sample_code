<?php
require __DIR__ . "/../vendor/autoload.php";
use telesign\enterprise\sdk\verify\VerifyClient;
use function telesign\sdk\util\randomWithNDigits;

# Replace the defaults below with your Telesign authentication credentials.
$customer_id = getenv('CUSTOMER_ID') ? getenv('CUSTOMER_ID') :'FFFFFFFF-EEEE-DDDD-1234-AB1234567890';
$api_key = getenv('API_KEY') ? getenv('API_KEY') :'ABC12345yusumoN6BYsBVkh+yRJ5czgsnCehZaOYldPJdmFh6NeX8kunZ2zU1YWaUw/0wV6xfw==';

# Set the default below to your test phone number. In your production code, update the phone number dynamically for each transaction.
$phone_number = getenv('PHONE_NUMBER') ? getenv('PHONE_NUMBER'):'11234567890';

# Generate OTP.
$verify_code = randomWithNDigits(5);

# Make the request and capture the response.
$verify_client = new VerifyClient($customer_id, $api_key);
$response = $verify_client->sms($phone_number, [ "verify_code" => $verify_code ]);

# Display the response body in the console for debugging purposes. In your production code, you would likely remove this.
print_r($response->json);

# Display prompt to enter OTP in the console.
# In your production code, you would instead collect the asserted OTP from the end-user in your platform's interface.

echo "Please enter the verification code you were sent: ";
$user_entered_verify_code = rtrim(fgets(STDIN));
if ($user_entered_verify_code == $verify_code) {
    echo "Your code is correct.";
} else {
    echo "Your code is incorrect.";
}

?>