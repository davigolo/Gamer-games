package com.example.gamergames.presentation.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.gamergames.R
import com.example.gamergames.loadUrl


class ImageDetailDialogFragment(private val gameImage: String): DialogFragment() {

    /**Dialog to display a detail of the selected game screenshot*/
    //region Private values
    private var imageDetail: ImageView? = null
    //endregion
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_image_detail_dialog,container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageDetail = view.findViewById(R.id.image_detail)
        imageDetail?.loadUrl(gameImage)

    }
}
