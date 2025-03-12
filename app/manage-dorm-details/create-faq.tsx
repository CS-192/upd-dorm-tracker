import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import AddFAQ from "@/components/add-faq";



const CreateFAQ = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Create FAQ</Text>
            <AddFAQ/>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default CreateFAQ;
