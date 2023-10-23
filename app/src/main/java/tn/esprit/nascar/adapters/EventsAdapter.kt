package tn.esprit.nascar.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import tn.esprit.nascar.databinding.SingleItemEventsBinding
import tn.esprit.nascar.models.Events
import tn.esprit.nascar.utils.AppDatabase

class EventsAdapter(val eventsList: MutableList<Events>, val context: Context) : RecyclerView.Adapter<EventsAdapter.EventsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsHolder {
        val binding = SingleItemEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventsHolder(binding)
    }

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        with(holder){
            with(eventsList[position]){
                binding.eventTitle.text = title
                binding.eventDescription.text = description
                binding.eventImage.setImageResource(imageRes)
                binding.btnAddBookmark.setOnClickListener {
                    //TODO 12 Check if this event is already added in the database(Show Snackbar) otherwise add it
                    val event = eventsList[position]

                    if (isEventInDatabase(event)) {
                        // Event is already in the database, show a Snackbar
                        val snackbar =
                            Snackbar.make(binding.root, " Event is already in the data base  ", Snackbar.LENGTH_LONG)
                                .setAction("action", null).show()
                    } else {
                        // Event is not in the database, add it
                        addToDatabase(event)
                        val snackbar =
                            Snackbar.make(binding.root, "  Event is added  ", Snackbar.LENGTH_LONG)
                                .setAction("action", null).show()
                    }
                }
            }
        }
    }
    private fun isEventInDatabase(event: Events): Boolean {
        val eventId = event.id
        val count = AppDatabase.getDatabase(context).EventDao()?.doesEventExist(eventId) ?: 0
        Log.d("EventsAdapter", "Event with ID $eventId exists in the database: $count > 0")
        return count > 0
    }

    private fun addToDatabase(event: Events) {
        try {
            AppDatabase.getDatabase(context).EventDao()?.insertEvent(event)
            Log.d("EventsAdapter", "Event added to the database")
        } catch (e: Exception) {
            Log.e("EventsAdapter", "Error adding event to the database: ${e.message}")
        }
    }


    override fun getItemCount() = eventsList.size

    inner class EventsHolder(val binding: SingleItemEventsBinding) : RecyclerView.ViewHolder(binding.root)
}