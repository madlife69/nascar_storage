package tn.esprit.nascar.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tn.esprit.nascar.models.Events


//TODO 9 Change this interface to a DAO and add 4 methods with the proper annotation:
// insertEvent(events: Events)
// deleteEvent(events: Events)
// getAllEvent() : MutableList<Events>
// getFindEventById(id: Int) : Events

@Dao
interface EventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Events)

    @Delete
    fun deleteEvent(event: Events)

//    @Query("DELETE  FROM Events")
//    fun deleteEvents()

    @Query("SELECT * FROM Events")
    fun getAllEvents(): MutableList<Events>

    @Query("SELECT * FROM Events WHERE id = :eventId")
    fun getEventById(eventId: Int): Events

    @Query("SELECT COUNT(*) FROM events WHERE id = :eventId")
    fun doesEventExist(eventId: Int): Int
}