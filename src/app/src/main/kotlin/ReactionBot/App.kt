/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ReactionBot

import kotlin.random.Random

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent

class BotClient : ListenerAdapter(){
    lateinit var jda: JDA

    val commentOfMention = listOf("早よ来い", "ゲームするぞボケ", "Valorantしよ")
    val commentOfWoman = listOf("女！女！！女！！！", "女！酒！金！", "おんなぁ", "女と電話するな")
    val commentOfYoutube = listOf("このクリップはすごい", "いやぁさすがだなあ", "えぇぇ", "これはmasan並み")
    val commentOfBF = listOf("BFやオーバーウォッチはゲームではないですよ", "valorantやりませんか？", "神ゲーｷﾀ━━━━(ﾟ∀ﾟ)━━━━!!")
    val commentOfUnrated = listOf("コンペから逃げるなxxx")
    val commentOfSubAccount = listOf("本アカから逃げるな", "スマーフは規約違反ですよ")
    val commentOfValorant = listOf("スパイクラッシュはクソ", "rion", "それはもうLazやん")

    val bfRegex = Regex("""(BF|bf|おばっち|オーバーウォッチ|overwatch|OW|ow)""")
    val subAccountRegex = Regex("""(サブ垢|サブアカ)""")
    val valorantRegex = Regex("""(Valorant|valorant|valo|ヴァロ|バロ)""")

    fun main(token: String) { //トークンを使ってBotを起動する部分
        jda = JDABuilder.createLight(token,
            GatewayIntent.GUILD_MESSAGES)
            .addEventListeners(this)
            .build()
    }

    override fun onReady(event: ReadyEvent) { //Botがログインしたときの処理
        println("[ReactionDiscordBot] Logged in")
    }

    override fun onGuildMessageReceived(event : GuildMessageReceivedEvent) {
        val message = event.message;

        if(event.author.isBot) return //Bot自身のメッセージは無視する

        // ダイス機能追加
        if(message.contentDisplay.startsWith("/dice")) {
            val dice = Random.nextInt(1, 101)
            message.channel.sendMessage("${dice}").queue()
        }

        // メンションのみのメッセージがあった時の処理
        if(message.contentDisplay.startsWith("@") && !message.contentDisplay.contains(" ")){
            randomSendMessage(event, commentOfMention)
        }

        // BFやオーバーウォッチを示唆するメッセージがあった時の処理
        if(bfRegex.containsMatchIn(message.contentDisplay)){
            randomSendMessage(event, commentOfBF)
        }

        // 女を含むテキストがあった時の処理
        if(message.contentDisplay.contains("女")){
            randomSendMessage(event, commentOfWoman)
        }

        // youtubeのリンクがあった時の処理
        if(message.contentDisplay.contains("www.youtube.com")){
            randomSendMessage(event, commentOfYoutube)
        }

        // アンレートを示唆するメッセージがあった時の処理
        if(message.contentDisplay.contains("アンレ") || message.contentDisplay.contains("あんれ")){
            randomSendMessage(event, commentOfUnrated)
        }

        // サブアカウントを示唆するメッセージがあった時の処理
        if(subAccountRegex.containsMatchIn(message.contentDisplay)){
            randomSendMessage(event, commentOfSubAccount)
        }

        // Valorantを示唆するメッセージがあった時の処理
        if(valorantRegex.containsMatchIn(message.contentDisplay)){
            randomSendMessage(event, commentOfValorant)
        }
    }

    private fun randomSendMessage(event: GuildMessageReceivedEvent, list: List<String>) {
        val sendMessage = list.get(Random.nextInt(list.size))
        event.channel.sendMessageFormat(sendMessage).queue()
    }
}

/*Kotlinではコード中から単体main関数を探して最初に実行します。
そこでmain関数にBotClientクラスのインスタンス作成とトークンを渡した起動処理mainメソッドを実行させる形でBotを起動します。
*/
fun main() {
    val bot = BotClient()
    bot.main(System.getenv("DISCORD_BOT_TOKEN"))
}
