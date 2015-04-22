package yesmen.cs2340.shoppingwithfriends;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * Class ReportedSales shows a ListView of all the sales relevant to you.
 *
 * @author      Luka Antolic-Soban, Resse Aitken, Ratchapong Tangkijvorakul, Matty Attokaren, Sunny Patel
 * @version     1.6
 */
public class ReportedSales extends Activity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText enteredItem, enteredPrice, enteredLocation, enteredQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_sales);

        Button submitButton = (Button) findViewById(R.id.reported_submit_button);
        submitButton.setOnClickListener(this);

        Button cancelButton = (Button) findViewById(R.id.reported_cancel_button);
        cancelButton.setOnClickListener(this);

        enteredItem = (EditText) findViewById(R.id.reported_item_name);
        enteredPrice = (EditText) findViewById(R.id.reported_item_price);
        enteredLocation = (EditText) findViewById(R.id.location_input);
        enteredQuantity = (EditText) findViewById(R.id.reported_item_quantity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reported_sales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.reported_submit_button) {
            new AddReportAttempt().execute();
        } else if (v.getId() == R.id.reported_cancel_button) {
            Intent intention = new Intent(this, Homepage.class);
            finish();
            startActivity(intention);
            overridePendingTransition(R.anim.left_in, R.anim.right_out);

        }
    }

    /**
     * Class AddReportAttempt is an async task that loads the ListView as the program is running.
     */
    private class AddReportAttempt extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ReportedSales.this);
            progressDialog.setMessage("Submitting...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String newItem = enteredItem.getText().toString();
            String newPrice = enteredPrice.getText().toString();
            String quantity = enteredQuantity.getText().toString();
            String newLocation = enteredLocation.getText().toString();

            double price = Double.parseDouble(newPrice);
            int quant = Integer.parseInt(quantity);
            return DatabaseInterfacer.createItemReport(newItem, newLocation, price, quant);
        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            progressDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ReportedSales.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
    }
}
