package ir.sq.apps.sqclubside.controllers;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import ir.sq.apps.sqclubside.ConnectionUi;


public abstract class Connection extends AsyncTask<Void, Void, String> {

    private String urlString;
    private ConnectionUi connectionUi;
    private HttpURLConnection connection;
    private String data;

    public Connection(String urlString, String data, ConnectionUi connectionUi) {
        this.connectionUi = connectionUi;
        this.urlString = urlString;
        this.data = data;
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    @Override
    protected void onPreExecute() {
        connectionUi.start();
        //TODO SHOW ALERT WHEN CANCELED
        connectionUi.setOnCancelDialog(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                cancel(true);
            }
        });
    }

    @Override
    protected String doInBackground(Void... voids) {
        BufferedReader reader = null;
        String result = "notConnected";
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(1000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (data.length() > 0) {
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(UserHandler.getInstance().getmClub().toJson());
                writer.close();
                os.close();
            }
            InputStream stream;
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                stream = connection.getErrorStream();
            } else {
                stream = connection.getInputStream();
            }
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");
            result = sb.toString();
        } catch (Exception e) {
            Log.e("Exception", e.getMessage(), e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }
        endProcess(result);
        return result;
    }

    public void endProcess(String result) {
        onResult(result);
    }
    @Override
    protected void onPostExecute(String result) {
        onResult(result);
        connectionUi.end();
    }

    protected abstract void onResult(String result);

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setConnection(HttpsURLConnection connection) {
        this.connection = connection;
    }

}
