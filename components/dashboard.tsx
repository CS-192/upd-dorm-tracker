import Ptext from "@/project_components";
import { Link } from "expo-router";
import React, { ReactNode } from "react";
import { View, Text, StyleSheet, TouchableOpacity } from "react-native";

interface DashboardCardProps {
  children: ReactNode;
}

const DashboardCard = ({ children }: DashboardCardProps) => {
  return <View style={styles.card}>{children}</View>;
};

const DashboardCardIcon = ({ children }: { children: ReactNode }) => (
  <View style={styles.iconContainer}>{children}</View>
);

const DashboardCardTitle = ({ children }: { children: string }) => (
  <Ptext style={styles.title}>{children}</Ptext>
);

const DashboardCardStatistic = ({ children }: { children: string }) => (
  <Ptext style={styles.statistic}>{children}</Ptext>
);

DashboardCard.Icon = DashboardCardIcon;
DashboardCard.Title = DashboardCardTitle;
DashboardCard.Statistic = DashboardCardStatistic;

export const DashboardGrid = () => {
  return (
    <View style={styles.container}>
      <Link href="/dashboard" asChild>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.text}>SCAN UP RFID</Text>
        </TouchableOpacity>
      </Link>

      <Link href="/dashboard" asChild>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.text}>DORMERS</Text>
        </TouchableOpacity>
      </Link>

      <Link href="/manage-requests" asChild>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.text}>REQUESTS</Text>
        </TouchableOpacity>
      </Link>

      <Link href="./manage-dorm-details" asChild>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.text}>DORM DETAILS</Text>
        </TouchableOpacity>
      </Link>
    </View>
  );
};

export default DashboardCard;

const styles = StyleSheet.create({
  card: {
    backgroundColor: "white",
    padding: 20,
    borderRadius: 4,
    borderColor: "#0000004a",
    borderWidth: 0.2,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
    flexDirection: "column",
    alignItems: "center",
    width: "80%",
    marginLeft: "auto",
    marginRight: "auto",
    marginBottom: 20,
  },
  iconContainer: {
    marginBottom: 10,
  },
  title: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#333",
  },
  statistic: {
    fontSize: 25,
    fontWeight: "bold",
    marginBottom: 3,
  },
  container: {
    flexDirection: "row",
    flexWrap: "wrap",
    justifyContent: "center",
    gap: 10,
    padding: 20,
    paddingTop: 10,
  },
  button: {
    backgroundColor: "white",
    borderRadius: 4,
    borderColor: "#0000004a",
    borderWidth: 0.9,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 1,
    shadowRadius: 5,
    width: "43%",
    paddingVertical: 10,
    alignItems: "center",
    justifyContent: "center",
  },
  text: {
    fontSize: 16,
    fontWeight: "bold",
  },
});
