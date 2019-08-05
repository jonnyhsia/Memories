package com.jonnyhsia.memories.page.compose.format

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jonnyhsia.appcore.ext.asHorizontalList
import com.jonnyhsia.memories.R
import kotlinx.android.synthetic.main.format_fragment.*

class FormatFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.format_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerFormat.asHorizontalList()
        recyclerFormat.adapter = FormatAdapter { item, position ->

        }
    }
}