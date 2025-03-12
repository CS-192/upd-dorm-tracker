import React, { useState, useEffect } from "react";
import { SafeAreaView, ScrollView, Text, View, Button } from "react-native";
import { useRouter, Link } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { SafeAreaProvider } from "react-native-safe-area-context";
import { StyleSheet, TouchableOpacity } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import { handleLogout } from "@/project_functions";
import NavigationMenu from "../components/NavigationMenu";
import DashboardCard, { DashboardGrid } from "@/components/dashboard";
import { Ptitle } from "@/project_components";

const Dashboard = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [userRole, setUserRole] = useState<"admin" | "student">("admin"); // Change to "student/admin" to test student/admin menu

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

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
      <SafeAreaView style={styles.container}>
        {/* Header Section */}
        <View style={styles.header}>
          <TouchableOpacity onPress={toggleMenu}>
            <AntDesign
              name="menu-fold"
              size={24}
              color="white"
              style={styles.icon}
            />
          </TouchableOpacity>
          <View style={styles.iconContainer}>
            <AntDesign
              name="bells"
              size={24}
              color="white"
              style={styles.icon}
            />
            <Link href="/user-profile">
              <AntDesign
                name="user"
                size={24}
                color="white"
                style={styles.icon}
              />
            </Link>
          </View>
        </View>

        {/* Sidebar Navigation Menu */}
        {isMenuOpen && (
          <NavigationMenu userRole={userRole} toggleMenu={toggleMenu} />
        )}

        {/*User Info (temporary. should be moved to different page)*/}
        <SafeAreaView
          style={[
            styles.container,
            { justifyContent: "flex-start", alignItems: "baseline" },
          ]}
        >
          <Text>Hello, {email.split("@")[0]}!</Text>
          <Button title="Logout" onPress={() => handleLogout(router)} />
        </SafeAreaView>

        {/* Dashboard Content */}
        {!isMenuOpen && (
          <ScrollView>
            <Ptitle
              style={{ textAlign: "left", padding: 20, paddingBottom: 0 }}
            >
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
    fontSize: 16, // Slightly smaller than main menu items
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
