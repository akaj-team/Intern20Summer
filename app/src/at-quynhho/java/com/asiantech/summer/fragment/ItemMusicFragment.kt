package com.asiantech.summer.fragment

import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.SeekBar
import android.widget.Toast
import com.asiantech.summer.*
import com.asiantech.summer.service.MediaMusicService
import com.asiantech.summer.service.MediaMusicService.Companion.isRepeat
import com.asiantech.summer.service.MediaMusicService.Companion.isShare
import kotlinx.android.synthetic.`at-quynhho`.fragment_item_music.*

class ItemMusicFragment : Fragment() {

    private var dataMedia: ArrayList<DataMedia> = ArrayList()
    private var position = 0
    private var notification: CreateNotification? = null
    private var isBinded: Boolean = false
    private var isPlaying = false
    private var mHandler: Handler = Handler(HandlerThread("Name").apply {
        start()
    }.looper)
    private var binder: MediaMusicService.LocalBinder? = null
    private var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            Log.e("xxx", "onServiceDisconnected")
            isBinded = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e("xxx", "onServiceConnected")
            binder = (service as MediaMusicService.LocalBinder)
            isBinded = true
        }
    }

    companion object {
        const val DELAY_TIME: Long = 1000
        const val LIST_MUSIC_KEY = "listmusic"
        const val POSITION = "position"
        const val IS_PLAYING_KEY = "isplaying"
        fun newInstance(
            dataMedia: ArrayList<DataMedia>,
            position: Int,
            isPlayer: Boolean
        ): ItemMusicFragment {
            return ItemMusicFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_MUSIC_KEY, dataMedia)
                    putInt(POSITION, position)
                    putBoolean(IS_PLAYING_KEY, isPlayer)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments.let {
            dataMedia =
                it?.getParcelableArrayList<DataMedia>(LIST_MUSIC_KEY) as ArrayList
            position = it.getInt(POSITION, 0)
            isPlaying = it.getBoolean(IS_PLAYING_KEY)
        }
        return inflater.inflate(R.layout.fragment_item_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()// Chi goi handle.post khi nhac duoc start
        initPlayPauseButton()
        initObserverMediaPlaying()
    }

    override fun onStop() {
        super.onStop()
        if (isBinded) {
            activity?.unbindService(connection)
            isBinded = false
        }
    }

    private fun initView() {
        civDiskAlbum.setImageURI(Uri.parse(dataMedia[position].imageMusic))
        tvMusicNamePlaying.text = dataMedia[position].musicName
        tvMusicArtistPlaying.text = dataMedia[position].musicArtist
        Log.d("aaaa", "" + dataMedia.size)
        if (isRepeat) {
            imgRepeat.setColorFilter(R.color.colorLightGrey)
        } else {
            imgRepeat.setColorFilter(R.color.colorAccent)
        }
        if (isShare) {
            imgShare.setColorFilter(R.color.colorLightGrey)
        } else {
            imgShare.setColorFilter(R.color.colorAccent)
        }
        val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        civDiskAlbum.startAnimation(rotation)
    }

    private fun initPlayPauseButton() {
        playMediaService()
        if (isPlaying) {
            imgPlayButton.setImageResource(R.drawable.ic_pause_30)
        } else {
            imgPlayButton.setImageResource(R.drawable.ic_play_arrow_black_30)
        }
    }

    private fun initListener() {
        imgPlayButton.setOnClickListener {
            initPlayPauseMedia()
        }
        imgNext.setOnClickListener {
            nextMusic()
        }
        imgPrevious.setOnClickListener {
            previousMusic()
        }
        imgShare.setOnClickListener {
            initShare()
        }
        imgRepeat.setOnClickListener {
            initRepeat()
        }
    }

    private fun nextMusic() {
        isPlaying = true
        ListMediaFragment.newInstance(isPlaying).nextMusic()
    }

    private fun playMediaService() {
        val mediaIntent = Intent(context, MediaMusicService::class.java)
        requireActivity().bindService(mediaIntent, connection, BIND_AUTO_CREATE)
    }

    private fun previousMusic() {
        isPlaying = true
        ListMediaFragment.newInstance(isPlaying).previousMusic()
    }

    private fun initPlayPauseMedia() {
        if (isPlaying) {
            imgPlayButton.setImageResource(R.drawable.ic_play_arrow_black_30)
            isPlaying = false
            ListMediaFragment.newInstance(isPlaying).pauseMusic()
        } else {
            imgPlayButton.setImageResource(R.drawable.ic_pause_30)
            isPlaying = true
        }
        binder?.playNextMedia(Uri.parse(dataMedia[position].path))
    }

    private fun initShare() {
        if (!isShare) {
            imgShare.setColorFilter(Color.MAGENTA)
            isShare = true
            Toast.makeText(requireContext(), getString(0), Toast.LENGTH_SHORT).show()
        } else {
            imgShare.setColorFilter(Color.GRAY)
            isShare = false
            Toast.makeText(requireContext(), getString(0), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initRepeat() {
        if (!isRepeat) {
            imgRepeat.setColorFilter(Color.MAGENTA)
            isRepeat = true
            Toast.makeText(requireContext(), getString(0), Toast.LENGTH_SHORT).show()
        } else {
            imgRepeat.setColorFilter(Color.GRAY)
            isRepeat = false
            Toast.makeText(requireContext(), getString(0), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initObserverMediaPlaying() {
        val runnable = object : Runnable {
            override fun run() {
                activity?.runOnUiThread {
                    updateProgress()
                }
//                initRotatyAlumArt()// Tach moi function lam mot nhiem vu rieng
                Log.e("xxx", (binder?.getService()?.mediaPlayer?.currentPosition ?: 0).toString())
                mHandler.postDelayed(this, DELAY_TIME)

            }
        }
        mHandler.post(runnable)
    }

    private fun initRotatyAlumArt() {
        civDiskAlbum.setImageURI(Uri.parse(dataMedia[position].imageMusic))
        val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
        civDiskAlbum.startAnimation(rotation)
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(requireContext())
        val notification = notification?.createNotificationMusic(dataMedia[position], isPlaying)
    }

    private fun updateProgress() {
        sbMusicTime?.progress = binder?.getService()?.mediaPlayer?.currentPosition ?: 0
        tvTimeStart?.text = Music.convertTimeMusic(sbMusicTime.progress)
        sbMusicTime?.max =
            binder?.getService()?.mediaPlayer?.duration ?: 0
        val maxProgress = sbMusicTime?.max
        if (maxProgress != null) {
            tvTimeEnd?.text = Music.convertTimeMusic(maxProgress)
        }
        initRotatyAlumArt()
    }
}
