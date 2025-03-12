import { useEffect, useState } from "react";
import {
  View,
  ActivityIndicator,
  SafeAreaView,
  StyleSheet,
  Alert,
  Button,
  TouchableOpacity,
} from "react-native";
import { doc, getDoc, deleteDoc } from "firebase/firestore";
import { useLocalSearchParams, useRouter } from "expo-router";
import { FIREBASE_AUTH, FIREBASE_DB } from "@/FirebaseConfig";
import Ptext, { Ptitle } from "@/project_components";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";

export default function Dormer() {
  const { id } = useLocalSearchParams();
  const [dormer, setDormer] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const fetchDormer = async () => {
      if (!id || !FIREBASE_AUTH) return; // Ensure ID exists before querying

      try {
        const dormerRef = doc(FIREBASE_DB, "dormers", id as string);
        const dormerSnap = await getDoc(dormerRef);

        if (dormerSnap.exists()) {
          setDormer(dormerSnap.data());
        } else {
          console.error("Dormer not found");
        }
      } catch (error) {
        console.error("Error fetching dormer:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDormer();
  }, [id]);

  const handleDelete = async () => {
    if (!id) return;

    Alert.alert(
      "Confirm Delete",
      "Are you sure you want to delete this dormer?",
      [
        { text: "Cancel", style: "cancel" },
        {
          text: "Delete",
          style: "destructive",
          onPress: async () => {
            try {
              await deleteDoc(doc(FIREBASE_DB, "dormers", id as string));
              Alert.alert("Success", "Dormer deleted successfully!");
              router.replace("/manage-dormers"); // Redirect after delete
            } catch (error) {
              console.error("Error deleting dormer:", error);
              Alert.alert("Error", "Failed to delete dormer.");
            }
          },
        },
      ]
    );
  };

  if (loading) {
    return <ActivityIndicator size="large" />;
  }

  if (!dormer) {
    return <Ptext>Dormer not found.</Ptext>;
  }

  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <View style={styles.manageDormersContainer}>
          <Ptitle style={{ marginBottom: 10 }}>
            Manage Dormer's Information
          </Ptitle>
          <Ptext style={dormerStyles.details}>
            Name: {dormer.first_name} {dormer.middle_initial} {dormer.last_name}
          </Ptext>
          <Ptext style={dormerStyles.details}>
            Student ID: {dormer.student_id}
          </Ptext>
          <Ptext style={dormerStyles.details}>
            Birth Date: {dormer.birth_date}
          </Ptext>
          <Ptext style={dormerStyles.details}>
            Phone: {dormer.phone_number}
          </Ptext>
          <Ptext style={dormerStyles.details}>Dorm: {dormer.dorm}</Ptext>
          <Ptext style={dormerStyles.details}>Room: {dormer.room_number}</Ptext>
          <Ptext style={dormerStyles.details}>Address: {dormer.address}</Ptext>
          <TouchableOpacity
            onPress={handleDelete}
            disabled={loading}
            style={dormerStyles.button}
          >
            <Ptext>Delete Dormer</Ptext>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

const dormerStyles = StyleSheet.create({
  details: {
    marginVertical: 5,
    marginLeft: 20,
  },
  button: {
    borderColor: "#4e4f52",
    backgroundColor: "#e06c8d",
    borderWidth: 1,
    paddingHorizontal: 20,
    paddingVertical: 6,
    margin: "auto",
    marginTop: 10,
    borderRadius: 5,
    width: 200,
  },
});
