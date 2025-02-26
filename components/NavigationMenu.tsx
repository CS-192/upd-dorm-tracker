import React from "react";
import { View, Text, StyleSheet, TouchableOpacity } from "react-native";
import { Link } from "expo-router";

interface NavigationMenuProps {
  userRole: "admin" | "student";
  toggleMenu: () => void; // Function to close the menu
}

const NavigationMenu: React.FC<NavigationMenuProps> = ({ userRole, toggleMenu }) => {
  return (
    <TouchableOpacity style={styles.sidebar} onPress={toggleMenu} activeOpacity={1}>
      <View style={styles.sidebarContent}>
        {userRole === "admin" ? (
          <>
            <Link style={styles.menuItem} href="/dashboard">☐ Dashboard</Link>
            <Text style={styles.menuItem}>☐ Scan UP ID</Text>
            <Link style={styles.menuItem} href="/manage-dormers">☐ Maintain Dormer's Information</Link>
            <Link style={styles.menuItem} href="/manage-requests">☐ Maintain Requests</Link>
            <Link style={styles.menuItem} href="./manage-dorm-details">☐ View & Update Dorm Details</Link>
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
  );
};

const styles = StyleSheet.create({
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
    fontSize: 16,
    marginLeft: 20, // Indent submenu items
    marginBottom: 10,
  },
});

export default NavigationMenu;
