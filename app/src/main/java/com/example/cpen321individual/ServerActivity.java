package com.example.cpen321individual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.w3c.dom.Text;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        TextView serverIpTextView = findViewById(R.id.server_ip);
        TextView serverTimeTextView = findViewById(R.id.server_time);
        TextView serverNameTextView = findViewById(R.id.my_name);

        TextView clientIpTextView = findViewById(R.id.client_ip);
        clientIpTextView.setText("Client IP: " + getLocalIpAddress());

        TextView clientTime = findViewById(R.id.client_time);
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        clientTime.setText("Client local time: " + currentTime);

        TextView googleNameTextView = findViewById(R.id.google_name);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        googleNameTextView.setText("Logged in: " + account.getDisplayName());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://20.248.173.194:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonAPI JsonApi = retrofit.create(JsonAPI.class);

        Call<Post> callIp = JsonApi.getPostIp();
        callIp.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    serverIpTextView.setText("Code: " + response.code());
                    return;
                }
                Post post = response.body();
                String content = "Server IP Address: " + post.getHostname();
                serverIpTextView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                serverIpTextView.setText(t.getMessage());
            }
        });

        Call<Post> callTime = JsonApi.getPostTime();
        callTime.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    serverTimeTextView.setText("Code: " + response.code());
                    return;
                }
                Post post = response.body();
                String content = "Server Local Time: " + post.getServerTime();
                serverTimeTextView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                serverTimeTextView.setText(t.getMessage());
            }
        });

        Call<Post> callName = JsonApi.getPostName();
        callName.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    serverNameTextView.setText("Code: " + response.code());
                    return;
                }
                Post post = response.body();
                String content = "Server IP Address: " + post.getMyName();
                serverNameTextView.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                serverNameTextView.setText(t.getMessage());
            }
        });

    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}