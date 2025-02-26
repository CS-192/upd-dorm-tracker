import React from "react";
import { SafeAreaView, Text } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import { Link } from "expo-router";

const Dashboard = () => {
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <Text>Successfully signed in!</Text>
        <Link href="/manage-requests">Go to Manage Requests</Link> 
        <Link href="./manage-dorm-details">Go to Manage Dorm Details</Link> 
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default Dashboard;
