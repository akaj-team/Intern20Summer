package com.asiantech.summer.fragment

import android.content.*
import android.content.Context.BIND_AUTO_CREATE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.asiantech.summer.*
import com.asiantech.summer.service.MediaMusicService
import com.asiantech.summer.service.MediaMusicService.LocalBinder
import kotlinx.android.synthetic.`at-quynhho`.fragment_list_media.*

class ListMediaFragment : Fragment() {

    private var mediaMusicService = MediaMusicService()
    private var createNotification: CreateNotification? = null
    private var listDataMedia: ArrayList<DataMedia> = ArrayList()
    private var recyclerViewAdapter = RecyclerViewAdapter(listDataMedia)
    var binded: Boolean = false
    var isPlaying = false
    var position: Int = 0
    private var mediaIntent: Intent? = null
    private var binder: LocalBinder? = null

    companion object {
        fun newInstance(isPlayer: Boolean): ListMediaFragment {
            return ListMediaFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("Click", isPlayer)
                }
            }
        }

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
        rvListMusic.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = recyclerViewAdapter
        }
        initData()
        initListMusic()
        itemBottomPlayer()
        initEvents()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) return
            initData()
        }
    }

    override fun onStart() {
        super.onStart()
        mediaIntent = Intent(requireContext(), MediaMusicService::class.java)
        activity?.bindService(mediaIntent, connection, BIND_AUTO_CREATE)
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

    private fun getPathService() {
        mediaIntent.apply {
            mediaIntent?.putExtra("MEDIA_PATH", listDataMedia[position].path)
        }
        binder?.playNextMedia(Uri.parse(listDataMedia[position].path))
    }

    private fun initListMusic() {
        recyclerViewAdapter.onPlayClick = {
            position = it
            Toast.makeText(
                requireContext(),
                "Playing " + listDataMedia[it].musicName,
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerViewAdapter.notifyDataSetChanged()
    }

    private fun itemBottomPlayer() {
        if (listDataMedia.isNotEmpty()) {
            imgMusicArt.setImageURI(Uri.parse(listDataMedia[position].imageMusic))
            tvNameMusic.text = listDataMedia[position].musicName
            tvNameSinger.text = listDataMedia[position].musicArtist
        }
    }

    private fun initEvents() {
        imgPlayPause.setOnClickListener {
            if (!isPlaying) {
                isPlaying = true
                startMusic()
                getPathService()
            } else {
                isPlaying = false
                pauseMusic()
            }
        }
        imgNext.setOnClickListener {
            imgPlayPause.setImageResource(R.drawable.ic_pause_30)
            nextMusic()

        }
        imgPrevious.setOnClickListener {
            imgPlayPause.setImageResource(R.drawable.ic_pause_30)
            previousMusic()

        }
        clCardView.setOnClickListener {
            (activity as PlayerMusicActivity)
                .replaceFragment(
                    ItemMusicFragment
                        .newInstance(listDataMedia, position, isPlaying)
                    , true
                )
        }
    }

    private fun startMusic() {
        if (!isPlaying) {
            imgPlayPause.setImageResource(R.drawable.ic_play_arrow_black_30)
        } else {
            imgPlayPause.setImageResource(R.drawable.ic_pause_30)
        }
    }

    fun pauseMusic() {
        imgPlayPause.setImageResource(R.drawable.ic_play_arrow_black_30)
        binder?.getService()?.onPauseMusic()
        createNotificationMedia(position)
    }

    fun nextMusic() {
        position++
        binder?.playNextMedia(Uri.parse(listDataMedia[position].path))
        itemBottomPlayer()
        binder?.playNextMedia(Uri.parse(listDataMedia[position].path))
        binder?.getService()?.onNextMusic()
        createNotificationMedia(position)
    }

    fun previousMusic() {
        if (position > 0) {
            binder?.playNextMedia(Uri.parse(listDataMedia[position].path))
            itemBottomPlayer()
            binder?.getService()?.onPreviousMusic()
            createNotificationMedia(position)
        }
        else{
            position = 0
        }
    }

    private var connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mediaMusicService.stopSelf()
            binded = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val bind = service as LocalBinder
            binder = bind
            mediaMusicService = bind.getService()
            this@ListMediaFragment.binded = true
        }

    }

    private fun createNotificationMedia(mPositon: Int) {
        createNotification = CreateNotification(mediaMusicService)
        val notification =
            createNotification?.createNotificationMusic(listDataMedia[mPositon], isPlaying)
        mediaMusicService.startForeground(1, notification)
    }
}
