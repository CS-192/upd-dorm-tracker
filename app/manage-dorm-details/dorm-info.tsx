import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "../styles";
import DisplayDormInfo from "@/components/display-dorm-info";


const DormInfo = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.manageRequestsContainer}>
            <Text style={styles.tabHeader}>Dorm information</Text>
            <DisplayDormInfo/>
        </SafeAreaView>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default DormInfo;
