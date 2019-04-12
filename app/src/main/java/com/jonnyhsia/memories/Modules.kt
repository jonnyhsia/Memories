package com.jonnyhsia.memories

import com.arch.jonnyhsia.memories.model.Repository
import com.jonnyhsia.memories.page.login.LoginViewModel
import com.jonnyhsia.memories.page.main.MainViewModel
import com.jonnyhsia.memories.page.main.discover.DiscoverViewModel
import com.jonnyhsia.memories.page.main.timeline.TimelineViewModel
import com.jonnyhsia.memories.page.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository.getPassportDataSource() }
    single { Repository.getMiscDataSource() }
    single { Repository.getTrophyDataSource() }
}

val mvvmModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { (id: String) -> LoginViewModel(id, get()) }
    viewModel { TimelineViewModel() }
    viewModel { DiscoverViewModel() }
}