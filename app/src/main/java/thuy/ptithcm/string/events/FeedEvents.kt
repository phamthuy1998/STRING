package thuy.ptithcm.string.events

import thuy.ptithcm.string.model.Feed

interface FeedEvents {
    fun onSaveClick(id: Int)
    fun onStringClick(id: Int)
    fun onLikeClick(id: Int)
    fun onCommentClick(id: Int)
    fun onShowMoreClick(id: Int)
    fun feedItemClick(feed: Feed, position: Int)
}