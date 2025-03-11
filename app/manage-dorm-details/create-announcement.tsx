import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import App from "@/components/add-announcement";



const CreateAnnouncement = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Add Announcement</Text>
            <App />
            
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default CreateAnnouncement;
