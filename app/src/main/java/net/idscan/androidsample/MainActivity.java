package net.idscan.androidsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import net.idscan.parsers.DLParser;
import net.idscan.parsers.DLResult;
import net.idscan.parsers.ParserResult;
import net.idscan.parsers.interfaces.CheckLicenseNumberFormatResult;
import net.idscan.parsers.interfaces.DateFormatEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private String GetLicenseFile() {
        BufferedReader reader = null;
        StringBuilder licenseText = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("license.json")));
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                licenseText.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        return licenseText.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String licenseText = GetLicenseFile();
        DateFormatEnum dfe = DateFormatEnum.DateIsEmpty;

        DLParser p = new DLParser(licenseText);
        String value = "QAoeDUFOU0kgNjM2MDE1MDMwMDAyREwwMDQxMDIyOFpUMDI2OTAwMTVETERDQUMKRENCTk9ORQpEQ0ROT05FCkRCQTAxMDIyMDIzCkRDU0JBVENIRUxPUgpEQ1RKVU5FIERJQU5FCkRCRDA4MDQyMDE2CkRCQjAxMDIxOTYzCkRCQzIKREFZQlJPCkRBVSA2MSBJTgpEQUcxMjYzNSBMQUtFU0hPUkUgRFIKREFJTU9OVEdPTUVSWQpEQUpUWApEQUs3NzM1NiAgICAgIApEQVEyMjY4MDcxNApEQ0YwMTIxMTYyMDA4ODA0NDYxNzI0OApEQ0dVU0EKRENITk9ORQpEQVpCUk8KRENVCg1aVFpUQTIxMApaVEJXCg0=";

//        CheckLicenseNumberFormatResult r = new CheckLicenseNumberFormatResult();
//
//        CheckLicenseNumberFormatResult res = p.CheckLicenseNumberFormat("", "", "");

        // Get parser version (optional)
        String version = p.GetParserVersion();
        ParserResult result = p.Parse(value);
        // Do something with result;


        ((TextView) findViewById(R.id.tv_status)).setText(result.Status);
        ((TextView) findViewById(R.id.tv_isValid)).setText(result.ValidationCode.IsValid ? " True" : "False");
        ((TextView) findViewById(R.id.tv_first_name)).setText(result.IdentityCardPresenter.FirstName);
        ((TextView) findViewById(R.id.tv_middle_name)).setText(result.IdentityCardPresenter.MiddleName);
        ((TextView) findViewById(R.id.tv_last_name)).setText(result.IdentityCardPresenter.LastName);
        ((TextView) findViewById(R.id.tv_address)).setText(result.IdentityCardPresenter.Address1);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            ((TextView) findViewById(R.id.tv_birthdate)).setText(result.IdentityCardPresenter.Birthdate.format(formatter));
            ((TextView) findViewById(R.id.tv_expiration_date)).setText(result.IdentityCardPresenter.ExpirationDate.format(formatter));
        }


        /*
        * System.out.println("Status: "+result.Status);
        * System.out.println("Document is valid: " + result.ValidationCode.IsValid);
        * */
        ((TextView) findViewById(R.id.tv_version)).setText(version);
    }
}