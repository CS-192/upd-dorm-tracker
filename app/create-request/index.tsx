import React from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import CreateRequestButtons from "@/components/create_request_buttons";

const CreateRequest = () => {
  return (
    <SafeAreaProvider>
        <SafeAreaView style={styles.container}>
            <SafeAreaView style={styles.manageRequestsContainer}>
                <Text style={styles.tabHeader}>Create Request</Text>
                <CreateRequestButtons />
            </SafeAreaView>
        </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default CreateRequest;