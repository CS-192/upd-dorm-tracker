package com.example.upddormtracker.ui.scan_id

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.R
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.internal.wait
import com.google.firebase.firestore.FieldValue
import java.time.ZoneId
import java.time.ZonedDateTime



@Suppress("DEPRECATION")
class ScanIDFragment : Fragment() {

    private lateinit var textView: TextView
    private var nfcAdapter: NfcAdapter? = null
    private var entryOrExit = "entry"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.scan_id, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entryExitGroup = view.findViewById<RadioGroup>(R.id.entryExitGroup)
        entryExitGroup.setOnCheckedChangeListener { _, checkedId ->
            entryOrExit = if (checkedId == R.id.exitRadio) "exit" else "entry"
        }

        textView = view.findViewById(R.id.nfcText)
        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

        if (nfcAdapter == null) {
            textView.text = "NFC is not supported on this device."
        }
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        val intent = Intent(activity, activity.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            activity,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(requireActivity())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun handleTagIntent(intent: Intent) {
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        val id = tag?.id?.joinToString(":") { byte -> "%02X".format(byte) } ?: return

        val db = FirebaseFirestore.getInstance()
        val dormersRef = db.collection("dormers")

        dormersRef.whereEqualTo("rfid_number", id).get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val dormerDoc = documents.documents[0]
                    val firstName = dormerDoc.getString("firstName") ?: ""
                    val lastName = dormerDoc.getString("lastName") ?: ""
                    val studentId = dormerDoc.getString("studentNumber")?: ""
                    val dorm = dormerDoc.getString("dorm") ?: "Unknown"

                    // Create a log in "entry-or-exit" collection
                    val logEntry = hashMapOf(
                        "date" to FieldValue.serverTimestamp(),
                        "dorm" to dorm,
                        "entryOrExit" to entryOrExit,
                        "studentNumber" to studentId
                    )

                    db.collection("entry-or-exit").add(logEntry)
                        .addOnSuccessListener {
                            // Log curfew violation if after 10 PM (Asia/Manila time)
                            val manilaZoneId = ZoneId.of("Asia/Manila")
                            val now = ZonedDateTime.now(manilaZoneId)

                            if (now.hour >= 22) {
                                val violationEntry = hashMapOf(
                                    "date" to FieldValue.serverTimestamp(),
                                    "studentNumber" to studentId,
                                    "violation" to "entry or exit after curfew hours"
                                )

                                db.collection("violation").add(violationEntry)
                                    .addOnSuccessListener {
                                        Log.d("Violation", "Violation logged for late activity")
                                    }
                                    .addOnFailureListener {
                                        Log.e("Violation", "Failed to log violation: ${it.message}")
                                    }
                            }

                            // Navigate after logging (regardless of violation outcome)
                            val bundle = bundleOf(
                                "rfid" to id,
                                "first_name" to firstName,
                                "last_name" to lastName,
                                "student_id" to studentId
                            )
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.successfulScanFragment, bundle)
                        }

                        .addOnFailureListener {
                            // Optional: Show error or fallback to navigation anyway
                            Toast.makeText(requireContext(), "Failed to log entry: ${it.message}", Toast.LENGTH_SHORT).show()
                            // Proceed with navigation even on log failure
                            val bundle = bundleOf(
                                "rfid" to id,
                                "first_name" to firstName,
                                "last_name" to lastName,
                                "student_id" to studentId
                            )
                            findNavController().popBackStack()
                            findNavController().navigate(R.id.successfulScanFragment, bundle)
                        }
                } else {
                    findNavController().popBackStack()
                    findNavController().navigate(R.id.unsuccessfulScanFragment)
                }
            }
            .addOnFailureListener {
                textView.text = "Failed to check database: ${it.message}"
            }
    }


}
