
package monte.com.tipcalculatorapp;

import java.text.NumberFormat; //for currency formatting

import android.app.Activity; //base class for activities
import android.os.Bundle; // for saving sate information
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; //EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; //SeekBar listener
import android.widget.TextView; //for displaying text

//import org.w3c.dom.Text;

public class TipCalculator extends Activity {
    //currency and percent formatters
    //private static final means that I can only use these variables only within this class.
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();



    private double billAmount = 0.0;
    private double customPercent = 0.18;
    private TextView amountDisplayTextView;
    private TextView percentCustomTextView;
    private TextView tip15TextView;
    private TextView total15TextView;
    private TextView tipCustomTextView;
    private TextView totalCustomTextView;

    //called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
    // referencing all TextView objects from layout to the variables declared here
    amountDisplayTextView= (TextView)findViewById(R.id.amountDisplayTextView);
    percentCustomTextView=(TextView)findViewById(R.id.percentCustomTextView);
    tip15TextView=(TextView)findViewById(R.id.tip15TextView);
    total15TextView=(TextView)findViewById(R.id.total15TextView);
    tipCustomTextView=(TextView)findViewById(R.id.tipCustomTextView);
    totalCustomTextView=(TextView)findViewById(R.id.totalCustomTextView);

    //shows number in currency format
    amountDisplayTextView.setText(currencyFormat.format(billAmount));
        updateStandard(); //update the 15% tip TextViews
        updateCustom(); //update the custom tip TextViews

    //set amountEditText's TextWatcher
    EditText amountEditText = (EditText)findViewById(R.id.amountEditText);
    amountEditText.addTextChangedListener(amountEditTextWatcher);

    //set customTipSeekBar's OnSeekBarChangeListener
    SeekBar customTipSeekBar= (SeekBar)findViewById(R.id.customTipSeekBar);
    customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

    } //end method onCreate

    //updates 15% tip TextViews
    private void updateStandard()
    {
        //calculate 15% tip and total
        double fifteenPercentTip = billAmount * 0.15;
        double fifteenPercentTotal = billAmount + fifteenPercentTip;

        //display 15% tip and total formatted as currency
        tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
        total15TextView.setText(currencyFormat.format(fifteenPercentTotal));

    } //end method updateStandard

    private void updateCustom() {
        percentCustomTextView.setText(percentFormat.format(customPercent));

        //calculate custom% tip and total
        double customTip = billAmount * customPercent;
        double customTotal = billAmount + customTip;

        //display custom tip and total formatted as currency
        tipCustomTextView.setText(currencyFormat.format(customTip));
        totalCustomTextView.setText(currencyFormat.format(customTotal));

    }  //end method updateCustom

    //called when the user changes the position of SeekBar
    private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //sets customPercent to position of the SeekBar's thumb
            customPercent = progress / 100.0;
            updateCustom(); //update the custom tip TextViews
        }//end method onProgressChanged

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }; //end method OnSeekBarChangeListener

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                billAmount = Double.parseDouble(s.toString()) / 100.0;
            }//end try
            catch (NumberFormatException e) {
                billAmount = 0.0; //default if exception occurs
            }//end catch

            //display currency formatted bill amount
            amountDisplayTextView.setText(currencyFormat.format(billAmount));
            updateStandard();
            updateCustom();
        }//end method onTextChanged
        @Override
        public void afterTextChanged(Editable s) {
        }

    };//end amountEditWatcher

}
