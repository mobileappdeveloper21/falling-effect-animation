package com.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.myapplication.databinding.EmojiItemRvBinding

class EmojiAdapter(val itemClickCallback:(String)->Unit) :RecyclerView.Adapter<EmojiAdapter.EmojiViewHolder>() {

    private val emojiList = arrayListOf(
        "\uD83D\uDE00", // 😀 Grinning Face
        "\uD83D\uDE01", // 😁 Beaming Face with Smiling Eyes
        "\uD83D\uDE02", // 😂 Face with Tears of Joy
        "\uD83D\uDE03", // 😃 Grinning Face with Big Eyes
        "\uD83D\uDE04", // 😄 Grinning Face with Smiling Eyes
        "\uD83D\uDE05", // 😅 Grinning Face with Sweat
        "\uD83D\uDE06", // 😆 Grinning Squinting Face
        "\uD83D\uDE07", // 😉 Winking Face
        "\uD83D\uDE08", // 😊 Smiling Face with Smiling Eyes
        "\uD83D\uDE09", // 😋 Face Savoring Food
        "\uD83D\uDE0A", // 😊 Smiling Face with Smiling Eyes
        "\uD83D\uDE0B", // 😋 Face Savoring Food
        "\uD83D\uDE0C", // 😌 Relieved Face
        "\uD83D\uDE0D", // 😍 Smiling Face with Heart-Eyes
        "\uD83D\uDE0E", // 😎 Smiling Face with Sunglasses
        "\uD83D\uDE0F", // 😏 Smirking Face
        "\uD83D\uDE10", // 😐 Neutral Face
        "\uD83D\uDE11", // 😑 Expressionless Face
        "\uD83D\uDE12", // 😒 Unamused Face
        "\uD83D\uDE13", // 😓 Downcast Face with Sweat
        "\uD83D\uDE14", // 😔 Pensive Face
        "\uD83D\uDE15", // 😕 Confused Face
        "\uD83D\uDE16", // 😖 Confounded Face
        "\uD83D\uDE17", // 😗 Kissing Face
        "\uD83D\uDE18", // 😘 Face Blowing a Kiss
        "\uD83D\uDE19", // 😙 Kissing Face with Smiling Eyes
        "\uD83D\uDE1A", // 😚 Kissing Face with Closed Eyes
        "\uD83D\uDE1B", // 😛 Face with Tongue
        "\uD83D\uDE1C", // 😜 Winking Face with Tongue
        "\uD83D\uDE1D", // 😝 Squinting Face with Tongue
        "\uD83D\uDE1E", // 😞 Disappointed Face
        "\uD83D\uDE1F", // 😟 Worried Face
        "\uD83D\uDE20", // 😠 Angry Face
        "\uD83D\uDE21", // 😡 Pouting Face
        "\uD83D\uDE22", // 😢 Crying Face
        "\uD83D\uDE23", // 😣 Persevering Face
        "\uD83D\uDE24", // 😤 Face with Steam From Nose
        "\uD83D\uDE25", // 😥 Sad but Relieved Face
        "\uD83D\uDE26", // 😦 Frowning Face with Open Mouth
        "\uD83D\uDE27", // 😧 Anguished Face
        "\uD83D\uDE28", // 😨 Fearful Face
        "\uD83D\uDE29", // 😩 Weary Face
        "\uD83D\uDE2A", // 😪 Sleepy Face
        "\uD83D\uDE2B", // 😫 Tired Face
        "\uD83D\uDE2C", // 😬 Grimacing Face
        "\uD83D\uDE2D", // 😭 Loudly Crying Face
        "\uD83D\uDE2E", // 😮 Face with Open Mouth
        "\uD83D\uDE2F", // 😯 Hushed Face
        "\uD83D\uDE30", // 😰 Face with Open Mouth and Cold Sweat
        "\uD83D\uDE31", // 😱 Face Screaming in Fear
        "\uD83D\uDE32", // 😲 Astonished Face
        "\uD83D\uDE33", // 😳 Flushed Face
        "\uD83D\uDE34", // 😴 Sleeping Face
        "\uD83D\uDE35", // 😵 Dizzy Face
        "\uD83D\uDE36", // 😷 Face with Medical Mask
        "\uD83D\uDE37", // 😸 Grinning Cat with Smiling Eyes
        "\uD83D\uDE38", // 😹 Cat with Tears of Joy
        "\uD83D\uDE39", // 😺 Smiling Cat with Open Mouth
        "\uD83D\uDE3A", // 😻 Smiling Cat with Heart-Eyes
        "\uD83D\uDE3B", // 😼 Cat with Wry Smile
        "\uD83D\uDE3C", // 😽 Kissing Cat
        "\uD83D\uDE3D", // 😾 Pouting Cat
        "\uD83D\uDE3E", // 😿 Crying Cat
        "\uD83D\uDE3F", // 🙀 Weary Cat
        "\uD83D\uDE40", // 🙁 Slightly Frowning Face
        "\uD83D\uDE41", // 🙂 Slightly Smiling Face
        "\uD83D\uDE42", // 🙃 Upside-Down Face
        "\uD83D\uDE43", // 🙄 Face with Rolling Eyes
        "\uD83D\uDE44", // 🙅 Person Gesturing No
        "\uD83D\uDE45", // 🙆 Person Gesturing OK
        "\uD83D\uDE46", // 🙇 Person Bowing
        "\uD83D\uDE47", // 🙈 See-No-Evil Monkey
        "\uD83D\uDE48", // 🙉 Hear-No-Evil Monkey
        "\uD83D\uDE49", // 🙊 Speak-No-Evil Monkey
        "\uD83D\uDE4A", // 🙋 Person Raising Hand
        "\uD83D\uDE4B", // 🙌 Person Raising Both Hands in Celebration
        "\uD83D\uDE4C", // 🙍 Person Frowning
        "\uD83D\uDE4D", // 🙎 Person Pouting
        "\uD83D\uDE4E", // 🙏 Folded Hands
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiViewHolder {
        return EmojiViewHolder(EmojiItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: EmojiViewHolder, position: Int) {
        val emoji= emojiList[position]
        holder.binding.emojiText.text = emoji
        holder.binding.root.setOnClickListener {
            itemClickCallback.invoke(emoji)
        }
    }

    override fun getItemCount(): Int = emojiList.size

    inner class EmojiViewHolder(val binding: EmojiItemRvBinding):RecyclerView.ViewHolder(binding.root)

}