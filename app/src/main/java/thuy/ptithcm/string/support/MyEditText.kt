package thuy.ptithcm.string.support

import android.R
import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText


class MyEditText : EditText {
    private var mIsError = false

    constructor(context: Context?) : this(context, null, 0) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) { // empty
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                setError(null)
            }

            override fun afterTextChanged(s: Editable) { // empty
            }
        })
    }

    override fun setError(error: CharSequence?) {
        mIsError = true
        super.setError(error)
        refreshDrawableState()
    }

    override fun setError(error: CharSequence, icon: Drawable) {
        mIsError = true
        super.setError(error, icon)
        refreshDrawableState()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
//        if (mIsError) {
//            View.mergeDrawableStates(
//                drawableState,
//                STATE_ERROR
//            )
//        }
        return drawableState
    }

//    companion object {
//        private val STATE_ERROR = intArrayOf(R.attr.state_error)
//    }
}