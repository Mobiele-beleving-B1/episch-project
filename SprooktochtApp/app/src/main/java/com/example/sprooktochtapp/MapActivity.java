package com.example.sprooktochtapp;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class MapActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter;
    TextView textView;
    private PendingIntent mPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        textView = (TextView) findViewById(R.id.goToText);

        NfcManager nfcManager = (NfcManager) getSystemService(NFC_SERVICE);
        mNfcAdapter = nfcManager.getDefaultAdapter();

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private static NdefMessage getTestMessage() {
        byte[] mimeBytes = "application/com.android.cts.verifier.nfc"
                .getBytes(Charset.forName("US-ASCII"));
        byte[] id = new byte[]{1, 3, 3, 7};
        byte[] payload = "CTS Verifier NDEF Push Tag".getBytes(Charset.forName("US-ASCII"));
        return new NdefMessage(new NdefRecord[]{
                new NdefRecord(NdefRecord.TNF_MIME_MEDIA, mimeBytes, id, payload)
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
        mNfcAdapter.setNdefPushMessageCallback((NfcAdapter.CreateNdefMessageCallback) this, this);
    }


    public NdefMessage createNdefMessage(NfcEvent event) {
        return getTestMessage();
    }


    private NdefMessage[] getNdefMessages(Intent intent) {
        Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (rawMessages != null) {
            NdefMessage[] messages = new NdefMessage[rawMessages.length];
            for (int i = 0; i < messages.length; i++) {
                messages[i] = (NdefMessage) rawMessages[i];
            }
            return messages;
        } else {
            return null;
        }
    }

    static String displayByteArray(byte[] bytes) {
        String res = "";
        StringBuilder builder = new StringBuilder().append("[");
        for (int i = 0; i < bytes.length; i++) {
            res += (char) bytes[i];
        }
        return res;
    }

    // displaying message
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        NdefMessage[] messages = getNdefMessages(intent);
        textView.setText(displayByteArray(messages[0].toByteArray()));
    }
}