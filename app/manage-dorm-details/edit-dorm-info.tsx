import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import EditDormInfoForm from "@/components/edit-dorm-info";



const EditDormInfo = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Edit Dorm Info</Text>
            <EditDormInfoForm />
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default EditDormInfo;
