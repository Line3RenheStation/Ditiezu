package com.passionpenguin.ditiezu.htmlTextView

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.provider.Browser
import android.text.ParcelableSpan
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import com.passionpenguin.ditiezu.ViewThread

open class CustomURLSpan(
    var uRL: String?
) : ClickableSpan(), ParcelableSpan {

    override fun onClick(widget: View) {
        val uri = Uri.parse(uRL)
        val context = widget.context
        val href = uri.toString()

        // IN BBS-THREAD REDIRECT
        if (href.contains("ditiezu.com") && (href.contains("mod=viewthread") || href.contains("thread-"))) {
            val i = Intent(context, ViewThread::class.java)
            val tid = if (href.contains("mod=viewthread")) href.substring(
                href.indexOf("tid=") + 4,
                href.indexOf("&", href.indexOf("tid=") + 4)
            ) else href.substring(
                href.indexOf("thread-") + 7,
                href.indexOf("-", href.indexOf("thread-") + 7)
            )

            i.putExtra("tid", tid.toInt())
            val page = when {
                (href.contains("thread-")) -> href.substring(
                    href.indexOf("-", href.indexOf("thread-") + 7) + 1,
                    href.indexOf("-", href.indexOf("-", href.indexOf("thread-") + 7) + 1)
                )
                (href.contains("page=")) -> href.substring(
                    href.indexOf(
                        "page=" + 5,
                        href.indexOf("&", href.indexOf("page") + 5)
                    )
                )
                else -> "1"
            }
            i.putExtra("page", page.toInt())
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
        // NON BBS-THREAD REDIRECT
        else {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, context.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.w("URLSpan", "Actvity was not found for intent, $intent")
            }
        }
    }

    override fun getSpanTypeId(): Int {
        return 0
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {}

}