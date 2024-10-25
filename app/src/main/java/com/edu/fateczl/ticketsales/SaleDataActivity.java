package com.edu.fateczl.ticketsales;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * @author Adriano M Sanchez
 */
public class SaleDataActivity extends AppCompatActivity {

    private TextView tvTicketName;
    private TextView tvTicketId;
    private TextView tvSaleValue;

    private TextView tvFunctionLabel;
    private TextView tvFunction;

    private Button btBack;

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sale_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setupActivity();
    }

    private void initializeViews() {
        tvTicketName = findViewById(R.id.tvTicketName);
        tvTicketId = findViewById(R.id.tvTicketId);
        tvSaleValue = findViewById(R.id.tvSaleValue);

        tvFunctionLabel = findViewById(R.id.tvFunctionLabel);
        tvFunction = findViewById(R.id.tvFunction);

        btBack = findViewById(R.id.btBack);
    }

    @SuppressLint("DefaultLocale")
    private void setupActivity(){
        Bundle bundle = getIntent().getExtras();
        tvTicketId.setText(bundle.getString("id"));
        tvSaleValue.setText(String.format("%s %.2f", getString(R.string.tv_sale_value_prefix), bundle.getFloat("finalValue")));
        if(bundle.getBoolean("isVip")) {
            tvTicketName.setText(getString(R.string.tv_vip_ticket_name));
            tvFunction.setText(bundle.getString("function"));
        } else {
            tvTicketName.setText(getString(R.string.tv_normal_ticket_name));
            tvFunctionLabel.setVisibility(View.INVISIBLE);
            tvFunction.setVisibility(View.INVISIBLE);
        }
        count = bundle.getInt("count");
        btBack.setOnClickListener(bt -> backActivity());
    }

    private void backActivity(){
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
    }
}