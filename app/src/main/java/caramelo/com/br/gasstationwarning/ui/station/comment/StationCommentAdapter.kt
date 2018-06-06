package caramelo.com.br.gasstationwarning.ui.station.comment

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Comment
import caramelo.com.br.gasstationwarning.ui.BaseViewHolder
import kotlinx.android.synthetic.main.adapter_station_detail_comment.view.*

class StationCommentAdapter(
        val onDelete: (Comment) -> Unit
) : RecyclerView.Adapter<CommentHolder>() {

    var data: List<Comment> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        return CommentHolder(parent, onDelete)
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount() = data.size
}

class CommentHolder(
        parent: ViewGroup,
        private val onDelete: (Comment) -> Unit
) : BaseViewHolder(parent, R.layout.adapter_station_detail_comment) {

    fun bind(comment: Comment) {
        with(itemView) {
            commentUserName.text = comment.user?.name
            commentText.text = comment.text
            commentDate.text = comment.date
            setOnLongClickListener {
                onDelete(comment)
                true
            }
        }
    }
}