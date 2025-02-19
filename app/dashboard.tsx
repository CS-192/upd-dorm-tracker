import React from "react";
import { SafeAreaView, ScrollView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import Ptext, { Ptitle } from "@/project_components";
import DashboardCard, { DashboardGrid } from "@/components/dashboard";
import { AntDesign } from "@expo/vector-icons";

const Dashboard = () => {
  return (
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
    </SafeAreaProvider>
  );
};

export default Dashboard;
