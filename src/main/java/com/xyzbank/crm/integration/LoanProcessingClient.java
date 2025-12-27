package com.xyzbank.crm.integration;

import org.springframework.stereotype.Component;

@Component
public class LoanProcessingClient {
    public boolean submitLoanLead(String customerEmail, String product) {
        // Stub: Pretend the lead is sent to the loan system
        return true;
    }
}
