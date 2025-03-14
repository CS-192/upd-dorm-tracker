import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import CreateButton from "@/components/createButton";
import DisplayFAQ from "@/components/display-faq";


const Faq = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Frequently asked questions</Text>
            <CreateButton path="faq" />
            <DisplayFAQ/>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default Faq;
