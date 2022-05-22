package com.example.ljensen_studentbudgetcalc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EnterExpensesFragment extends Fragment {

    //UI Input references:
    private EditText RentAmountET;
    private EditText UtilAmountET;
    private EditText InternetAmountET;
    private EditText PhoneAmountET;
    private EditText TuitionAmountET;
    private EditText BooksAmountET;
    private EditText OtherAmountET;
    private RadioButton MonthlyRB;
    private RadioButton AnnualRB;

    //UI Output references:
    private TextView TotalHousingTV;
    private TextView TotalEducationTV;
    private TextView TotalExpensesTV;

    //Button
    Button AcceptBtn;

    //Budget object
    private Budget budget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_enterexpenses, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        budget = new Budget();

        //assign UI input/outputs to instances
        RentAmountET = getView().findViewById(R.id.et_Rent);
        UtilAmountET = getView().findViewById(R.id.et_Utilities);
        InternetAmountET = getView().findViewById(R.id.et_Internet);
        PhoneAmountET = getView().findViewById(R.id.et_Phone);
        TuitionAmountET = getView().findViewById(R.id.et_Tuition);
        BooksAmountET = getView().findViewById(R.id.et_Books);
        OtherAmountET = getView().findViewById(R.id.et_OtherFees);
        MonthlyRB = getView().findViewById(R.id.rad_Monthly);
        AnnualRB = getView().findViewById(R.id.rad_Annual);
        AcceptBtn = getView().findViewById(R.id.btn_Accept);
        TotalHousingTV = getView().findViewById(R.id.tv_calc_TotalHousing);
        TotalEducationTV = getView().findViewById(R.id.tv_calc_TotalEdCosts);
        TotalExpensesTV = getView().findViewById(R.id.tv_calc_TotalExpenses2);

        //assign OnFocusChange listener to input UI elements
        RentAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        UtilAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        InternetAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        PhoneAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        TuitionAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        BooksAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        OtherAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);


        //Get data (if available) from the host activity
        Bundle arguments = getArguments();

        if (arguments != null){
            budget.setLoansIncome(arguments.getDouble("loans"));
            budget.setEarningsIncome(arguments.getDouble("earnings"));
            budget.setMonthlyExpenses(arguments.getBoolean("isMonthly"));
            budget.setBooksExpense(arguments.getDouble("books"));
            budget.setTuitionExpense(arguments.getDouble("tuition"));
            budget.setOtherFeesExpense(arguments.getDouble("other"));
            budget.setInternetExpense(arguments.getDouble("internet"));
            budget.setUtilitiesExpense(arguments.getDouble("utilities"));
            budget.setPhoneExpense(arguments.getDouble("phone"));
            budget.setRentExpense(arguments.getDouble("rent"));

            //set previous any previous values into inputs
            RentAmountET.setText(formatResultTwoDecimals(budget.getRentExpense()));
            UtilAmountET.setText(formatResultTwoDecimals(budget.getUtilitiesExpense()));
            InternetAmountET.setText(formatResultTwoDecimals(budget.getInternetExpense()));
            PhoneAmountET.setText(formatResultTwoDecimals(budget.getPhoneExpense()));
            TuitionAmountET.setText(formatResultTwoDecimals(budget.getTuitionExpense()));
            BooksAmountET.setText(formatResultTwoDecimals(budget.getBooksExpense()));
            OtherAmountET.setText(formatResultTwoDecimals(budget.getOtherFeesExpense()));
            MonthlyRB.setChecked(true); //will have loaded monthly values from Budget, so make UI display monthly radiobox

            //update calculations
            updateCalculations();
        }

        //button on-click listener
        AcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send budget object data from this fragment to main activity for processing
                Accept();
            }
        });
    }

    //separate function because it needs to be called within the class and from Main Activity
    public void Accept(){
        updateCalculations();
        getParentFragmentManager().setFragmentResult("enterexpenses", createBudgetBundle());
    }

    private View.OnFocusChangeListener multipurposeFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            updateCalculations();
        }
    };

    private void updateCalculations(){
        //update Budget object with this fragment's current inputs
        //using method to check radiobuttons, to keep code easier to read

        //check if values are annual or monthly
        if (budget.isMonthlyExpenses) {
            //update Budget object with this fragment's current inputs
            //using method to check radiobuttons, to keep code easier to read
            budget.setMonthlyExpenses(areAmountsMonthly());
            budget.setRentExpense(Double.valueOf(RentAmountET.getText().toString()));
            budget.setUtilitiesExpense(Double.valueOf(UtilAmountET.getText().toString()));
            budget.setInternetExpense(Double.valueOf(InternetAmountET.getText().toString()));
            budget.setPhoneExpense(Double.valueOf(PhoneAmountET.getText().toString()));
            budget.setTuitionExpense(Double.valueOf(TuitionAmountET.getText().toString()));
            budget.setBooksExpense(Double.valueOf(BooksAmountET.getText().toString()));
            budget.setOtherFeesExpense(Double.valueOf(OtherAmountET.getText().toString()));
            //update Total Housing Expense
            TotalHousingTV.setText(formatResultTwoDecimals(budget.calculateHousingUtilityCosts()));
            //update Total Education Expense
            TotalEducationTV.setText(formatResultTwoDecimals(budget.calculateEducationCosts()));
            //update TotalExpenses
            TotalExpensesTV.setText(formatResultTwoDecimals(budget.calculateTotalExpenses()));
        }
        else{
            //dividing values by 12 to get monthly values so Budget object always has monthly data
            budget.setMonthlyExpenses(areAmountsMonthly());
            budget.setRentExpense(Double.valueOf(RentAmountET.getText().toString())/12);
            budget.setUtilitiesExpense(Double.valueOf(UtilAmountET.getText().toString())/12);
            budget.setInternetExpense(Double.valueOf(InternetAmountET.getText().toString())/12);
            budget.setPhoneExpense(Double.valueOf(PhoneAmountET.getText().toString())/12);
            budget.setTuitionExpense(Double.valueOf(TuitionAmountET.getText().toString())/12);
            budget.setBooksExpense(Double.valueOf(BooksAmountET.getText().toString())/12);
            budget.setOtherFeesExpense(Double.valueOf(OtherAmountET.getText().toString())/12);
            //update Total Housing Expense * 12 months
            TotalHousingTV.setText(formatResultTwoDecimals(budget.calculateHousingUtilityCosts()*12));
            //update Total Education Expense * 12 months
            TotalEducationTV.setText(formatResultTwoDecimals(budget.calculateEducationCosts()*12));
            //update TotalExpenses * 12 months
            TotalExpensesTV.setText(formatResultTwoDecimals(budget.calculateTotalExpenses()*12));
        }

    }

    private Boolean areAmountsMonthly(){
        Boolean isMonthly = true;
        if (AnnualRB.isChecked()){
            isMonthly = false;
        }
        return isMonthly;
    }

    private Bundle createBudgetBundle(){
        Bundle budgetData = new Bundle();

        budgetData.putDouble("earnings", budget.getEarningsIncome());
        budgetData.putDouble("loans", budget.getLoansIncome());
        budgetData.putDouble("rent", budget.getRentExpense());
        budgetData.putDouble("utilities", budget.getUtilitiesExpense());
        budgetData.putDouble("internet", budget.getInternetExpense());
        budgetData.putDouble("phone", budget.getPhoneExpense());
        budgetData.putDouble("tuition", budget.getTuitionExpense());
        budgetData.putDouble("books", budget.getBooksExpense());
        budgetData.putDouble("other", budget.getOtherFeesExpense());
        budgetData.putBoolean("isMonthly", budget.getMonthlyExpenses());

        return budgetData;
    }

    private String formatResultTwoDecimals(Double input){
        //idea for this method of rounding from:
        // https://www.delftstack.com/howto/java/how-to-round-a-double-to-two-decimal-places-in-java/
        Double roundedResult = Math.round(input*100.0)/100.0;
        return roundedResult.toString();
    }

}
