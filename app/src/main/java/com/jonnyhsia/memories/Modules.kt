package com.jonnyhsia.memories

import com.arch.jonnyhsia.memories.model.Repository
import com.jonnyhsia.memories.page.about.AboutViewModel
import com.jonnyhsia.memories.page.compose.ComposeViewModel
import com.jonnyhsia.memories.page.login.LoginViewModel
import com.jonnyhsia.memories.page.main.MainViewModel
import com.jonnyhsia.memories.page.main.account.AccountViewModel
import com.jonnyhsia.memories.page.main.account.storylist.StoryListViewModel
import com.jonnyhsia.memories.page.main.discuss.DiscussViewModel
import com.jonnyhsia.memories.page.main.timeline.TimelineViewModel
import com.jonnyhsia.memories.page.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Repository.getStoryDataSource() }
    single { Repository.getPassportDataSource() }
    single { Repository.getMiscDataSource() }
    single { Repository.getTrophyDataSource() }
    single { Repository.getGroupDataSource() }
}

val mvvmModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { (id: String) -> LoginViewModel(id, get()) }
    viewModel { TimelineViewModel() }
    viewModel { DiscussViewModel(get()) }
    viewModel { AccountViewModel(get()) }
    viewModel { StoryListViewModel(get(), get()) }
    viewModel { AboutViewModel() }
    viewModel { ComposeViewModel() }
}