import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import CreateButton from '@/components/createButton';
import DisplayAnnouncement from "@/components/display-announcement";


const Announcement = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Announcements</Text>
            <CreateButton path="announcement" />
            <DisplayAnnouncement/>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default Announcement;
