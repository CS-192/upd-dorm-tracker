import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import EditFAQForm from "@/components/edit-faq";



const EditFAQ = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Edit FAQ</Text>
            <EditFAQForm />
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default EditFAQ;
