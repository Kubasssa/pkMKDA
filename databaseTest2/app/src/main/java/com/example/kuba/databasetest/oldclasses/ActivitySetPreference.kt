package com.example.kuba.databasetest.oldclasses

import android.os.Bundle
import android.app.Activity
import com.example.kuba.databasetest.R

import kotlinx.android.synthetic.main.activity_set_preference.*

class ActivitySetPreference : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_preference)
    }

}
