package com.wei.android.lib.fingerprintidentify.demo;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;

public class MainActivity extends AppCompatActivity {

    private TextView mTvTips;
    private ScrollView mScrollView;
    private FingerprintIdentify mFingerprintIdentify;
    private Activity that = this;
    private ImageButton btnlogo;

    private  AlertDialog popupok;

    private static final int MAX_AVAILABLE_TIMES = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTvTips = (TextView) findViewById(R.id.mTvTips);
//        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        btnlogo = (ImageButton) findViewById(R.id.btnmain);
        long time = System.currentTimeMillis();
//        append("new FingerprintIdentify() ");
        mFingerprintIdentify = new FingerprintIdentify(getApplicationContext(), new BaseFingerprint.FingerprintIdentifyExceptionListener() {
            @Override
            public void onCatchException(Throwable exception) {
//                append("\nException：" + exception.getLocalizedMessage());
            }
        });

//        append("\n" + getString(R.string.time) + (System.currentTimeMillis() - time) + "ms");
//        append("\nisHardwareEnable() " + mFingerprintIdentify.isHardwareEnable());
//        append("\nisRegisteredFingerprint() " + mFingerprintIdentify.isRegisteredFingerprint());
//        append("\nisFingerprintEnable() " + mFingerprintIdentify.isFingerprintEnable());

        if (!mFingerprintIdentify.isFingerprintEnable()) {
//            append("\n" + getString(R.string.not_support));
            return;
        }

//        append("\n" + getString(R.string.click_to_start));
    }

//    private void append(String msg) {
//        mTvTips.append(msg);
//        mScrollView.post(new Runnable() {
//            @Override
//            public void run() {
//                mScrollView.fullScroll(View.FOCUS_DOWN);
//            }
//        });
//    }

    private void changebtn(String str){
        AlertDialog.Builder popupsaldo = new AlertDialog.Builder(getActivity());
        if (str == "ok"){

            popupsaldo.setMessage("Saldo R$80,85 \n - R$04,15")
                    .setTitle("Passe a Catraca");
            popupok = popupsaldo.create();

            btnlogo.setImageResource(R.drawable.logokn);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            popupok.show();
//                            Toast.makeText(that,"R$ 04,15", Toast.LENGTH_SHORT).show();
                            btnlogo.setImageResource(R.drawable.logon);
                        }
                    },
                    1500);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                              popupok.cancel();
//                            Toast.makeText(that,"R$ 04,15", Toast.LENGTH_SHORT).show();

                        }
                    },
                    3500);
        }else{

            popupsaldo.setMessage("Tente Novamente")
                    .setTitle(getString(R.string.not_pass));
            popupok = popupsaldo.create();

            btnlogo.setImageResource(R.drawable.logoerrn);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            popupok.show();
//                            Toast.makeText(that,"R$ 04,15", Toast.LENGTH_SHORT).show();
                            btnlogo.setImageResource(R.drawable.logon);
                        }
                    },
                    1500);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            popupok.cancel();
//                            Toast.makeText(that,"R$ 04,15", Toast.LENGTH_SHORT).show();

                        }
                    },
                    3500);
        }
    }


    public void start(final View view) {
//        append("\n" + getString(R.string.start));
            Toast.makeText(this,getString(R.string.start),Toast.LENGTH_SHORT).show();
        mFingerprintIdentify.startIdentify(MAX_AVAILABLE_TIMES, new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed( ) {
//                append("\n" + getString(R.string.succeed));
//                Toast.makeText(view,getString(R.string.succeed),Toast.LENGTH_LONG).show();
                changebtn("ok");
            }

            @Override
            public void onNotMatch(int availableTimes) {
//                append("\n" + getString(R.string.not_match, availableTimes));
                changebtn("not");
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
//                append("\n" + getString(R.string.failed) + " " + isDeviceLocked);
            }

            @Override
            public void onStartFailedByDeviceLocked() {
//                append("\n" + getString(R.string.start_failed));
            }
        });
    }

//    public void copy(View view) {
//        String text = mTvTips.getText().toString();
//        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
//        clipboardManager.setPrimaryClip(ClipData.newPlainText(text, text));
//        Toast.makeText(this, getString(R.string.copied), Toast.LENGTH_SHORT).show();
//    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerprintIdentify.cancelIdentify();
//        append("\n" + getString(R.string.stopped_by_life_callback));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFingerprintIdentify.cancelIdentify();
    }

    public Context getActivity() {
        return that;
    }
}
