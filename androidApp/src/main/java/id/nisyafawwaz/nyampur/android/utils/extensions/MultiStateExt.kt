package id.nisyafawwaz.nyampur.android.utils.extensions

import id.nisyafawwaz.nyampur.android.utils.views.MultiStateView

fun MultiStateView.showLoading() {
    this.viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showDefault() {
    this.viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showError() {
    this.viewState = MultiStateView.ViewState.ERROR
}

fun MultiStateView.showEmpty() {
    this.viewState = MultiStateView.ViewState.EMPTY
}