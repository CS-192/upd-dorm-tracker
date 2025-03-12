import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";

const MonthlyBill = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Monthly Bill Request</Text>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default MonthlyBill;
