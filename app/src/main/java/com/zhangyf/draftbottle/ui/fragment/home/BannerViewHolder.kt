package com.zhangyf.draftbottle.ui.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.zhangyf.draftbottle.R
import com.zhangyf.draftbottle.databinding.ItemBannerBinding
import com.zhouwei.mzbanner.holder.MZViewHolder


class BannerViewHolder : MZViewHolder<String> {

    var bannerViewBinding: ItemBannerBinding? = null
    override fun onBind(context: Context?, position: Int, data: String?) {
        bannerViewBinding?.imgUrl = data
    }

    override fun createView(context: Context?): View {
        bannerViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_banner,
            null,
            false
        )

        // 返回页面布局
        return bannerViewBinding?.root!!
    }

}