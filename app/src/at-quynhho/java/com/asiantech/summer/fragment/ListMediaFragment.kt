package com.asiantech.summer.fragment

import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.*
import com.asiantech.summer.service.MediaMusicService
import kotlinx.android.synthetic.`at-quynhho`.fragment_list_media.*

class ListMediaFragment : Fragment() {

    private var mediaMusicService = MediaMusicService()
    private var createNotification: CreateNotification? = null
    private val listMusic: ArrayList<DataMedia> = ArrayList()
    private val listPath: ArrayList<String> = ArrayList()
    private var recyclerViewAdapter = RecyclerViewAdapter(listMusic)
    var binded: Boolean = false
    var isPlaying = false
    var position: Int = 0

    companion object {
        const val PERMISSION = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_media, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListMusic()
        listenMusic()
        initcheckPemission()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) return
            initListMusic()
        }
    }

    override fun onStart() {
        super.onStart()
//        activity?.startService(Intent(context, MediaMusicService::class.java))
//            .apply {
//            initListMusic()
//        }
        val mediaIntent = Intent(requireContext(), MediaMusicService::class.java)
       mediaIntent.apply {
            mediaIntent.putStringArrayListExtra(RecyclerViewAdapter.MUSIC_LIST, listPath)
            mediaIntent.putParcelableArrayListExtra(RecyclerViewAdapter.MUSIC_LIST, listMusic)
            mediaIntent.putExtra(RecyclerViewAdapter.MUSIC_POSITION, position)
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               requireContext().startForegroundService(mediaIntent)
               activity?.bindService( Intent(requireContext(), MediaMusicService::class.java), connection, Context.BIND_AUTO_CREATE )
           }
        }

//        initListMusic()
    }

    override fun onStop() {
        super.onStop()
 //       activity?.bindService( Intent(requireContext(), MediaMusicService::class.java), connection, BIND_AUTO_CREATE)
        if (binded) {
            activity?.unbindService(connection)
            binded = false
        }
    }

    private fun initData() {
        listMusic.clear()
        listMusic.apply {
            addAll(Music.insertData(requireContext()))
        }
    }

    private fun initcheckPemission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    arrayOf(
//                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    ),
//                    PERMISSION
//                )
//            } else {
            initData()
//            }
        }
    }

    private fun initListMusic() {
        rvListMusic.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter
            recyclerViewAdapter.onPlayClick = {
                position = it
                Toast.makeText(
                    requireContext(),
                    "Playing " + listMusic[it].musicName,
                    Toast.LENGTH_SHORT
                ).show()
                isPlaying = true
                itemBottomPlayer()
            }
        }
    }

    private fun itemBottomPlayer() {
        if (listMusic.isNotEmpty()) {
            imgMusicArt.setImageURI(Uri.parse(listMusic[position].imageMusic))
            Log.d("AAA", "uri: $listMusic")
            tvNameMusic.text = listMusic[position].musicName
            tvNameSinger.text = listMusic[position].musicArtist
        }
        pauseMusic()
    }

    private fun playPauseMedia() {
        isPlaying = when (isPlaying) {
            true -> {
                imgPlayPause.setImageResource(R.drawable.ic_play_arrow_black_30)
                onStop()
                false
            }
            else -> {
                imgPlayPause.setImageResource(R.drawable.ic_pause_30)
                true
            }
        }
        mediaMusicService.initPlayPause()
    }

    private fun listenMusic() {
        imgPlayPause.setOnClickListener {
            playPauseMedia()
        }
        imgNext.setOnClickListener {
            nextMusic()
        }
        imgPrevious.setOnClickListener {
            previousMusic()
        }
        clCardView.setOnClickListener {
            (activity as PlayerMusicActivity)
                .replaceFragment(
                    ItemMusicFragment
                        .newInstance(listMusic, isPlaying)
                    , true
                )
        }
    }

    private fun pauseMusic() {
        if (isPlaying) {
            imgPlayPause.setImageResource(R.drawable.ic_pause_30)
        } else {
            imgPlayPause.setImageResource(R.drawable.ic_play_arrow_black_30)
        }
    }

    private fun nextMusic() {
        mediaMusicService.initNextMusic()
        position = mediaMusicService.initPosition()
        createNotificationMedia(position)
        itemBottomPlayer()
    }

    private fun previousMusic() {
        mediaMusicService.initPreviousMusic()
        position = mediaMusicService.initPosition()
        createNotificationMedia(position)
        itemBottomPlayer()
    }

    private var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mediaMusicService.stopSelf()
            binded = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val bind = service as MediaMusicService.LocalBinder
            mediaMusicService = bind.getService()
            this@ListMediaFragment.binded = true
            position = mediaMusicService.initPosition()
            isPlaying = mediaMusicService.isPlaying() ?: true
            itemBottomPlayer()
        }

    }

    private fun createNotificationMedia(mPositon: Int) {
        createNotification = CreateNotification(mediaMusicService)
        val notification =
            createNotification?.createNotificationMusic(listMusic[mPositon], isPlaying)
        mediaMusicService.startForeground(1, notification)
        isPlaying = mediaMusicService.isPlaying() ?: true
    }
}
