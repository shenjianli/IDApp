package com.shenjianli.idapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.shen.netclient.NetClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private final int MY_SCAN_REQUEST_CODE = 1988;

    @Bind(R.id.scan_result_tv)
    TextView scanResultTv;
    @Bind(R.id.id_scan_btn)
    Button idScanBtn;
    @Bind(R.id.id_query_btn)
    Button idQueryBtn;
    @Bind(R.id.id_content_et)
    EditText idContentEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void scan(View v) {
        Intent scanIntent = new Intent(this, CardIOActivity.class);
        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
            scanResultTv.setText(resultDisplayStr);

        }
        // else handle other activity results
    }

    @OnClick({R.id.id_scan_btn, R.id.id_query_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_scan_btn:
                scan(view);
                break;
            case R.id.id_query_btn:
                queryIdInfo();
                break;
        }
    }

    private void queryIdInfo() {
        String content = idContentEt.getText().toString();
        if(!TextUtils.isEmpty(content)){
            IDQueryApi idQueryApi = NetClient.retrofit().create(IDQueryApi.class);
            Map<String,String> params = new HashMap<>();
            params.put("cardno",content);
            params.put("dtype","json");
            Call<IDData> idDataCall = idQueryApi.queryIdData(params,Constant.KEY);
            idDataCall.enqueue(new RetrofitCallback<IDData>() {
                @Override
                public void onSuccess(IDData idData) {
                    if(null != idData){
                        String resultcode = idData.getResultcode();
                        String result = null;
                        ResultBean result1 = idData.getResult();
                        if("200".equals(resultcode)){
                            if(null != result1){
                                result = "性别：" + result1.getSex() + "\n";
                                result += "生日：" + result1.getBirthday() + "\n";
                                result += "地址：" + result1.getArea();
                            }
                        }
                        else {
                            result = idData.getReason() + "\n";
                            result += result1.getVerify();
                        }
                        scanResultTv.setText(result);
                    }
                }

                @Override
                public void onFail(String errorMsg) {

                }
            });
        }
    }
}
