package com.example.upddormtracker.ui.managedormers

import android.app.Activity
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.upddormtracker.databinding.FragmentRegisterRfidBinding

class RegisterRFIDFragment : Fragment() {
    private var _binding: FragmentRegisterRfidBinding? = null
    private val binding get() = _binding!!

    private var nfcAdapter: NfcAdapter? = null
    private var dormerDocId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterRfidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dormerDocId = arguments?.getString("docId")
        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(requireContext(), activity?.javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = android.app.PendingIntent.getActivity(
            requireContext(), 0, intent, android.app.PendingIntent.FLAG_MUTABLE
        )
        val filter = arrayOf(arrayOf("android.nfc.tech.Ndef"))
        nfcAdapter?.enableForegroundDispatch(activity, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handleNfcIntent(intent: Intent) {
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        val rfid = tag?.id?.joinToString(":") { "%02X".format(it) } ?: return

        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()

        dormerDocId?.let { currentDormerId ->
            val dormersRef = db.collection("dormers")

            // First, check if this RFID is already in use
            dormersRef.whereEqualTo("rfid_number", rfid).get()
                .addOnSuccessListener { documents ->
                    val conflict = documents.any { it.id != currentDormerId }
                    if (conflict) {
                        Toast.makeText(requireContext(), "This RFID is already registered to another dormer.", Toast.LENGTH_LONG).show()
                    } else {
                        // Safe to update
                        dormersRef.document(currentDormerId)
                            .update("rfid_number", rfid)
                            .addOnSuccessListener {
                                Toast.makeText(requireContext(), "RFID registered successfully", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            }
                            .addOnFailureListener {
                                Toast.makeText(requireContext(), "Failed to register RFID: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error checking existing RFIDs: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

}
