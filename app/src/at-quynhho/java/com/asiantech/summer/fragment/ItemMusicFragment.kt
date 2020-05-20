package com.asiantech.summer.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
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

@Suppress("CAST_NEVER_SUCCEEDS")
class ItemMusicFragment : Fragment() {

    private var dataMedia = arrayListOf<DataMedia>()
    private var position = 0
    private var notification: CreateNotification? = null
    private var binded: Boolean = false
    private var isPlaying = false
    private var mediaMusicService = MediaMusicService()
    private var mHandler: Handler = Handler(Looper.getMainLooper())

    companion object {
        const val DELAY_TIME: Long = 1000
        const val LIST_MUSIC_KEY = "listmusic"
        const val IS_PLAYING_KEY = "isplaying"
        fun newInstance(listMusic: ArrayList<DataMedia>, isPlayer: Boolean): ItemMusicFragment {
            return ItemMusicFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_MUSIC_KEY, listMusic)
                    putBoolean(IS_PLAYING_KEY, isPlayer)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_music, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            dataMedia =
                it?.getParcelableArrayList<DataMedia>(LIST_MUSIC_KEY) as ArrayList<DataMedia>
            isPlaying = it.getBoolean(IS_PLAYING_KEY)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        updateMusic()
        initPlayPauseButton()
    }

    override fun onStart() {
        super.onStart()
//        activity?.startService(Intent(context, MediaMusicService::class.java))
//            .apply {
//            this.putExtra(RecyclerViewAdapter.MUSIC_POSITION, 0)
//            this.getStringArrayListExtra(RecyclerViewAdapter.MUSIC_LIST)
//            this.putParcelableArrayListExtra(RecyclerViewAdapter.MUSIC_LIST, dataMedia)
//        })
        requireActivity().bindService( Intent(requireContext(), MediaMusicService::class.java), connection, Context.BIND_AUTO_CREATE )
        updateMusic()
    }

    override fun onStop() {
        super.onStop()
        if (binded) {
            activity?.unbindService(connection)
            binded = false
        }
    }

    private fun initView() {
        civDiskAlbum.setImageURI(Uri.parse(dataMedia[position].imageMusic))
        tvMusicNamePlaying.text = dataMedia[position].musicName
        tvMusicArtistPlaying.text = dataMedia[position].musicArtist

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
        mediaMusicService.initPlayPause();
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
        position++
        if (position > dataMedia.size - 1) position = 0
        mediaMusicService.initNextMusic()
        createNotification(position)
        initView()
    }

    private fun previousMusic() {
        isPlaying = true
        position--
        if (position < 0) position = dataMedia.size - 1
        mediaMusicService.initPreviousMusic()
        createNotification(position)
        initView()
    }

    private fun initPlayPauseMedia() {
        isPlaying = if (isPlaying) {
            imgPlayButton.setImageResource(R.drawable.ic_play_arrow_black_30)
            false
        } else {
            imgPlayButton.setImageResource(R.drawable.ic_pause_30)
            true
        }
        mediaMusicService.initPlayPause()
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

    private fun updateMusic() {
//        var mPosition : Int = position
        val runnable = object : Runnable {
            override fun run() {
                sbMusicTime?.max = dataMedia[position].timePlay
                position = mediaMusicService.initPosition()
                val currentPosition: Int = mediaMusicService.currentPosition()?:0
         //           (mediaMusicService.currentPosition()!! / DELAY_TIME).toInt()
                sbMusicTime?.progress = currentPosition
                tvTimeStart?.text = Music.convertTimeMusic(sbMusicTime.progress)
                tvTimeEnd?.text = Music.convertTimeMusic(dataMedia[position].timePlay)
                //                if (position > 0) {
//                    try {
//                        position
//                        initView()
//                        Log.d("DDD", "position:$position")
//                    } catch (exception: NullPointerException) {
//                        Log.d("CCC", "position:$position")
//                    }
                initView()
//                }
                mHandler.postDelayed(this, DELAY_TIME)
                Log.e("zzz",mediaMusicService.currentPosition().toString());
            }
        }
        mHandler.post(runnable)
        sbMusicTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvTimeStart?.text = Music.convertTimeMusic(sbMusicTime.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaMusicService.seekTo(sbMusicTime.progress)
            }
        })
    }

    private var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            binded = false
            mediaMusicService.stopSelf()
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            binded = true
            val localBinder = service as MediaMusicService.LocalBinder
            mediaMusicService = localBinder.getService()
            position = mediaMusicService.initPosition()
            isPlaying = mediaMusicService.isPlaying() ?: true
            initView()
        }
    }

    private fun createNotification(position: Int) {
        notification = CreateNotification(mediaMusicService)
        val notification = notification?.createNotificationMusic(dataMedia[position], isPlaying)
        mediaMusicService.startForeground(1, notification)
        isPlaying = mediaMusicService.isPlaying() ?: true
        isRepeat = mediaMusicService.isRepeat()
        isShare = mediaMusicService.isShare()
    }
}
