import React, { useState, useEffect } from "react";
import { SafeAreaView, ScrollView, Text, View, Button } from "react-native";
import { useRouter } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { signOut } from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import Ptext, { Ptitle } from "@/project_components";
import DashboardCard, { DashboardGrid } from "@/components/dashboard";
import { AntDesign } from "@expo/vector-icons";

const Dashboard = () => {
  const router = useRouter();
  const [email, setEmail] = useState("Guest"); // Default to Guest
  const auth = FIREBASE_AUTH;

  //Basically retrieves email from email stored in login.tsx
  useEffect(() => {
    const fetchEmail = async () => {
      const storedEmail = await AsyncStorage.getItem("userEmail");
      if (storedEmail) {
        setEmail(storedEmail); //Set email to be displayed to the stored email
      }
    };

    fetchEmail();
  }, []);

  const handleLogout = async () => {
    try {
      await signOut(auth); // Log out from Firebase
      await AsyncStorage.removeItem("userToken"); // Remove stored token
      await AsyncStorage.removeItem("userEmail"); // Remove stored email
      router.push("/"); // Balik login
    } catch (error) {
      console.error("Failed to logout:", error);
    }
  };

  return (
    <View>
      <Text>Welcome to the Dashboard!</Text>
      <Text>Hello, {email.split("@")[0]}!</Text> 
      <Button title="Logout" onPress={handleLogout} />
    </View>
  );
    
const AdminDashboard = () => {
  <SafeAreaProvider>
    <SafeAreaView
      style={[
        styles.container,
        { justifyContent: "flex-start", alignItems: "baseline" },
      ]}
    >
      <ScrollView>
        <Ptitle style={{ textAlign: "left", padding: 20, paddingBottom: 0 }}>
          Dashboard
        </Ptitle>
        <DashboardGrid />
        <DashboardCard>
          <DashboardCard.Icon>
            <AntDesign name="team" size={50} color="black" />
          </DashboardCard.Icon>
          <DashboardCard.Statistic>245/300</DashboardCard.Statistic>
          <DashboardCard.Title>Number of People Inside</DashboardCard.Title>
        </DashboardCard>

        <DashboardCard>
          <DashboardCard.Icon>
            <AntDesign name="home" size={50} color="black" />
          </DashboardCard.Icon>
          <DashboardCard.Statistic>100/100</DashboardCard.Statistic>
          <DashboardCard.Title>
            Rooms are Currently Occupied
          </DashboardCard.Title>
        </DashboardCard>

        <DashboardCard>
          <DashboardCard.Icon>
            <AntDesign name="file1" size={50} color="black" />
          </DashboardCard.Icon>
          <DashboardCard.Statistic>23</DashboardCard.Statistic>
          <DashboardCard.Title>New Request Submitted</DashboardCard.Title>
        </DashboardCard>
      </ScrollView>
    </SafeAreaView>
  </SafeAreaProvider>;
};

export default Dashboard;
