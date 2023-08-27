package com.example.inbank.user_account.exception

class UserAccountNotFoundException(personalCode: String) :
    Exception("User account not found by personalCode: $personalCode")