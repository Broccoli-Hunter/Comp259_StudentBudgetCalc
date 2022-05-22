package com.example.ljensen_studentbudgetcalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    Menu appMenu;
    int currentFragment; //1 if budgetSummary; 2 if enterExpenses

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //set listener for bundle from budgetsummary
        getSupportFragmentManager().setFragmentResultListener("budgetsummary", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                //don't need to unpack bundle, just pass it along

                //start fragment to enter expenses and send this data as an argument bundle
                //create the fragment
                EnterExpensesFragment expensesFragment = new EnterExpensesFragment();

                //put the same bundle into the fragment
                expensesFragment.setArguments(bundle);

                //change title of menu option
                appMenu.findItem(R.id.menuitem_expense_accept).setTitle("Accept");

                currentFragment = 2;
                //use Fragment Manager to create a Fragment Transaction object
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, expensesFragment, "enterexpenses");
                fragmentTransaction.commit();
            }
        });

        //set listener for bundle from enterexpenses
        getSupportFragmentManager().setFragmentResultListener("enterexpenses", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                //don't need to unpack bundle, just pass it along

                //start fragment to enter expenses and send this data as an argument bundle
                //create the fragment
                BudgetSummaryFragment summaryFragment = new BudgetSummaryFragment();

                //put the same bundle into the fragment
                summaryFragment.setArguments(bundle);

                //change title of menu option
                appMenu.findItem(R.id.menuitem_expense_accept).setTitle("Expenses");

                currentFragment = 1;
                //use Fragment Manager to create a Fragment Transaction object
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, summaryFragment, "budgetsummary");
                fragmentTransaction.commit();
            }
        });


        //start Budget Summary fragment by default
        BudgetSummaryFragment summaryFragment = new BudgetSummaryFragment();
        currentFragment = 1;
        //replace Fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view, summaryFragment, "budgetsummary");
        fragmentTransaction.commit();
    }

    //Menu code
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        appMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //get id of tapped menu item
        int id = item.getItemId();

        //decide what to do depending on which menu option selected
        if (id == R.id.menuitem_expense_accept){
            //menu item text changed when fragment loads instead of in this section

            if (currentFragment==1){ //on budget summary screen
                //get reference to fragment to call the 'Expenses' function (same as button)
                BudgetSummaryFragment fragment = (BudgetSummaryFragment) getSupportFragmentManager().findFragmentByTag("budgetsummary");
                fragment.Expenses();
            }
            else {  //on expenses screen
                //get reference to fragment to call the 'Accept' function (same as button)
                EnterExpensesFragment fragment = (EnterExpensesFragment) getSupportFragmentManager().findFragmentByTag("enterexpenses");
                fragment.Accept();
            }
            return true;

        }

        else if (id == R.id.menuitem_quit){
            //CLOSE ACTIVITY
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}