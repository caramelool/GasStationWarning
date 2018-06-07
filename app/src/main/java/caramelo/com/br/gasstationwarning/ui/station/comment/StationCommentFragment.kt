package caramelo.com.br.gasstationwarning.ui.station.comment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import caramelo.com.br.gasstationwarning.R
import caramelo.com.br.gasstationwarning.data.model.Comment
import caramelo.com.br.gasstationwarning.data.model.Station
import caramelo.com.br.gasstationwarning.ui.BaseFragment
import caramelo.com.br.gasstationwarning.ui.station.detail.EXTRA_STATION
import kotlinx.android.synthetic.main.fragment_station_detail_comment.*
import org.kodein.di.generic.instance

class StationCommentFragment : BaseFragment(stationCommentModule.init) {

    companion object {
        fun newInstance(
                station: Station
        ) = StationCommentFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_STATION, station)
            }
        }
    }

    private val viewModel: StationCommentViewModel by kodein.instance()
    private val adapter = StationCommentAdapter { comment ->
        if (viewModel.canDeleteComment(comment)) {
            showDeleteDialog(comment)
        }
    }
    private var deleteDialog: AlertDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_station_detail_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        commentEditText.requestFocus()

        sendButton.setOnClickListener {
            val comment = commentEditText.text.toString()
            viewModel.addComment(comment)
            commentEditText.setText("")
        }

        viewModel.commentLiveData.observe(this, detailObserver)
        viewModel.listComments()
    }

    private val detailObserver = Observer<List<Comment>> {
        val list = it ?: listOf()
        when {
            list.isNotEmpty() -> {
                recyclerView.visibility = View.VISIBLE
                emptyGroup.visibility = View.GONE
                adapter.data = list
            }
            else -> {
                recyclerView.visibility = View.GONE
                emptyGroup.visibility = View.VISIBLE
            }
        }
    }

    private fun showDeleteDialog(comment: Comment) {
        val context = context ?: return
        if (deleteDialog?.isShowing == true) return
        deleteDialog = AlertDialog.Builder(context)
                .setTitle(R.string.title_delete_comment)
                .setMessage(R.string.message_delete_comment)
                .setPositiveButton(R.string.yes, { _, _ ->
                    viewModel.deleteComment(comment)
                })
                .setNegativeButton(R.string.no, null)
                .show()

    }
}
