package ua.ilyadreamix.m3amino.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.fragment.app.Fragment
import ua.ilyadreamix.m3amino.R
import ua.ilyadreamix.m3amino.databinding.ActivityHomeBinding
import ua.ilyadreamix.m3amino.fragment.ChatsFragment
import ua.ilyadreamix.m3amino.fragment.ComsFragment
import ua.ilyadreamix.m3amino.fragment.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.homeContent) { contentView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            contentView.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }

        setContentView(view)

        binding.homeNavBar.menu.getItem(1).isChecked = true

        binding.homeNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_me -> replaceFragment(ProfileFragment())
                R.id.home_coms -> replaceFragment(ComsFragment())
                R.id.home_chats -> replaceFragment(ChatsFragment())
            }

            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.home_content, fragment)
            .commit()
    }
}