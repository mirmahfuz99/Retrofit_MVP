package com.mirmahfuz.retrofit_mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mirmahfuz.retrofit_mvp.data.model.Post;
import com.mirmahfuz.retrofit_mvp.data.remote.APIService;
import com.mirmahfuz.retrofit_mvp.data.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mResponse;

    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponse = (TextView) findViewById(R.id.tv_response);


        mAPIService = ApiUtils.getAPIService();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)){
                    sendPost(title, body);
                }
            }
        });

    }

    public void showErroeMessage() {
        Toast.makeText(this, "Error submitting post ", Toast.LENGTH_SHORT).show();
    }

    private void sendPost(String title, String body) {

        mAPIService.savePost(title, body, 1).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful()){
                    showResponse(response.body().toString());
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

                showErroeMessage();
                Log.e(TAG, "Unable to submit post a API");

            }
        });

    }

    private void showResponse(String response) {

        if (mResponse.getVisibility() == View.GONE) {
            mResponse.setVisibility(View.VISIBLE);
        }
        mResponse.setText(response);

    }


}
























