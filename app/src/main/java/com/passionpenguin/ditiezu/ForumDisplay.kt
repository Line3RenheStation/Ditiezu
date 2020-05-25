package com.passionpenguin.ditiezu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.postDelayed
import org.jsoup.Jsoup


class ForumDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val extras = intent.extras
        var fid = 23
        if (extras != null) {
            fid = extras.getInt("fid")
        } else finish()

        val homeButton: LinearLayout = findViewById(R.id.HomeButton)
        val categoryButton: LinearLayout = findViewById(R.id.CategoryButton)
        val notificationButton: LinearLayout = findViewById(R.id.NotificationButton)
        val accountButton: LinearLayout = findViewById(R.id.AccountButton)
        val categoryListContainer: LinearLayout = findViewById(R.id.CategoryListContainer)
        val categoryListMaskContainer: LinearLayout = findViewById(R.id.CategoryListMaskContainer)
        val categoryListView: ListView = findViewById(R.id.CategoryList)
        val threadListView: ListView = findViewById(R.id.ThreadList)
        val mask: LinearLayout = findViewById(R.id.LoadingMaskContainer)

        val categoryContent = CategoryContent(applicationContext)
        val categoryList = categoryContent.categoryList
        val categoryId = categoryContent.categoryId

        fun bottomButtonHighlight(clear: Array<LinearLayout>, add: LinearLayout) {
            clear.forEach {
                (it.getChildAt(0) as ImageView).setColorFilter(
                    ContextCompat.getColor(applicationContext, R.color.black),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
                (it.getChildAt(1) as TextView).setTextColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.black
                    )
                )
            }
            (add.getChildAt(0) as ImageView).setColorFilter(
                ContextCompat.getColor(applicationContext, R.color.primary700),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            (add.getChildAt(1) as TextView).setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.primary700
                )
            )
            mask.visibility = View.GONE
        }

        fun categoryDialogController(state: Boolean) {
            val ctrlAnimation = if (state) TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF, 0F, TranslateAnimation.RELATIVE_TO_SELF, 1F
            ) else TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0F, TranslateAnimation.RELATIVE_TO_SELF, 0F,
                TranslateAnimation.RELATIVE_TO_SELF, 1F, TranslateAnimation.RELATIVE_TO_SELF, 0F
            )
            ctrlAnimation.duration = 400L //设置动画的过渡时间

            categoryListMaskContainer.visibility = View.VISIBLE
            categoryListContainer.startAnimation(ctrlAnimation)
            if (state) {
                categoryListMaskContainer.postDelayed(400) {
                    categoryListMaskContainer.visibility = View.GONE
                }
                bottomButtonHighlight(
                    arrayOf(categoryButton, notificationButton, accountButton),
                    homeButton
                )
            }
        }

        fun loadForumContent(page: Int) {
            fun processResult(result: String) {
                val parser = Jsoup.parse(result)
                runOnUiThread {
                    val bannerView = layoutInflater.inflate(R.layout.category_banner, null)
                    bannerView.findViewById<ImageView>(R.id.CategoryIcon)
                        .setImageDrawable(
                            resources.getDrawable(
                                categoryList[categoryId.indexOf(fid)].icon,
                                null
                            )
                        )
                    bannerView.findViewById<TextView>(R.id.CategoryTitle).text =
                        categoryList[categoryId.indexOf(fid)].title
                    threadListView.addHeaderView(bannerView)



                    val threadListContent = mutableListOf<ThreadListItem>()
                    parser.select("[id^='normalthread_']").forEach {
                        val type = if (it.select(".new em").text() !== "") {
                            "[" + it.select(".new em").text() + "] "
                        } else {
                            ""
                        }
                        val author = it.select(".by cite a")
                        val authorName = author.text()
                        val time = it.select(".by em span").text()
                        val title = it.select(".new .xst")
                        threadListContent.add(
                            ThreadListItem(
                                author.attr("href").substring(
                                    author.attr("href").indexOf("uid-") + 4,
                                    author.attr("href").indexOf(".html")
                                ).toInt(),
                                title.text(),
                                authorName,
                                "$type $time",
                                title.attr("href")
                                    .substring(30, title.attr("href").lastIndexOf("-1-1"))
                                    .toInt()
                            )
                        )
                    }
                    threadListView.adapter =
                        ThreadListAdapter(
                            this,
                            R.layout.thread_list_item,
                            threadListContent
                        )

                    threadListView.setOnItemClickListener { _, _, position, _ ->
                        if (position == 0) {
                            // Banner
                        } else {
                            val i = Intent(this@ForumDisplay, ViewThread::class.java)
                            i.putExtra(
                                "tid",
                                threadListContent[position - 1].target
                            )
                            Log.i("", threadListContent[position - 1].target.toString())
                            startActivity(i)
                        }
                    }
                }
            }

            HttpExt().retrievePage("http://www.ditiezu.com/forum-$fid-$page.html") {
                val ctrlAnimation = TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 0F,
                    TranslateAnimation.RELATIVE_TO_SELF, 0F,
                    TranslateAnimation.RELATIVE_TO_SELF, 0F,
                    TranslateAnimation.RELATIVE_TO_SELF, 1F
                )
                ctrlAnimation.duration = 400L
                runOnUiThread {
                    findViewById<LinearLayout>(R.id.LoadingMaskContainer).visibility = View.VISIBLE
                    findViewById<LinearLayout>(R.id.LoadingMaskContainer).startAnimation(
                        ctrlAnimation
                    )
                    findViewById<LinearLayout>(R.id.LoadingMaskContainer).postDelayed(400) {
                        findViewById<LinearLayout>(R.id.LoadingMaskContainer).visibility = View.GONE
                    }
                }

                if (it == "Failed Retrieved") {
                    // Failed Retrieved
                    Log.i("HTTPEXT", "FAILED RETRIEVED")
                }
                processResult(it)
            }
        }

        categoryListView.adapter =
            CategoryAdapter(
                this,
                R.layout.category_popup,
                categoryList
            )
        categoryListView.setOnItemClickListener { _, _, position, _ ->
            categoryDialogController(true)
            fid = categoryId[position]
            loadForumContent(1)
        }
        categoryListMaskContainer.setOnClickListener {
            categoryDialogController(true)
        }
        findViewById<LinearLayout>(R.id.LoadingMaskContainer).setOnClickListener {
            it.visibility = View.GONE
        }
        homeButton.setOnClickListener {
            bottomButtonHighlight(
                arrayOf(categoryButton, notificationButton, accountButton),
                homeButton
            )
        }
        categoryButton.setOnClickListener {
            bottomButtonHighlight(
                arrayOf(homeButton, notificationButton, accountButton),
                categoryButton
            )

            categoryDialogController(false)
        }
        notificationButton.setOnClickListener {
            bottomButtonHighlight(
                arrayOf(homeButton, categoryButton, accountButton),
                notificationButton
            )
        }
        accountButton.setOnClickListener {
            bottomButtonHighlight(
                arrayOf(homeButton, notificationButton, categoryButton),
                accountButton
            )
        }

        loadForumContent(1)
    }
}