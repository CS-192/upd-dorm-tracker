package com.example.upddormtracker.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.upddormtracker.R
import com.example.upddormtracker.datamodel.Request
import com.google.firebase.firestore.FirebaseFirestore

class RequestTrackerAdapter (private val requestList: MutableList<Request>,
                             private val firestore: FirebaseFirestore
): RecyclerView.Adapter<RequestTrackerAdapter.RequestTrackerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestTrackerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_request_dormer,
            parent, false
        )
        return RequestTrackerViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: RequestTrackerViewHolder, position: Int) {
        val currentItem = requestList[position]
        holder.bind(currentItem, position)
    }

    inner class RequestTrackerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.colDate)
        private val type: TextView = itemView.findViewById(R.id.colType)
        private val view: TextView = itemView.findViewById(R.id.colViewBtn)


        fun bind(request: Request, position: Int) {
            // Set text for TextViews
            date.text = request.timestamp
            type.text = request.type
            view.setOnClickListener {
                showFullDetails(request.documentId, request.type, request.timestamp)
                }
        }

        private fun showFullDetails(id: String, type: String, date: String) {
            // WARNING! spaghetti code ahead (T_T)
            firestore.collection("requests")
                .document(id)
                .get()
                .addOnSuccessListener { document ->
                        if (type=="pass") {
                            val name = document.getString("name")?:""
                            val dorm = document.getString("dorm")?:""
                            val typeOfPass = document.getString("pass")?:""
                            val departureDate = document.getString("departureDate")?:""
                            val arrivalDate = document.getString("arrivalDate")?:""
                            val destination = document.getString("destination")?:""
                            val reason = document.getString("reason")?:""
                            val comment = document.getString("comment")?:""
                            AlertDialog.Builder(itemView.context)
                                .setTitle("PASS DETAILS")
                                .setMessage(
                                    "Name:  ${name.uppercase()}\n" +
                                            "Dorm:  ${dorm.uppercase()}\n" +
                                            "Type:  ${typeOfPass.uppercase()}\n"+
                                            "Departure Date:    $departureDate\n"+
                                            "Arrival Date:    $arrivalDate\n"+
                                            "Destination:    ${destination.uppercase()}\n"+
                                            "Reason:    $reason\n"+
                                            "Comment:   $comment\n" +
                                            "Date submitted: $date"
                                        )
                                .setNegativeButton("Cancel", null)
                                .show()
                        } else if (type=="billing") {
                            val name = document.getString("name")?:""
                            val dorm = document.getString("dorm")?:""
                            val startDate = document.getString("startDate")?:""
                            val endDate = document.getString("endDate")?:""
                            AlertDialog.Builder(itemView.context)
                                .setTitle("BILLING DETAILS")
                                .setMessage(
                                    "Name:  ${name.uppercase()}\n" +
                                            "Dorm:  ${dorm.uppercase()}\n" +
                                            "Start Date:    $startDate\n"+
                                            "End Date:    $endDate\n"+
                                            "Date submitted: $date"
                                )
                                .setNegativeButton("Cancel", null)
                                .show()
                        } else {
                            val name = document.getString("name")?:""
                            val dorm = document.getString("dorm")?:""
                            val subject = document.getString("subject")?:""
                            val details = document.getString("details")?:""

                            AlertDialog.Builder(itemView.context)
                                .setTitle("REPORT DETAILS")
                                .setMessage(
                                    "Name:  ${name.uppercase()}\n" +
                                            "Dorm:  ${dorm.uppercase()}\n" +
                                            "Subject    $subject\n"+
                                            "Details:    $details\n"+
                                            "Date submitted: $date"
                                )
                                .setNegativeButton("Cancel", null)
                                .show()

                        }


                }





                .addOnFailureListener {
                    Toast.makeText(
                        itemView.context,
                        "Error fetching request details from the database.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }


    }
}
