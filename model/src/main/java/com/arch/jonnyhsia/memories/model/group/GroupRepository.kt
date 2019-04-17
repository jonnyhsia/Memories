package com.arch.jonnyhsia.memories.model.group

import com.arch.jonnyhsia.memories.model.Repository
import com.arch.jonnyhsia.memories.model.group.bean.GroupListModel
import com.arch.jonnyhsia.memories.model.group.bean.GroupTagModel
import com.arch.jonnyhsia.memories.model.story.bean.DiscussDisplayModel
import com.arch.jonnyhsia.memories.model.story.bean.GroupDisplayModel
import com.arch.jonnyhsia.memories.model.story.bean.TopDiscussListModel
import com.arch.jonnyhsia.memories.model.story.bean.TopDiscussModel
import io.reactivex.Single

object GroupRepository : Repository(), GroupDataSource {

    override fun getList(): Single<List<Any>> {
        val groupDc = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a7fc94.jpg", name = "DC观影分队", description = "")
        val groupNewe = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a8f353.jpg", name = "新维野鸡场", description = "")
        val groupNintendo = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a90d6b.jpg", name = "任豚索狗撕逼群", description = "")
        val groupBattle = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a92826.jpg", name = "漫威DC撕逼专用小组", description = "")
        val groupMemories = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a9a504.jpg", name = "Memories意见反馈小组", description = "")
        val groupEverything = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a7be7f.jpg", name = "杂七杂八小组", description = "")
        val groupTs = GroupDisplayModel(id = 0, avatar = "https://i.loli.net/2019/04/17/5cb6b21a7d86e.jpg", name = "泰勒斯威夫特粉丝团", description = "")

        return Single.just(listOf(
                TopDiscussListModel(title = "圆桌会谈", list = listOf(
                        TopDiscussModel(id = 0, image = "https://i.loli.net/2019/04/09/5cacbfc39c3e4.jpg", title = "只狼受难者联盟", description = "誓要宫崎英高跌落神坛", meta = "1,300 用户发言"),
                        TopDiscussModel(id = 0, image = "https://i.loli.net/2019/04/09/5cacbfc33fdfb.jpg", title = "你的职业都有哪些不为人知的辛酸", description = "", meta = ""),
                        TopDiscussModel(id = 0, image = "https://i.loli.net/2019/04/09/5cacbfc35ae94.jpg", title = "为孩子们征集「未来生活100问」", description = "", meta = ""),
                        TopDiscussModel(id = 0, image = "https://i.loli.net/2019/04/09/5cacbfc358c1a.jpg", title = "土豆🥔家好玩吗，还想再去吗~", description = "", meta = ""),
                        TopDiscussModel(id = 0, image = "https://i.loli.net/2019/04/09/5cacbfc35414c.jpg", title = "相册里的只有你知道的故事", description = "", meta = "")
                )),
                GroupListModel(
                        tags = listOf(
                                GroupTagModel(id = 0, name = "我的小组"),
                                GroupTagModel(id = 0, name = "发现新小组"),
                                GroupTagModel(id = 0, name = "寻找同类"),
                                GroupTagModel(id = 0, name = "好好生活")
                        ),
                        groupOfTagList = listOf(
                                listOf(
                                        groupMemories,
                                        groupNewe,
                                        groupNintendo,
                                        groupBattle,
                                        groupDc,
                                        groupTs,
                                        groupEverything
                                ),
                                listOf(
                                        groupMemories,
                                        groupNewe,
                                        groupNintendo,
                                        groupDc,
                                        GroupDisplayModel(id = 0, avatar = "", name = "", description = "")
                                )
                        )
                ),
                "正在讨论",
                DiscussDisplayModel(id = 0, title = "雷霆沙赞票房彻底扑街了，连调音师都打不过了", description = "真的没想到，才第五天票房就连一千万都达不到了，DC的宣发真的烂透了，请我去做宣发都比DC的宣发好。", image = "https://i.loli.net/2019/04/17/5cb6b381cf6f2.jpg", group = groupDc, comments = 25, updateTime = "刚刚更新", tint = "#371400"),
                DiscussDisplayModel(id = 0, title = "野鸡场头像里仙仙的女子是谁啊？是雅雅的女朋友吗？", description = "", image = "https://i.loli.net/2019/04/17/5cb6b381c4064.jpg", group = groupNewe, comments = 14, updateTime = "3分钟前更新", tint = "#002854"),
                DiscussDisplayModel(id = 0, title = "Memories 还没有推出会员服务吗！？强烈要求下一版本添加！", description = "App 做的真实太美丽惹，简直是 Android 之光！虽然还没有功能，但已经迫不及待要捐赠了有木有~！", image = null, group = groupMemories, comments = 3, updateTime = "刚刚更新", tint = null),
                DiscussDisplayModel(id = 0, title = "请各位🐔都 Buy TS7 on iTunes", description = "划动你们的鸡爪子前往 aprilTwentySix.com 和我一起倒数啾咪", image = "https://i.loli.net/2019/04/17/5cb6b38182511.jpg", group = groupTs, comments = 130, updateTime = "5分钟前更新", tint = "#5F0600"),
                DiscussDisplayModel(id = 0, title = "喜迎专八旅人中文化 🎉", description = "专八旅人即将在六月份开启中文配信，精明的玩家已经开始大量收购用于投资理财，导致淘宝商家大量缺货。而另外一些玩家则坐等PC版本发布并预购了3DM正版。", image = "", group = groupNintendo, comments = 3, updateTime = "刚刚更新", tint = null),
                DiscussDisplayModel(id = 0, title = "🕯💔阿莫西林的钟楼没了？", description = "", image = "", group = groupEverything, comments = 1, updateTime = "1小时前更新", tint = null),
                DiscussDisplayModel(id = 0, title = "你更喜欢五一休三天还是休四天？", description = "今年突兀地得到消息称五一长假改为四天，并且前一周周日与后一周周日调休为工作日。也就是说本可人儿下周无休，下下周也无休？？？", image = "", group = groupDc, comments = 3, updateTime = "刚刚更新", tint = null),
                DiscussDisplayModel(id = 0, title = "剧透妇联4 你妈儿子死了 淦你妈儿子鸡掰!", description = "", image = "", group = groupBattle, comments = 3, updateTime = "1小时前更新", tint = null)
        ))
    }
}