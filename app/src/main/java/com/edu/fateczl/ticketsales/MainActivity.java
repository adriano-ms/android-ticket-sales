package com.edu.fateczl.ticketsales;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.appsearch.GetSchemaResponse;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.edu.fateczl.ticketsales.model.Ticket;
import com.edu.fateczl.ticketsales.model.VipTicket;

/**
 * @author Adriano M Sanchez
 */
public class MainActivity extends AppCompatActivity {

    private RadioGroup rgTicketType;
    private RadioButton rbNormalTicket;
    private RadioButton rbVipTicket;

    private TextView tvValue;
    private TextView tvFinalValue;
    private TextView tvVipFinalValueNote;

    private EditText etBuyerFunction;

    private Button btBuy;

    private float ticketPrice;
    private float convenienceFee;
    private int count;

    private Ticket currentTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeViews();
        setupActivity();
    }

    private void initializeViews(){
        rgTicketType = findViewById(R.id.rgTicketType);
        rbNormalTicket = findViewById(R.id.rbNormalTicket);
        rbVipTicket = findViewById(R.id.rbVipTicket);

        tvValue = findViewById(R.id.tvValue);
        tvFinalValue = findViewById(R.id.tvFinalValue);
        tvVipFinalValueNote = findViewById(R.id.tvVipFinalValueNote);

        etBuyerFunction = findViewById(R.id.etBuyerFunction);

        btBuy = findViewById(R.id.btBuy);
    }

    @SuppressLint("DefaultLocale")
    private void setupActivity(){
        ticketPrice = (float) Math.random() * 150f + 100f;
        convenienceFee = (float) Math.random() * 5f + 5f;
        Intent i = getIntent();
        count = i.getExtras() != null ? i.getExtras().getInt("count") : 0;
        rgTicketType.setOnCheckedChangeListener((rg, rb) -> switchTicketType(rb == rbVipTicket.getId()));
        rbNormalTicket.setChecked(true);
        switchTicketType(false);
        btBuy.setOnClickListener(bt -> {
            try {
                buy();
            } catch (Exception e) {
                new AlertDialog.Builder(this).setTitle(getString(R.string.txt_error_alert_title)).setMessage(e.getMessage()).show();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void switchTicketType(boolean isVip){
        if(isVip){
            tvVipFinalValueNote.setVisibility(View.VISIBLE);
            etBuyerFunction.setVisibility(View.VISIBLE);
            currentTicket = (currentTicket == null || !currentTicket.getClass().equals(VipTicket.class) ?
                    new VipTicket(String.format("%s %d", getString(R.string.txt_ticket_id_prefix), count), ticketPrice, null) : currentTicket);
        } else {
            tvVipFinalValueNote.setVisibility(View.INVISIBLE);
            etBuyerFunction.setVisibility(View.INVISIBLE);
            currentTicket = (currentTicket == null || !currentTicket.getClass().equals(Ticket.class) ?
                    new Ticket(String.format("%s %d", getString(R.string.txt_ticket_id_prefix), count), ticketPrice) : currentTicket);
        }
        tvValue.setText(String.format("%s %.2f", getString(R.string.tv_value_prefix), currentTicket.getValue()));
        tvFinalValue.setText(String.format("%s %.2f", getString(R.string.tv_final_value_prefix), currentTicket.finalValue(convenienceFee)));
    }

    private void buy() throws Exception {
        boolean isVip = currentTicket.getClass().equals(VipTicket.class);
        if(isVip) {
            String buyerFunction = etBuyerFunction.getText().toString();
            if (buyerFunction.isEmpty() || buyerFunction.isBlank())
                throw new Exception(getString(R.string.txt_invalid_buyer_function_exception));
            ((VipTicket)currentTicket).setBuyerFunction(buyerFunction);
        }
        count++;
        nextActivity(isVip);
    }

    private void nextActivity(boolean isVip){
        Bundle bundle = new Bundle();
        bundle.putString("id", currentTicket.getId());
        bundle.putFloat("finalValue", currentTicket.finalValue(convenienceFee));
        bundle.putBoolean("isVip", isVip);
        bundle.putInt("count", count);
        if(isVip)
            bundle.putString("function", ((VipTicket)currentTicket).getBuyerFunction());

        Intent intent = new Intent(this, SaleDataActivity.class);
        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
    }
}