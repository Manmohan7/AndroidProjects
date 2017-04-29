/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "JustJava Order for " + getName());
        intent.putExtra(intent.EXTRA_TEXT, orderSummary(calculatePrice()));
        if(intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private String checkCheckBox()
    {
        String s = "";
        if(((CheckBox)findViewById(R.id.whipped_cream)).isChecked())
            s += "\nAdd Whipped Cream";
        if(((CheckBox)findViewById(R.id.chocolate)).isChecked())
            s += "\nAdd Chocolate";
        return s;
    }

    private String getName()
    {
        return ((EditText) findViewById(R.id.name)).getText().toString();
    }

    private String orderSummary(int price)
    {
        return "Name: "+ getName() + checkCheckBox() + "\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
    }

    public void increment(View view)
    {
        if(quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 cups of Coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    public void decrement(View view)
    {
        if(quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    private int calculatePrice()
    {
        int price = 5;
        if(((CheckBox)findViewById(R.id.whipped_cream)).isChecked())
            price += 1;
        if(((CheckBox)findViewById(R.id.chocolate)).isChecked())
            price += 2;
        return price * quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}