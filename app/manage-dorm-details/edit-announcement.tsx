import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import EditAnnouncementForm from "@/components/edit-announcement";



const EditAnnouncement = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Edit Announcement</Text>
            <EditAnnouncementForm />
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default EditAnnouncement;
