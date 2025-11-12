package com.ichiraku.model;

import java.math.BigDecimal;
import java.util.List;

public class TransactionLog {
    /*
    * *** Fields / Attributes ***
    * */
    private List<Transaction> transactionList;

    /*
     * *** Constructor ***
     * */
    public TransactionLog(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    /*
     * *** Getters (Standard) ***
     * */
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    /*
     * *** Methods ***
     * */
    public BigDecimal calculateTotalSales() {
        return transactionList.stream()
                .map(Transaction::getTotalAmount)            // extract totalAmount from each Transaction
                .reduce(BigDecimal.ZERO, BigDecimal::add);   // sum them all starting from ZERO
    }

    //public List<Transaction> getTransactionListByDate()
}
