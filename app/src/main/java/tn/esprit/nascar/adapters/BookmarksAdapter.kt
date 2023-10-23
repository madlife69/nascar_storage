package tn.esprit.nascar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tn.esprit.nascar.databinding.SingleItemBookmarkBinding
import tn.esprit.nascar.models.Events
import tn.esprit.nascar.utils.AppDatabase

class BookmarksAdapter(val eventsList: MutableList<Events>, val context: Context) : RecyclerView.Adapter<BookmarksAdapter.BookmarksHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksHolder {
        val binding = SingleItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarksHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarksHolder, position: Int) {
        with(holder){
            with(eventsList[position]){
                binding.eventTitle.text = title
                binding.eventDescription.text = description
                binding.eventImage.setImageResource(imageRes)
                binding.btnRemoveBookmark.setOnClickListener {
                    //TODO 13 Delete this event from the database and refresh the list
                    val event = eventsList[position]
                    AppDatabase.getDatabase(context).EventDao()?.deleteEvent(event)
                    eventsList.removeAt(position)
                    notifyDataSetChanged()
                    val snackbar =
                        Snackbar.make(binding.root, " BookMark deleted successfully  ", Snackbar.LENGTH_LONG)
                            .setAction("action", null).show()
                }
            }
        }
    }

    override fun getItemCount() = eventsList.size

    inner class BookmarksHolder(val binding: SingleItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root)
}