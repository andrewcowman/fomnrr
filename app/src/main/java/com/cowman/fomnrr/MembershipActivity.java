package com.cowman.fomnrr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.cowman.requests.MembershipRequest;
import com.cowman.utils.VolleyController;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MembershipActivity extends AppCompatActivity {

    private Spinner _stateSpinner;
    private Spinner _donationSpinner;

    private TextInputEditText _nameEdit;
    private TextInputEditText _organizationEdit;
    private TextInputEditText _addressEdit;
    private TextInputEditText _cityEdit;
    private TextInputEditText _emailEdit;
    private TextInputEditText _phoneEdit;

    private LinearLayout _checkWrapper;
    private LinearLayout _creditWrapper;

    private AwesomeValidation _validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        _stateSpinner = (Spinner)findViewById(R.id.membership_spinner_state);
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, R.layout.view_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _stateSpinner.setAdapter(stateAdapter);

        _donationSpinner = (Spinner)findViewById(R.id.membership_spinner_donation);
        ArrayAdapter<CharSequence> donationAdapter = ArrayAdapter.createFromResource(this,
                R.array.membership_array, android.R.layout.simple_spinner_item);
        donationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _donationSpinner.setAdapter(donationAdapter);

        _nameEdit = (TextInputEditText)findViewById(R.id.membership_edit_name);
        _organizationEdit = (TextInputEditText)findViewById(R.id.membership_edit_company);
        _addressEdit = (TextInputEditText)findViewById(R.id.membership_edit_address);
        _cityEdit = (TextInputEditText)findViewById(R.id.membership_edit_city);
        _emailEdit = (TextInputEditText)findViewById(R.id.membership_edit_email);
        _phoneEdit = (TextInputEditText)findViewById(R.id.membership_edit_phone);

        _checkWrapper = (LinearLayout)findViewById(R.id.membership_check_wrapper);
        _creditWrapper = (LinearLayout)findViewById(R.id.membership_credit_wrapper);

        _validator = new AwesomeValidation(ValidationStyle.BASIC);

        _validator.addValidation(_nameEdit, ".+", "Name is required.");
        _validator.addValidation(_addressEdit, ".+", "Address is required.");
        _validator.addValidation(_cityEdit, ".+", "City is required.");
        _validator.addValidation(_emailEdit, Patterns.EMAIL_ADDRESS, "Please enter a valid email address.");
        _validator.addValidation(_phoneEdit, "[0-9]{10,13}", "Please enter a valid phone number.");
    }

    public void submitMembership(View view) {
        _validator.validate();

        if(!CheckForErrors()) {
            try {
                final JSONObject jsonBody = new JSONObject();

                jsonBody.put("Name", _nameEdit.getText());
                jsonBody.put("Organization", _organizationEdit.getText());
                jsonBody.put("Address", _addressEdit.getText());
                jsonBody.put("City", _cityEdit.getText());
                jsonBody.put("State", _stateSpinner.getSelectedItem().toString());
                jsonBody.put("Email", _emailEdit.getText());
                jsonBody.put("Phone", _phoneEdit.getText());
                jsonBody.put("MembershipType", _donationSpinner.getSelectedItem().toString());
                jsonBody.put("PaymentType", "Check");

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://api.cowman.a2hosted.com/api/membership/submit", jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error has occurred, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });

                VolleyController.getInstance(getApplicationContext()).addToRequestQueue(request);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean CheckForErrors() {
        return _nameEdit.getError() != null || _addressEdit.getError() != null || _cityEdit.getError() != null || _emailEdit.getError() != null || _phoneEdit.getError() != null;
    }

    public void onRadioButtonClicked(View view) {
        String optionText = ((RadioButton)view).getText().toString();

        if(optionText.equals("Check")) {
            _checkWrapper.setVisibility(View.VISIBLE);
            _creditWrapper.setVisibility(View.GONE);
        } else {
            _checkWrapper.setVisibility(View.GONE);
            _creditWrapper.setVisibility(View.VISIBLE);
        }
    }

    public void sendToWebsite(View view) {
        Uri webpage = Uri.parse("http://www.fomnrr.org/join.html");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
