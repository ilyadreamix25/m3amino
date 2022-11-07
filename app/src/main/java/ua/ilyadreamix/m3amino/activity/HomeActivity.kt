package ua.ilyadreamix.m3amino.activity

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Build
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

        setDecor()
        addFragments()
        setNavClickListeners()
        setContentView(view)
    }

    private fun setDecor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setNavClickListeners() {
        binding.homeNavBar.menu.getItem(1).isChecked = true

        binding.homeNavBar.setOnItemReselectedListener {  }
        binding.homeNavBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_me -> {
                    replaceFragment(profileFragment)
                    setIcons(profileIcon = getDrawable(R.drawable.ic_person_filled)!!)
                }
                R.id.home_coms -> {
                    replaceFragment(comsFragment)
                    setIcons(comsIcon = getDrawable(R.drawable.ic_blocks_filled)!!)
                }
                R.id.home_chats -> {
                    replaceFragment(chatsFragment)
                    setIcons(chatsIcon = getDrawable(R.drawable.ic_chat_filled)!!)
                }
            }

            true
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setIcons(
        profileIcon: Drawable = getDrawable(R.drawable.ic_person_outlined)!!,
        comsIcon: Drawable = getDrawable(R.drawable.ic_blocks_outlined)!!,
        chatsIcon: Drawable = getDrawable(R.drawable.ic_chat_outlined)!!
    ) {
        binding.homeNavBar.menu.getItem(0).icon = profileIcon
        binding.homeNavBar.menu.getItem(1).icon = comsIcon
        binding.homeNavBar.menu.getItem(2).icon = chatsIcon
    }

    /**
     * Add fragments and hide unselected
     */
    private fun addFragments() {
        supportFragmentManager.beginTransaction().apply {
            add(R.id.home_content, profileFragment).hide(profileFragment)
            add(R.id.home_content, chatsFragment).hide(chatsFragment)
            add(R.id.home_content, comsFragment)
            commit()
        }
    }

    /**
     * Hide activeFragment and show fragment
     */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }
}