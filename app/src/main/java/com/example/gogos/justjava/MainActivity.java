package com.example.gogos.justjava;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean hasWhippedCream;
    private boolean hasChocolate;
    private int quantity;
    private String name;
    private int price;
    private String priceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void composeEmail(String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary() {
        priceMessage = getString(R.string.name_on_filed) + name;
        if (hasWhippedCream) {
            priceMessage += "\n" + getString(R.string.add_whipped_cream);
        }
        if (hasChocolate) {
            priceMessage += "\n" + getString(R.string.add_Chocolate);
        }
        priceMessage += "\n" + getString(R.string.Quantity) + quantity;
        priceMessage += "\n" + getString(R.string.total) + NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void calculatePrice() {
        price = 5;
        if (hasWhippedCream && hasChocolate) {
            price += 3;
        } else if (hasWhippedCream) {
            price += 1;
        } else if (hasChocolate) {
            price += 2;
        }
        price = quantity * price;
    }

    public void submitOrder(View view) {
        EditText nameOnFiled = (EditText) findViewById(R.id.name_id);
        name = nameOnFiled.getText().toString();
        calculatePrice();
        //displayMessage(createOrderSummary());
        createOrderSummary();
        composeEmail(getString(R.string.Just_java_app_order_for) + name, priceMessage);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, getString(R.string.you_can_not_have_more_than_100_cups), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, getString(R.string.you_can_not_have_less_than_1_cups), Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    public void onChecked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.whippedCream_id: {
                hasWhippedCream = checked;
                break;
            }
            case R.id.chocolate_id:
                hasChocolate = checked;
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quntity_text_view);
        quantityTextView.setText("" + number);
    }

//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
