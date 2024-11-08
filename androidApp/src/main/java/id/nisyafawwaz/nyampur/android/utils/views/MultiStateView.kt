package id.nisyafawwaz.nyampur.android.utils.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import id.nisyafawwaz.nyampur.android.R

class MultiStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    enum class ViewState {
        CONTENT, LOADING, ERROR, EMPTY
    }

    private var contentView: View? = null
    private var loadingView: View? = null
    private var emptyView: View? = null
    private var errorView: View? = null
    var listener: StateListener? = null
    var animateLayoutChanges: Boolean = false

    var viewState: ViewState = ViewState.CONTENT
        set(value) {
            val prevField = field
            if (value != prevField) {
                field = value
                setView(prevField)
                listener?.onStateChanged(value)
            }
        }

    init {
        val inflater = LayoutInflater.from(context)
        val a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateView)

        val loadingViewResId = a.getResourceId(R.styleable.MultiStateView_msvLoadingView, -1)
        if (loadingViewResId > -1) {
            val inflatedLoadingView = inflater.inflate(loadingViewResId, this, false)
            loadingView = inflatedLoadingView
            addView(inflatedLoadingView, inflatedLoadingView.layoutParams)
        }

        val errorViewResId = a.getResourceId(R.styleable.MultiStateView_msvErrorView, -1)
        if (errorViewResId > -1) {
            val inflatedErrorView = inflater.inflate(errorViewResId, this, false)
            errorView = inflatedErrorView
            addView(inflatedErrorView, inflatedErrorView.layoutParams)
        }

        val emptyViewResId = a.getResourceId(R.styleable.MultiStateView_msvEmptyView, -1)
        if (emptyViewResId > -1) {
            val inflatedEmptyView = inflater.inflate(emptyViewResId, this, false)
            emptyView = inflatedEmptyView
            addView(inflatedEmptyView, inflatedEmptyView.layoutParams)
        }

        viewState = when (a.getInt(R.styleable.MultiStateView_msvViewState, VIEW_STATE_CONTENT)) {
            VIEW_STATE_ERROR -> ViewState.ERROR
            VIEW_STATE_EMPTY -> ViewState.EMPTY
            VIEW_STATE_LOADING -> ViewState.LOADING
            else -> ViewState.CONTENT
        }
        animateLayoutChanges = a.getBoolean(R.styleable.MultiStateView_msvAnimateViewChanges, false)

        a.recycle()
    }

    fun getView(state: ViewState): View? = when (state) {
        ViewState.CONTENT -> contentView
        ViewState.LOADING -> loadingView
        ViewState.ERROR -> errorView
        ViewState.EMPTY -> emptyView
    }

    fun setViewForState(view: View, state: ViewState, switchToState: Boolean = false) {
        when (state) {
            ViewState.LOADING -> {
                if (loadingView != null) removeView(loadingView)
                loadingView = view
                addView(view)
            }

            ViewState.EMPTY -> {
                if (emptyView != null) removeView(emptyView)
                emptyView = view
                addView(view)
            }

            ViewState.ERROR -> {
                if (errorView != null) removeView(errorView)
                errorView = view
                addView(view)
            }

            ViewState.CONTENT -> {
                if (contentView != null) removeView(contentView)
                contentView = view
                addView(view)
            }
        }

        if (switchToState) viewState = state
    }

    fun setViewForState(
        @LayoutRes layoutRes: Int,
        state: ViewState,
        switchToState: Boolean = false
    ) {
        val view = LayoutInflater.from(context).inflate(layoutRes, this, false)
        setViewForState(view, state, switchToState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (contentView == null) throw IllegalArgumentException("Content view is not defined")

        when (viewState) {
            ViewState.CONTENT -> setView(ViewState.CONTENT)
            else -> contentView?.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        return when (val superState = super.onSaveInstanceState()) {
            null -> superState
            else -> SavedState(superState, viewState)
        }
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is SavedState) {
            super.onRestoreInstanceState(state.superState)
            viewState = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun addView(child: View) {
        if (isValidContentView(child)) contentView = child
        super.addView(child)
    }

    override fun addView(child: View, index: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, index, params)
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, params)
    }

    override fun addView(child: View, width: Int, height: Int) {
        if (isValidContentView(child)) contentView = child
        super.addView(child, width, height)
    }

    override fun addViewInLayout(child: View, index: Int, params: ViewGroup.LayoutParams): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params)
    }

    override fun addViewInLayout(
        child: View,
        index: Int,
        params: ViewGroup.LayoutParams,
        preventRequestLayout: Boolean
    ): Boolean {
        if (isValidContentView(child)) contentView = child
        return super.addViewInLayout(child, index, params, preventRequestLayout)
    }

    private fun isValidContentView(view: View): Boolean {
        return if (contentView != null && contentView !== view) {
            false
        } else view != loadingView && view != errorView && view != emptyView
    }

    private fun setView(previousState: ViewState) {
        when (viewState) {
            ViewState.LOADING -> {
                requireNotNull(loadingView).apply {
                    contentView?.visibility = GONE
                    errorView?.visibility = GONE
                    emptyView?.visibility = GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getView(previousState))
                    } else {
                        visibility = VISIBLE
                    }
                }
            }

            ViewState.EMPTY -> {
                requireNotNull(emptyView).apply {
                    contentView?.visibility = GONE
                    errorView?.visibility = GONE
                    loadingView?.visibility = GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getView(previousState))
                    } else {
                        visibility = VISIBLE
                    }
                }
            }

            ViewState.ERROR -> {
                requireNotNull(errorView).apply {
                    contentView?.visibility = GONE
                    loadingView?.visibility = GONE
                    emptyView?.visibility = GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getView(previousState))
                    } else {
                        visibility = VISIBLE
                    }
                }
            }

            ViewState.CONTENT -> {
                requireNotNull(contentView).apply {
                    loadingView?.visibility = GONE
                    errorView?.visibility = GONE
                    emptyView?.visibility = GONE

                    if (animateLayoutChanges) {
                        animateLayoutChange(getView(previousState))
                    } else {
                        visibility = VISIBLE
                    }
                }
            }
        }
    }

    /**
     * Animates the layout changes between [ViewState]
     *
     * @param previousView The view that it was currently on
     */
    private fun animateLayoutChange(previousView: View?) {
        if (previousView == null) {
            requireNotNull(getView(viewState)).visibility = View.VISIBLE
            return
        }

        ObjectAnimator.ofFloat(
            previousView,
            "alpha",
            1.0f,
            0.0f
        ).apply {
            duration = 250L
            addListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        previousView.visibility = VISIBLE
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        previousView.visibility = GONE
                        val currentView = requireNotNull(getView(viewState))
                        currentView.visibility = VISIBLE
                        ObjectAnimator
                            .ofFloat(currentView, "alpha", 0.0f, 1.0f)
                            .setDuration(250L)
                            .start()
                    }
                }
            )
        }.start()
    }

    interface StateListener {
        fun onStateChanged(viewState: ViewState)
    }

    private class SavedState : BaseSavedState {

        val state: ViewState

        constructor(superState: Parcelable, state: ViewState) : super(superState) {
            this.state = state
        }

        @Suppress("DEPRECATION")
        constructor(parcel: Parcel) : super(parcel) {
            state = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                parcel.readSerializable(null, ViewState::class.java) ?: ViewState.CONTENT
            } else {
                parcel.readSerializable() as ViewState
            }
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeSerializable(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    companion object {
        private const val VIEW_STATE_CONTENT = 0
        private const val VIEW_STATE_ERROR = 1
        private const val VIEW_STATE_EMPTY = 2
        private const val VIEW_STATE_LOADING = 3
    }
}