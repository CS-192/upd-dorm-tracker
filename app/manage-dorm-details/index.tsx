import React from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import DormDetailsButtons from "@/components/dorm_details_buttons";


const ManageDormDetails = () => {
  return (
    <SafeAreaProvider>
        <SafeAreaView style={styles.container}>
            <SafeAreaView style={styles.manageRequestsContainer}>
                <Text style={styles.tabHeader}>Manage dorm details</Text>
                <DormDetailsButtons />
            </SafeAreaView>
        </SafeAreaView>
    </SafeAreaProvider>
  );
};


export default ManageDormDetails;
