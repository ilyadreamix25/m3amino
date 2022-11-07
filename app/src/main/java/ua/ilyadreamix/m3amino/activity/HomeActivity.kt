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
    private lateinit var activeFragment: Fragment

    private val profileFragment = ProfileFragment()
    private val comsFragment = ComsFragment()
    private val chatsFragment = ChatsFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root

        activeFragment = comsFragment

        setInsets()
        addFragments()
        setNavClickListeners()
        setContentView(view)
    }

    private fun setInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(binding.homeContent) { contentView, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            contentView.updatePadding(top = insets.top)
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setNavClickListeners() {
        binding.homeNavBar.menu.getItem(1).isChecked = true
        binding.homeNavBar.setOnItemReselectedListener {  }

        binding.homeNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_me -> { replaceFragment(profileFragment); true }
                R.id.home_coms -> { replaceFragment(comsFragment); true }
                R.id.home_chats -> { replaceFragment(chatsFragment); true }
                else -> false
            }
        }
    }

    private fun addFragments() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.home_content, profileFragment).hide(profileFragment)
            add(R.id.home_content, comsFragment)
            add(R.id.home_content, chatsFragment).hide(chatsFragment)
            commit()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }
}