package timbercrew.org.evosup.backgroundServices;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import ru.evotor.framework.core.action.event.receipt.position_edited.PositionAddedEvent;
import ru.evotor.framework.receipt.Position;
import ru.evotor.framework.receipt.Receipt;
import ru.evotor.framework.receipt.ReceiptApi;

public class PositionBroadcastReceiver extends BroadcastReceiver {
    //final private static String url = "http://evosup.ru:3000/api/v1.0/cheque";
    //final private static String url = "http://evosup.ru:2999";
    final private static String url = "https://requestb.in/v189quv1";

    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "UUID: " + PositionAddedEvent.create(intent.getExtras()).getReceiptUuid(), Toast.LENGTH_LONG).show();
        Receipt r = ReceiptApi.getReceipt(context,PositionAddedEvent.create(intent.getExtras()).getReceiptUuid());

        final Vector<JSONObject> positions = new Vector<>();
        for (Position p:
             r.getPositions()) {
            try {
                JSONObject tmp = new JSONObject();
                tmp.put("uuid",p.getUuid());
                tmp.put("quantity",p.getQuantity());
                tmp.put("uuid",p.getUuid());
                positions.add(tmp);
            } catch (JSONException e) {
                Log.e("jsonPosition",e.getMessage());
            }
        }
        final JSONObject jsonReceipt = new JSONObject();
        try {
            jsonReceipt.put("postions",positions);
        } catch (JSONException e) {
            Log.e("jsonReceipt",e.getMessage());
        }
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("receipt",error.getMessage());

            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return jsonReceipt.toString().getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }
}