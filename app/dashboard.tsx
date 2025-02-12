import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";

const Dashboard = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <Text>Successfully signed in!</Text>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default Dashboard;
