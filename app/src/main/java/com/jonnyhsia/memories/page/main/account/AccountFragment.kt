package com.jonnyhsia.memories.page.main.account

import android.os.Bundle
import android.view.View
import com.jonnyhsia.appcore.component.BaseFragment
import com.jonnyhsia.memories.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<AccountViewModel>() {
    override val layoutRes: Int
        get() = R.layout.account_fragment

    override val vm: AccountViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}