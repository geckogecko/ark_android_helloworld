package steinbacher.georg.arkandroidtestapp;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.ark.core.Crypto;
import io.ark.core.Network;
import io.ark.core.Peer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Crypto.setNetworkVersion(Network.getMainnet().getPrefix());
        Log.i(TAG, "NetworkVersion: " + Crypto.getNetworkVersion().toString());


        //Request all peers
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.arkcoin.net/api/peers";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject responseJSON = new JSONObject(response);
                            JSONArray peersJSON = responseJSON.getJSONArray("peers");

                            List<Peer> peers = new ArrayList<>();
                            for(int i=0;i<peersJSON.length(); i++) {
                                JSONObject peer = (JSONObject) peersJSON.get(i);
                                peers.add(new Peer(peer.getString("ip"),
                                        peer.getInt("port")));
                            }

                            Network mainNet = Network.getMainnet();
                            mainNet.setPeers(peers);
                            mainNet.warmup();

                            Log.i(TAG, "randomPeer: " + mainNet.getRandomPeer().getIp());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
