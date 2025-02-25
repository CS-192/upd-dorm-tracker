import React, { useState, useEffect } from "react";
import { SafeAreaView, ScrollView, Text, View, Button } from "react-native";
import { useRouter } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import Ptext, { Ptitle } from "@/project_components";
import DashboardCard, { DashboardGrid } from "@/components/dashboard";
import { AntDesign } from "@expo/vector-icons";
import { handleLogout } from "@/project_functions";

const Dashboard = () => {
  const [email, setEmail] = useState("Guest"); // Default to Guest
  const router = useRouter();

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

  return (
    <SafeAreaProvider>
      <SafeAreaView
        style={[
          styles.container,
          { justifyContent: "flex-start", alignItems: "baseline" },
        ]}
      >
        <Text>Hello, {email.split("@")[0]}!</Text>
        <Button title="Logout" onPress={() => handleLogout(router)} />
        <AdminDashboard />
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

const AdminDashboard = () => {
  return (
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
        <DashboardCard.Title>Rooms are Currently Occupied</DashboardCard.Title>
      </DashboardCard>

      <DashboardCard>
        <DashboardCard.Icon>
          <AntDesign name="file1" size={50} color="black" />
        </DashboardCard.Icon>
        <DashboardCard.Statistic>23</DashboardCard.Statistic>
        <DashboardCard.Title>New Request Submitted</DashboardCard.Title>
      </DashboardCard>
    </ScrollView>
  );
};

export default Dashboard;
