import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";

const LatePass = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Late/Overnight Pass Request</Text>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default LatePass;
