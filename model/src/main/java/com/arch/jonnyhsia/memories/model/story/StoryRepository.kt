package com.arch.jonnyhsia.memories.model.story

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.core.content.edit
import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.memories.model.story.bean.*
import com.arch.jonnyhsia.mirror.logger.logd
import com.jonnyhsia.appcore.livebus.LiveBus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
object StoryRepository : Repository(), StoryDataSource {

    private val saveSubject = PublishSubject.create<StoryDraft>()

    private val draftPref: SharedPreferences by lazy {
        sharedPreferenceOf("draft")
    }

    init {
        saveSubject
                .debounce(1000L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    logd("Content Saved.")
                    draftPref.edit {
                        putString("title", it.title)
                        putString("content", it.content)
                        putLong("create_time", it.time)
                    }
                    LiveBus.dispatch(it)
                }, {
                })
    }

    override fun getLatestDraft(): StoryDraft? {
        return draftPref.run {
            StoryDraft(title = getString("title", "")!!,
                    content = getString("content", "")!!,
                    time = getLong("create_time", -1L))
        }
    }

    override fun save(title: String, content: String) {
        saveSubject.onNext(StoryDraft(title, content, System.currentTimeMillis()))
    }

    override fun getUserStories(userId: Int, page: Int): Single<List<StoryDisplayModel>> {
        return Single.just(listOf(
                StoryDisplayModel(id = 0, title = "不可知的未来", summary = "我要挣钱，然后换个房子，房间有阳台、飘窗。天晴时候，会有太阳照进来，空气都是橘子的颜色。可以躺在窗边惬意，睡午觉，打电玩，怎样都好。独独忘记赶跑孤独，什么事情都缺少意义。", tags = listOf(), dateText = "11:29AM, 4月8日",
                        imageRatio = 5f / 3, image = "https://i.loli.net/2019/04/09/5cacba1792054.jpg", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "遥遥无期的家。", summary = "", tags = listOf(), dateText = "11:49PM, 12月6日",
                        image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "万塘路300号的午夜声响", summary = "偶尔是会失眠，比如今天。外面下着小雨，水落到棚子的声音。这么晚了，还能听到万塘路上车辆来往。就在刚刚，还能听见飞机轰隆隆地驶过去了。\n" +
                        "换个更轻松的睡姿，然后床板不配合地嘎吱作响。脑海里的说话声不受控制地在播放。\n" +
                        "质问，抱怨。没个所以然。\n" +
                        "好像有点困了。", tags = listOf(), dateText = "1:39AM, 4月3日",
                        image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "@饭炒了个蛋：很多崩溃都是悄无声息的，不怕加班加到11点，不怕女朋友说你那么忙那么点工资有意思吗，不怕父母一次次的催你回家乡，而是没有人理解你的心情，也没有人想要理解你的心情。成年人的崩溃，从捂住嘴巴说不出话开始。\nvia sina weibo", tags = listOf(), dateText = "11:29AM, 4月8日", image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "原来雅雅是鸡。千真万确的全宇宙最野的鸡。", tags = listOf(), dateText = "11:28PM, 4月9日",
                        imageRatio = 1.641f, image = "https://i.loli.net/2019/04/09/5cacbca17d946.jpg", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "每个人都有各自的打算，可我对于自己的将来一点打算也没有。\n" +
                        "很糟糕的状态，不知道应该做什么。\n" +
                        "没有头绪。\n" +
                        "束手无策的时候，希望身边能有人一起。好懒惰，总想着别人能搭个手，拉一把。\n" +
                        "每个人的路都不尽相同，我也不知道我在强求什么。\n" +
                        "习惯了和大家一起跑，一转眼跑道上就我一个人。有点沮丧。很不恰当的比喻。…", tags = listOf(), dateText = "1:08AM, 11月22日", image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png"))
        )).delay(1300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTimeline(page: Int): Single<List<Any>> {
        return Single.just(listOf(
                FeaturedStoriesModel(title = "特别推荐", list = listOf(
                        StoryDisplayModel(id = 0, title = "不可知的未来", summary = "我要挣钱，然后换个房子，房间有阳台、飘窗。天晴的时候", image = "https://i.loli.net/2019/04/09/5cacbfc38e410.jpg", tags = listOf("土豆碎碎念"), dateText = "11:29AM, 4月8日"),
                        StoryDisplayModel(id = 0, title = "#只狼 影逝二度#", summary = "可算是白金啦 ✌️👍", image = "https://i.loli.net/2019/04/13/5cb0b71cb9e76.jpg", tags = listOf("围观大佬"), dateText = "11:29AM, 4月8日"),
                        StoryDisplayModel(id = 0, title = "万塘路300号的午夜声响", summary = "偶尔是会失眠，比如今天。外面下着小雨，水落到棚子的声音。这么晚了，还能听", image = "https://i.loli.net/2019/04/09/5cacbfc38e410.jpg", tags = listOf("失眠症候群"), dateText = "11:29AM, 4月8日"),
                        StoryDisplayModel(id = 0, title = "遥遥无期的家。", summary = "", image = "https://i.loli.net/2019/04/09/5cacba1792054.jpg", tags = listOf("土豆碎碎念"), dateText = "11:29AM, 4月8日"),
                        StoryDisplayModel(id = 0, title = "", summary = "原来雅雅是鸡。千真万确的全宇宙最野的鸡。", image = "https://i.loli.net/2019/04/09/5cacbca17d946.jpg", tags = listOf("土豆碎碎念"), dateText = "11:29AM, 4月8日")
                )),
                StoryHeaderModel(title = "Memories", tags = listOf(
                        TagModel(id = 0, text = "日常", icon = "https://i.loli.net/2019/04/09/5cacc06a6c622.jpg"),
                        TagModel(id = 0, text = "情绪", icon = "https://i.loli.net/2019/04/09/5cacbfc32e308.jpg"),
                        TagModel(id = 0, text = "幻想", icon = "https://i.loli.net/2019/04/09/5cacc06a6fadd.jpg"),
                        TagModel(id = 0, text = "碎碎念", icon = "https://i.loli.net/2019/04/09/5cacc06a7a330.jpg"),
                        TagModel(id = 0, text = "矛盾", icon = "https://i.loli.net/2019/04/09/5cacc06a6c622.jpg"),
                        TagModel(id = 0, text = "想不出", icon = "https://i.loli.net/2019/04/09/5cacbfc32e308.jpg"),
                        TagModel(id = 0, text = "标签了", icon = "https://i.loli.net/2019/04/09/5cacc06a7a330.jpg")
                )),
                StoryDisplayModel(id = 0, title = "不可知的未来", summary = "我要挣钱，然后换个房子，房间有阳台、飘窗。天晴时候，会有太阳照进来，空气都是橘子的颜色。可以躺在窗边惬意，睡午觉，打电玩，怎样都好。独独忘记赶跑孤独，什么事情都缺少意义。", tags = listOf(), dateText = "11:29AM, 4月8日",
                        imageRatio = 5f / 3, image = "https://i.loli.net/2019/04/09/5cacba1792054.jpg", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "遥遥无期的家。", summary = "", tags = listOf(), dateText = "11:49PM, 12月6日",
                        image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                UpdateFriendsModel(title = "朋友新动态", friends = listOf(
                        SimpleUserDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/09/5cacc06a6ad5f.jpg", nickname = "守望土豆"),
                        SimpleUserDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/09/5cacc06a69416.jpg", nickname = "吾辈摩尔纳豆"),
                        SimpleUserDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/09/5cacc06a64c36.jpg", nickname = "摔键盘的豆"),
                        SimpleUserDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/09/5cacc06a66756.jpg", nickname = "吼姆拉豆")
                )),
                StoryDisplayModel(id = 0, title = "万塘路300号的午夜声响", summary = "偶尔是会失眠，比如今天。外面下着小雨，水落到棚子的声音。这么晚了，还能听到万塘路上车辆来往。就在刚刚，还能听见飞机轰隆隆地驶过去了。\n" +
                        "换个更轻松的睡姿，然后床板不配合地嘎吱作响。脑海里的说话声不受控制地在播放。\n" +
                        "质问，抱怨。没个所以然。\n" +
                        "好像有点困了。", tags = listOf(), dateText = "1:39AM, 4月3日",
                        image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "@饭炒了个蛋：很多崩溃都是悄无声息的，不怕加班加到11点，不怕女朋友说你那么忙那么点工资有意思吗，不怕父母一次次的催你回家乡，而是没有人理解你的心情，也没有人想要理解你的心情。成年人的崩溃，从捂住嘴巴说不出话开始。\nvia sina weibo", tags = listOf(), dateText = "11:29AM, 4月8日", image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "原来雅雅是鸡。千真万确的全宇宙最野的鸡。", tags = listOf(), dateText = "11:28PM, 4月9日",
                        imageRatio = 1.641f, image = "https://i.loli.net/2019/04/09/5cacbca17d946.jpg", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png")),
                StoryDisplayModel(id = 0, title = "", summary = "每个人都有各自的打算，可我对于自己的将来一点打算也没有。\n" +
                        "很糟糕的状态，不知道应该做什么。\n" +
                        "没有头绪。\n" +
                        "束手无策的时候，希望身边能有人一起。好懒惰，总想着别人能搭个手，拉一把。\n" +
                        "每个人的路都不尽相同，我也不知道我在强求什么。\n" +
                        "习惯了和大家一起跑，一转眼跑道上就我一个人。有点沮丧。很不恰当的比喻。…", tags = listOf(), dateText = "1:08AM, 11月22日", image = "", author = AuthorModel(id = 0, nickname = "高能的土豆", avatar = "https://i.loli.net/2019/04/09/5cacb02b09412.png"))
        ))
                .delay(1300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}