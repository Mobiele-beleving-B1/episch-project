package com.example.sprooktochtapp;

import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Button;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity implements MQTTService {
    TextView fairyTaleName;
    TextView fairyLand;
    TextView gameDescription;
    ImageView fairyTaleImage;
    FairyTale selectedFairyTale;
    Button testbutton;
    protected MQTTProfile MQTTProfile;
    private MqttAndroidClient client;
    private HashMap<String, String> data;
    private final String mainTopic = "avanstibreda/ti/1.4/B1/sprookTocht/";
    private ArrayList<MQTTCallBack> callbacks;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new HashMap<>();
        setContentView(R.layout.activity_detail);
        callbacks = new ArrayList<>();
        MQTTProfile = (MQTTProfile) getIntent().getSerializableExtra("profile");

        callbacks.add(MQTTProfile);
        Intent intent = getIntent();
        String fairyTaleInfo = intent.getStringExtra("fairy_tale_info");
        this.selectedFairyTale = FairyTaleManager.getFairyTale(fairyTaleInfo);
        String clientId = MQTTProfile.getId();

//        testbutton = (Button) findViewById(R.id.button);
        String finalClientId = clientId;
        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscribe(mainTopic + "gameData/" + selectedFairyTale.getGameNames()[0] + "/Score");
                data.put(mainTopic + "gameData/" + selectedFairyTale.getGameNames()[0] + "/Score", "");
                publish(mainTopic + "playerData/" + selectedFairyTale.getGameNames()[0], finalClientId);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        // shows the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);
        new Thread(() -> {
            client =
                    new MqttAndroidClient(getApplicationContext(), "tcp://broker.hivemq.com:1883",
                            clientId);
            connect();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        FairyTaleManager.createFairyTales();

        fairyTaleImage = (ImageView) findViewById(R.id.fairyTaleImage);
        fairyLand = (TextView) findViewById(R.id.landTextView);
        fairyTaleName = (TextView) findViewById(R.id.fairyTaleName);
        gameDescription = (TextView) findViewById(R.id.fairyTaleDescription);

                findViewById(R.id.fairyTaleName);

//        fairyTaleDescription = (TextView)

                findViewById(R.id.fairyTaleDescription);

        fairyTaleImage = (ImageView)

                findViewById(R.id.fairyTaleImage);

        fairyTaleName.setText(selectedFairyTale.getNameOfTale());
        fairyLand.setText(selectedFairyTale.getNameOfLand());
        gameDescription.setText(selectedFairyTale.getGameDescription());
        fairyTaleImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), selectedFairyTale.getImageOfTaleId(), null));
    }

    private void connect() {
        try {
            IMqttToken token = client.connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("MQTT", topic + ","+ message);
                if (data.containsKey(topic)) {
                    String text = message.toString();
                    data.put(topic, text);
                    for (MQTTCallBack callback : callbacks) {
                        callback.receive(topic, text);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
        super.getIntent().putExtra("profile", MQTTProfile);
    }

    @Override
    public boolean publish(String topic, String payload) {
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            Log.i("MQTT", "publish to " + topic + "--" + payload);
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
            return true;
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean subscribe(String topic) {
        try {
            final boolean[] succes = {false};
            Log.i("MQTT", String.valueOf(client != null));
            if (!data.containsKey(topic) && client != null) {
                IMqttToken subToken = client.subscribe(topic, 0);
                subToken.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("MQTT", topic);

                        data.put(topic, "");
                        succes[0] = true;
                        // The message was published
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken,
                                          Throwable exception) {
                        Log.e("MQTT", "failure you are");

                        succes[0] = false;
                        // The subscription could not be performed, maybe the user was not
                        // authorized to subscribe on the specified topic e.g. using wildcards

                    }
                });
            } else if (client == null) {
                Log.e("MQTT", "WTF hoe dan maat");
            }
            return succes[0];
        } catch (MqttException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void disconnect() {
        try {
            IMqttToken token = client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsubscribe(String topic) {
        try {
            IMqttToken token = client.unsubscribe(topic);
            data.remove(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}