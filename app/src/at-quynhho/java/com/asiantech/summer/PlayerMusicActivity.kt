package com.asiantech.summer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.asiantech.summer.fragment.ListMediaFragment

class PlayerMusicActivity : AppCompatActivity() {

    private var isPlayer: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_music)
        replaceFragment(ListMediaFragment.newInstance(isPlayer))

    }

    internal fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flMediaPlayer, fragment, null)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }
}
