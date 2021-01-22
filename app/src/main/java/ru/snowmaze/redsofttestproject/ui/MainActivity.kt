package ru.snowmaze.redsofttestproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ru.snowmaze.redsofttestproject.R
import ru.snowmaze.redsofttestproject.ui.products.ProductsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.commit {
            if (supportFragmentManager.fragments.isEmpty())
                replace(R.id.fragments_container, ProductsFragment())
        }
    }

}