package com.example.pablo.recevoir_bdd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://10.151.23.73/JoinMe/JoinMe.php" ; //working
    private  static final String URL2 = "http://10.151.23.73/JoinMe/JoinMe_send.php" ;
    private RequestQueue requestQueue ;
    private Button button ;
    private Button buttonSend ;

    private StringRequest request ;
    private TextView textView1,textView2, textView3 ;
    private EditText prenom,nomEntrer, id ;
    private String[] nom ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = (TextView)findViewById(R.id.txt1);
        textView2 = (TextView)findViewById(R.id.txt2) ;
        textView3 = (TextView)findViewById(R.id.txt3) ;

        prenom = (EditText)findViewById(R.id.prenom) ;
        nomEntrer = (EditText)findViewById(R.id.nom) ;
        id = (EditText)findViewById(R.id.id) ;


        button = (Button)findViewById(R.id.button) ;
        buttonSend = (Button)findViewById(R.id.buttonSend) ;


        requestQueue = Volley.newRequestQueue(this) ;


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getBaseContext(),"clique",Toast.LENGTH_SHORT).show();
                request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(),"il y a une reponse",Toast.LENGTH_SHORT).show();
                        try {
                             // JSONObject jsonObject = new JSONObject(response);
                            // String nom = jsonObject.getString("Nom") ;
                            JSONArray jsonArray = new JSONArray(response) ;


                                textView2.setText(jsonArray.toString()) ;


                        } catch (JSONException e) {
                            e.printStackTrace();
                            textView3.setText(e.toString());
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView3.setText(error.toString());
                    }
                }) ;

                requestQueue.add(request);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"reponse",Toast.LENGTH_LONG).show();


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                        textView1.setText(error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();
                        hashMap.put("prenom",prenom.getText().toString());
                        hashMap.put("nom",nomEntrer.getText().toString());
                        hashMap.put("id",id.getText().toString()) ;

                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}



