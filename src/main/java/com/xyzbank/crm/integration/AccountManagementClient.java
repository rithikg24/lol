package com.xyzbank.crm.integration;

import org.springframework.stereotype.Component;

@Component
public class AccountManagementClient {
    public boolean flagForUpsell(String customerEmail, String product) {
        // Stub: Pretend an upsell note is recorded
        return true;
    }
}
