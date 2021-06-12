package kr.ac.kumoh.ce.leisureguardian.data

import android.provider.ContactsContract

data class SignUpData(
    var organization: String,
    var fullname: String,
    var email: String,
    var password: String
)
