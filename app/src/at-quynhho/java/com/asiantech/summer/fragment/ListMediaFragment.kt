package com.asiantech.summer.fragment

import android.Manifest
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.*
import com.asiantech.summer.service.MediaMusicService
import com.asiantech.summer.service.MediaMusicService.LocalBinder
import kotlinx.android.synthetic.`at-quynhho`.fragment_list_media.*

class ListMediaFragment : Fragment() {

    private var mediaMusicService = MediaMusicService()
    private var createNotification: CreateNotification? = null
    private val listDataMedia: ArrayList<DataMedia> = ArrayList()
    private var recyclerViewAdapter = RecyclerViewAdapter(listDataMedia)
    var binded: Boolean = false
    var isPlaying = false
    var position: Int = 0

    private var binder: LocalBinder? = null

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
//        initcheckPemission()
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
        val mediaIntent = Intent(requireContext(), MediaMusicService::class.java)
        mediaIntent.apply {
            mediaIntent.putExtra("MEDIA_PATH", listDataMedia[0].path)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                requireContext().startForegroundService(mediaIntent)
                activity?.bindService(mediaIntent, connection, BIND_AUTO_CREATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if (binded) {
            activity?.unbindService(connection)
            binded = false
        }
    }

    private fun initData() {
        listDataMedia.clear()
        listDataMedia.apply {
            addAll(Music.insertData(requireContext()))
        }
    }

    private fun initcheckPemission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PERMISSION
                )
            } else {
                initData()
            }
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
                    "Playing " + listDataMedia[it].musicName,
                    Toast.LENGTH_SHORT
                ).show()
                isPlaying = true
                itemBottomPlayer()
            }
        }
    }

    private fun itemBottomPlayer() {
        if (listDataMedia.isNotEmpty()) {
            imgMusicArt.setImageURI(Uri.parse(listDataMedia[position].imageMusic))
            Log.d("AAA", "uri: $listDataMedia")
            tvNameMusic.text = listDataMedia[position].musicName
            tvNameSinger.text = listDataMedia[position].musicArtist
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
                        .newInstance(listDataMedia, isPlaying)
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
        binder?.playNextMedia(Uri.parse(listDataMedia[position].path))
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
            val bind = service as LocalBinder
            binder = bind
            binder?.playNextMedia(Uri.parse(listDataMedia[0].path))
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
            createNotification?.createNotificationMusic(listDataMedia[mPositon], isPlaying)
        mediaMusicService.startForeground(1, notification)
        isPlaying = mediaMusicService.isPlaying() ?: true
    }
}
