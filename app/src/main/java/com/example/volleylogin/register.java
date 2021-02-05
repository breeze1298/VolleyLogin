package com.example.volleylogin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    private EditText name,email,password,c_password;
    private Button btn_register;
    private ProgressBar loading;
    String URL_REQUEST="http://192.168.43.6/APIDemo/signup.php";
    String success;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        c_password=findViewById(R.id.c_password);
        loading=findViewById(R.id.loading);
        btn_register=findViewById(R.id.btn_register);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private  void Register()
    {
        loading.setVisibility(View.VISIBLE);
        btn_register.setVisibility(View.GONE);

        String name=this.name.getText().toString().trim();
        String email=this.email.getText().toString().trim();
        String password=this.password.getText().toString().trim();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObject=new JSONObject(response);
                            success= jsonObject.getString("success");
                            Toast.makeText(register.this, success.toString(), Toast.LENGTH_SHORT).show();

                            /*if (success.equals("1"))
                            {
                                Toast.makeText(register.this, "Register Successful !", Toast.LENGTH_LONG).show();
                            }
                             else if (success.equals("0"))
                            {
                                Toast.makeText(register.this, "Error in Registration", Toast.LENGTH_SHORT).show();
                            }*/


                        }catch (JSONException e)
                        {
                            //Toast.makeText(register.this, "Response "+response, Toast.LENGTH_SHORT).show();
                            //e.printStackTrace();
                            //Log.e("CATH ERROR",e.toString());
                           // Toast.makeText(register.this, "Server Error !", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_register.setVisibility(View.VISIBLE);
                        }


                    }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Response ERROR",error.toString());
                        Toast.makeText(register.this, "Register Error ! "+error.toString() , Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_register.setVisibility(View.VISIBLE);

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params= new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };



        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}