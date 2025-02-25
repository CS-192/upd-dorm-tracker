import React, { useState } from "react";
import { SafeAreaProvider } from "react-native-safe-area-context";
import { SafeAreaView, View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import { Link } from "expo-router";

const Dashboard = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [userRole, setUserRole] = useState("admin"); // Change to "student/admin" to test student/admin menu

  const toggleMenu = () => {
    console.log("Navigation menu toggled:", !isMenuOpen);
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        {/* Header Section */}
        <View style={styles.header}>
          <TouchableOpacity onPress={toggleMenu}>
            <AntDesign name="menu-fold" size={24} color="white" style={styles.icon} />
          </TouchableOpacity>
          <View style={styles.iconContainer}>
            <AntDesign name="bells" size={24} color="white" style={styles.icon} />
            <AntDesign name="user" size={24} color="white" style={styles.icon} />
          </View>
        </View>

        {/* Sidebar Navigation Menu */}
        {isMenuOpen && (
          <TouchableOpacity style={styles.sidebar} onPress={toggleMenu} activeOpacity={1}>
            <View style={styles.sidebarContent}>
              {userRole === "admin" ? (
                <>
                  <Text style={styles.menuItem}>☐ Dashboard</Text>
                  <Text style={styles.menuItem}>☐ Scan UP ID</Text>
                  <Text style={styles.menuItem}>☐ Maintain Dormer's Information</Text>
                  <Text style={styles.menuItem}>☐ Maintain Requests</Text>
                  <Text style={styles.menuItem}>☐ View & Update Dorm Details</Text>
                </>
              ) : (
                <>
                  <Text style={styles.menuItem}>☐ Announcements</Text>
                  <Text style={styles.menuItem}>☐ Create a Request</Text>
                  <Text style={styles.subMenuItem}> Late Night/Overnight Pass</Text>
                  <Text style={styles.subMenuItem}> Monthly Bill</Text>
                  <Text style={styles.subMenuItem}> Report/Concerns</Text>
                  <Text style={styles.menuItem}>☐ View Dorm Details</Text>
                  <Text style={styles.menuItem}>☐ FAQs</Text>
                </>
              )}
            </View>
          </TouchableOpacity>
        )}

        {/* Dashboard Content */}
        {!isMenuOpen && (
          <>
            <Text style={styles.title}>Dashboard</Text>
            <View style={styles.buttonContainer}>
              <Link href="/dashboard" asChild>
                <TouchableOpacity style={styles.button}>
                  <Text style={styles.buttonText}>SCAN UP RFID</Text>
                </TouchableOpacity>
              </Link>
              <Link href="/dashboard" asChild>
                <TouchableOpacity style={styles.button}>
                  <Text style={styles.buttonText}>DORMERS</Text>
                </TouchableOpacity>
              </Link>
            </View>
            <View style={styles.buttonContainer}>
              <Link href="/dashboard" asChild>
                <TouchableOpacity style={styles.button}>
                  <Text style={styles.buttonText}>REQUESTS</Text>
                </TouchableOpacity>
              </Link>
              <Link href="/" asChild>
                <TouchableOpacity style={styles.button}>
                  <Text style={styles.buttonText}>DORM DETAILS</Text>
                </TouchableOpacity>
              </Link>
            </View>
            
            {/* Stats Section */}
            <View style={styles.card}>
              <AntDesign name="team" size={50} color="black" />
              <Text style={styles.statistic}>245/300</Text>
              <Text style={styles.cardTitle}>Number of People Inside</Text>
            </View>
            
            <View style={styles.card}>
              <AntDesign name="home" size={50} color="black" />
              <Text style={styles.statistic}>100/100</Text>
              <Text style={styles.cardTitle}>Rooms are Currently Occupied</Text>
            </View>
            
            <View style={styles.card}>
              <AntDesign name="filetext1" size={50} color="black" />
              <Text style={styles.statistic}>23</Text>
              <Text style={styles.cardTitle}>New Request Submitted</Text>
            </View>
          </>
        )}
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
  },
  header: {
    backgroundColor: "#800000", // Maroon color
    padding: 15,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    zIndex: 2,
  },
  iconContainer: {
    flexDirection: "row",
    gap: 15,
  },
  icon: {
    marginHorizontal: 10,
  },
  sidebar: {
    position: "absolute",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "#333",
    paddingTop: 70,
    zIndex: 1,
  },
  sidebarContent: {
    paddingLeft: 20,
  },
  menuItem: {
    color: "white",
    fontSize: 18,
    marginBottom: 15,
  },
  subMenuItem: {
    color: "white",
    fontSize: 16,  // Slightly smaller than main menu items
    marginLeft: 20, // Indent submenu items
    marginBottom: 10,
  },  
  title: {
    textAlign: "left",
    padding: 20,
    paddingBottom: 0,
    fontSize: 24,
    fontWeight: "bold",
  },
  buttonContainer: {
    flexDirection: "row",
    justifyContent: "center",
    gap: 10,
    padding: 10,
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
  buttonText: {
    fontSize: 16,
    fontWeight: "bold",
  },
  card: {
    backgroundColor: "#f9f9f9",
    padding: 20,
    margin: 10,
    borderRadius: 10,
    alignItems: "center",
    shadowColor: "#000",
    shadowOpacity: 0.1,
    shadowRadius: 5,
    elevation: 3,
  },
  statistic: {
    fontSize: 20,
    fontWeight: "bold",
  },
  cardTitle: {
    fontSize: 16,
    color: "gray",
  },
});

export default Dashboard;
