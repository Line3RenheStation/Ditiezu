package com.passionpenguin.ditiezu

import android.content.Context
import android.util.Log

class CategoryContent(val context: Context) {
    private fun string(id: Int): String {
        var str = ""
        try {
            str = context.resources.getString(id)
        } catch (e: Exception) {
            Log.i("", e.toString())
        }
        Log.i(str, str)
        return str
    }

    val categoryList = mutableListOf(
        CategoryItem(
            string(R.string.category_Beijing),
            string(R.string.description_Beijing), R.drawable.beijing
        ),
        CategoryItem(
            string(R.string.category_Tianjin),
            string(R.string.description_Tianjin), R.drawable.tianjin
        ),
        CategoryItem(
            string(R.string.category_Shanghai),
            string(R.string.description_Shanghai), R.drawable.shanghai
        ),
        CategoryItem(
            string(R.string.category_Guangzhou),
            string(R.string.description_Guangzhou), R.drawable.guangzhou
        ),
        CategoryItem(
            string(R.string.category_Changchun),
            string(R.string.description_Changchun), R.drawable.changchun
        ),
        CategoryItem(
            string(R.string.category_Dalian),
            string(R.string.description_Dalian), R.drawable.dalian
        ),
        CategoryItem(
            string(R.string.category_Wuhan),
            string(R.string.description_Wuhan), R.drawable.wuhan
        ),
        CategoryItem(
            string(R.string.category_Chongqing),
            string(R.string.description_Chongqing), R.drawable.chongqing
        ),
        CategoryItem(
            string(R.string.category_Shenzhen),
            string(R.string.description_Shenzhen), R.drawable.shenzhen
        ),
        CategoryItem(
            string(R.string.category_Nanjing),
            string(R.string.description_Nanjing), R.drawable.nanjing
        ),
        CategoryItem(
            string(R.string.category_Chengdu),
            string(R.string.description_Chengdu), R.drawable.chengdu
        ),
        CategoryItem(
            string(R.string.category_Shenyang),
            string(R.string.description_Shenyang), R.drawable.shenyang
        ),
        CategoryItem(
            string(R.string.category_Foshan),
            string(R.string.description_Foshan), R.drawable.foshan
        ),
        CategoryItem(
            string(R.string.category_Xian),
            string(R.string.description_Xian), R.drawable.xian
        ),
        CategoryItem(
            string(R.string.category_Suzhou),
            string(R.string.description_Suzhou), R.drawable.suzhou
        ),
        CategoryItem(
            string(R.string.category_Kunming),
            string(R.string.description_Kunming), R.drawable.kunming
        ),
        CategoryItem(
            string(R.string.category_Hangzhou),
            string(R.string.description_Hangzhou), R.drawable.hangzhou
        ),
        CategoryItem(
            string(R.string.category_Harbin),
            string(R.string.description_Harbin), R.drawable.harbin
        ),
        CategoryItem(
            string(R.string.category_Zhengzhou),
            string(R.string.description_Zhengzhou), R.drawable.zhengzhou
        ),
        CategoryItem(
            string(R.string.category_Changsha),
            string(R.string.description_Changsha), R.drawable.changsha
        ),
        CategoryItem(
            string(R.string.category_Ningbo),
            string(R.string.description_Ningbo), R.drawable.ningbo
        ),
        CategoryItem(
            string(R.string.category_Wuxi),
            string(R.string.description_Wuxi), R.drawable.wuxi
        ),
        CategoryItem(
            string(R.string.category_Qingdao),
            string(R.string.description_Qingdao), R.drawable.qingdao
        ),
        CategoryItem(
            string(R.string.category_Nanchang),
            string(R.string.description_Nanchang), R.drawable.nanchang
        ),
        CategoryItem(
            string(R.string.category_Fuzhou),
            string(R.string.description_Fuzhou), R.drawable.fuzhou
        ),
        CategoryItem(
            string(R.string.category_Dongguan),
            string(R.string.description_Dongguan), R.drawable.dongguan
        ),
        CategoryItem(
            string(R.string.category_Nanning),
            string(R.string.description_Nanning), R.drawable.nanning
        ),
        CategoryItem(
            string(R.string.category_Hefei),
            string(R.string.description_Hefei), R.drawable.hefei
        ),
        CategoryItem(
            string(R.string.category_Shijiazhuang),
            string(R.string.description_Shijiazhuang), R.drawable.shijiazhuang
        ),
        CategoryItem(
            string(R.string.category_Guiyang),
            string(R.string.description_Guiyang), R.drawable.guiyang
        ),
        CategoryItem(
            string(R.string.category_Xiamen),
            string(R.string.description_Xiamen), R.drawable.xiamen
        ),
        CategoryItem(
            string(R.string.category_Urumqi),
            string(R.string.description_Urumqi), R.drawable.urumqi
        ),
        CategoryItem(
            string(R.string.category_Wenzhou),
            string(R.string.description_Wenzhou), R.drawable.wenzhou
        ),
        CategoryItem(
            string(R.string.category_Jinan),
            string(R.string.description_Jinan), R.drawable.jinan
        ),
        CategoryItem(
            string(R.string.category_Lanzhou),
            string(R.string.description_Lanzhou), R.drawable.lanzhou
        ),
        CategoryItem(
            string(R.string.category_Changzhou),
            string(R.string.description_Changzhou), R.drawable.changzhou
        ),
        CategoryItem(
            string(R.string.category_Xuzhou),
            string(R.string.description_Xuzhou), R.drawable.xuzhou
        ),
        CategoryItem(
            string(R.string.category_Hongkong),
            string(R.string.description_Hongkong), R.drawable.hongkong
        ),
        CategoryItem(
            string(R.string.category_Macau),
            string(R.string.description_Macau), R.drawable.macau
        ),
        CategoryItem(
            string(R.string.category_Taiwan),
            string(R.string.description_Taiwan), R.drawable.taiwan
        )
    )
    val categoryId = arrayOf(
        7,
        6,
        8,
        23,
        40,
        41,
        39,
        38,
        24,
        22,
        53,
        50,
        56,
        54,
        51,
        70,
        52,
        55,
        64,
        67,
        65,
        68,
        66,
        71,
        72,
        75,
        73,
        74,
        140,
        76,
        77,
        143,
        142,
        148,
        78,
        48,
        144,
        151,
        28,
        79,
        36,
        47,
        37
    )
}