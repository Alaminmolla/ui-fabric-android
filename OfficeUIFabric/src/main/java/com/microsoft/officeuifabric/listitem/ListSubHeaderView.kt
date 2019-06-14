/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.officeuifabric.listitem

import android.content.Context
import android.support.v4.widget.TextViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.microsoft.officeuifabric.R
import com.microsoft.officeuifabric.util.setContentAndUpdateVisibility
import com.microsoft.officeuifabric.view.TemplateView

/**
 * Sub header for list sections with a styled title view and [customAccessoryView].
 *
 * TODO add examples in the demo for [titleColor] and [customAccessoryView].
 */
class ListSubHeaderView : TemplateView {
    companion object {
        val DEFAULT_TITLE_COLOR = TitleColor.PRIMARY
        val DEFAULT_TRUNCATION = TextUtils.TruncateAt.END
    }

    enum class TitleColor {
        PRIMARY, BLACK
    }

    /**
     * Text for the title view that will be displayed at the start of the [ListSubHeaderView].
     */
    var title: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Sets the text color for the title view.
     */
    var titleColor: TitleColor = DEFAULT_TITLE_COLOR
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Sets the text truncation for the title view.
     */
    var titleTruncateAt: TextUtils.TruncateAt = DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * This view will be displayed at the end of the [ListSubHeaderView].
     */
    var customAccessoryView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        // TODO: Add need examples in the demo that tests these attributes. Can inflate a layout in the adapter.
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ListSubHeaderView)

        title = styledAttrs.getString(R.styleable.ListSubHeaderView_title) ?: ""

        val titleColorOrdinal = styledAttrs.getInt(R.styleable.ListSubHeaderView_titleColor, DEFAULT_TITLE_COLOR.ordinal)
        titleColor = TitleColor.values()[titleColorOrdinal]

        val titleTruncateAtOrdinal = styledAttrs.getInt(R.styleable.ListSubHeaderView_titleTruncateAt, DEFAULT_TRUNCATION.ordinal)
        titleTruncateAt = TextUtils.TruncateAt.values()[titleTruncateAtOrdinal]

        styledAttrs.recycle()
    }

    // Template

    override val templateId: Int
        get() = R.layout.view_list_sub_header

    private var titleView: TextView? = null
    private var customAccessoryViewContainer: RelativeLayout? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        titleView = findViewInTemplateById(R.id.list_sub_header_title)
        customAccessoryViewContainer = findViewInTemplateById(R.id.list_sub_header_custom_accessory_view_container)

        updateTemplate()
    }

    private fun updateTemplate() {
        updateTitleView()
        customAccessoryViewContainer?.setContentAndUpdateVisibility(customAccessoryView)
    }

    private fun updateTitleView() {
        val titleView = titleView ?: return

        titleView.text = title
        titleView.ellipsize = titleTruncateAt

        when (titleColor) {
            TitleColor.PRIMARY -> TextViewCompat.setTextAppearance(titleView, R.style.TextAppearance_UIFabric_ListSubHeaderTitle_Primary)
            TitleColor.BLACK -> TextViewCompat.setTextAppearance(titleView, R.style.TextAppearance_UIFabric_ListSubHeaderTitle_Black)
        }
    }
}