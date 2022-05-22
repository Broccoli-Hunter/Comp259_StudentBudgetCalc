package com.example.ljensen_studentbudgetcalc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.fragment.app.Fragment;

public class BudgetSummaryFragment extends Fragment {

    //UI Input references:
    private EditText EarningsAmountET;
    private EditText LoanAmountET;

    //UI Output references:
    private TextView TotalIncomeTV;
    private TextView TotalExpensesTV;
    private TextView SurplusShortfallTV;

    //Button
    Button CalculateExpensesBtn;

    //Budget object
    private Budget budget;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_budgetsummary, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        budget = new Budget();

        //assign UI input/outputs to instances
        EarningsAmountET = getView().findViewById(R.id.et_Earnings);
        LoanAmountET = getView().findViewById(R.id.et_Loans);
        TotalIncomeTV = getView().findViewById(R.id.tv_calc_TotalIncome);
        TotalExpensesTV = getView().findViewById(R.id.tv_calc_TotalExpenses1);
        SurplusShortfallTV = getView().findViewById(R.id.tv_calc_SurplusShortfall);
        CalculateExpensesBtn = getView().findViewById(R.id.btn_CalcExpenses);

        //assign OnFocusChange listener to input UI elements
        EarningsAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);
        LoanAmountET.setOnFocusChangeListener(multipurposeFocusChangeListener);

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

            //set previous earnings and loan amounts into edit texts
            LoanAmountET.setText(formatResultTwoDecimals(budget.getLoansIncome()));
            EarningsAmountET.setText(formatResultTwoDecimals(budget.getEarningsIncome()));

            //update calculations
            updateCalculations();
        }

        //button on-click listener
        CalculateExpensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send budget object data from this fragment to main activity for processing
                Expenses();
            }
        });


    }

    //separate function because it needs to be called within the class and from Main Activity
    public void Expenses(){
        updateCalculations();
        getParentFragmentManager().setFragmentResult("budgetsummary", createBudgetBundle());
    }

    private View.OnFocusChangeListener multipurposeFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            updateCalculations();
        }
    };

    private void updateCalculations(){
        //update Budget object with this fragment's current inputs
        budget.setEarningsIncome(Double.valueOf(EarningsAmountET.getText().toString()));
        budget.setLoansIncome(Double.valueOf(LoanAmountET.getText().toString()));

        //update TotalIncome
        TotalIncomeTV.setText(formatResultTwoDecimals(budget.calculateTotalIncome()));

        //update TotalExpenses
        TotalExpensesTV.setText(formatResultTwoDecimals(budget.calculateTotalExpenses()));

        //update Surplus/Shortfall
        SurplusShortfallTV.setText(formatResultTwoDecimals(budget.calculateSurplusOrShortfall()));
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
