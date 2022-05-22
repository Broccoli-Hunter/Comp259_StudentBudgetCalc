package com.example.ljensen_studentbudgetcalc;

public class Budget {
    //All values saved into these properties are monthly values, enforced in Fragment code
    //isMonthlyExpenses is used to determine if calculations need to be multiplied by 12 to be shown accurately on UI

    //Income-related properties
    Double earningsIncome;
    Double loansIncome;

    //Expense-related properties
    Boolean isMonthlyExpenses;
    Double rentExpense;
    Double utilitiesExpense;
    Double internetExpense;
    Double phoneExpense;
    Double tuitionExpense;
    Double booksExpense;
    Double otherFeesExpense;

    //Totals will be calcluated with functions

    //Constructor
    public Budget() {
        //initializing values with $0.00 so values that haven't been entered on calculator are not null at calculation time
        earningsIncome = 0d;
        loansIncome = 0d;
        isMonthlyExpenses = true;
        rentExpense = 0d;
        utilitiesExpense = 0d;
        internetExpense = 0d;
        phoneExpense = 0d;
        tuitionExpense = 0d;
        booksExpense = 0d;
        otherFeesExpense = 0d;

    }

    public Double getEarningsIncome() {
        return earningsIncome;
    }

    public Double getLoansIncome() {
        return loansIncome;
    }

    public Boolean getMonthlyExpenses() {
        return isMonthlyExpenses;
    }

    public Double getRentExpense() {
        return rentExpense;
    }

    public Double getUtilitiesExpense() {
        return utilitiesExpense;
    }

    public Double getInternetExpense() {
        return internetExpense;
    }

    public Double getPhoneExpense() {
        return phoneExpense;
    }

    public Double getTuitionExpense() {
        return tuitionExpense;
    }

    public Double getBooksExpense() {
        return booksExpense;
    }

    public Double getOtherFeesExpense() {
        return otherFeesExpense;
    }

    public void setEarningsIncome(Double earningsIncome) {
        this.earningsIncome = earningsIncome;
    }

    public void setLoansIncome(Double loansIncome) {
        this.loansIncome = loansIncome;
    }

    public void setMonthlyExpenses(Boolean monthlyExpenses) {
        isMonthlyExpenses = monthlyExpenses;
    }

    public void setRentExpense(Double rentExpense) {
        this.rentExpense = rentExpense;
    }

    public void setUtilitiesExpense(Double utilitiesExpense) {
        this.utilitiesExpense = utilitiesExpense;
    }

    public void setInternetExpense(Double internetExpense) {
        this.internetExpense = internetExpense;
    }

    public void setPhoneExpense(Double phoneExpense) {
        this.phoneExpense = phoneExpense;
    }

    public void setTuitionExpense(Double tuitionExpense) {
        this.tuitionExpense = tuitionExpense;
    }

    public void setBooksExpense(Double booksExpense) {
        this.booksExpense = booksExpense;
    }

    public void setOtherFeesExpense(Double otherFeesExpense) {
        this.otherFeesExpense = otherFeesExpense;
    }

    //Calculations
    public Double calculateTotalIncome(){
        Double totalIncome = loansIncome + earningsIncome;
        return totalIncome;
    }

    public Double calculateHousingUtilityCosts(){
        Double housingUtilityCosts = rentExpense + utilitiesExpense + internetExpense + phoneExpense;
        return housingUtilityCosts;
    }

    public Double calculateEducationCosts(){
        Double educationCosts = tuitionExpense + booksExpense + otherFeesExpense;
        return educationCosts;
    }

    public Double calculateTotalExpenses(){
        Double totalExpenses = calculateHousingUtilityCosts() + calculateEducationCosts();
        return totalExpenses;
    }


    public Double calculateSurplusOrShortfall(){
        Double surplusOrShortfall = calculateTotalIncome() - calculateTotalExpenses();
        return surplusOrShortfall;
    }
}
