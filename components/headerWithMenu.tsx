// HeaderWithMenu.tsx
import React, { useState } from "react";
import { View, TouchableOpacity, StyleSheet, Dimensions } from "react-native";
import { AntDesign } from "@expo/vector-icons";
import NavigationMenu from "../components/NavigationMenu";

interface HeaderWithMenuProps {
  userRole?: "admin" | "student";
  children: React.ReactNode;
}

// We'll assume the header is ~54px tall
const HEADER_HEIGHT = 54;

const HeaderWithMenu: React.FC<HeaderWithMenuProps> = ({ userRole = "admin", children }) => { // Change userRole from "student" to "admin" or vice versa. NOTE changing roles seem to be a very hard task atm so calm down lng:)
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen((prev) => !prev);
  };

  return (
    <View style={{ flex: 1 }}>
      {/* ABSOLUTE HEADER */}
      <View style={[styles.header, { height: HEADER_HEIGHT, position: "absolute", top: 0, left: 0, right: 0 }]}>
        <TouchableOpacity onPress={toggleMenu}>
          <AntDesign name="menu-fold" size={24} color="white" style={styles.icon} />
        </TouchableOpacity>
        <View style={styles.iconContainer}>
          <AntDesign name="bells" size={24} color="white" style={styles.icon} />
          <AntDesign name="user" size={24} color="white" style={styles.icon} />
        </View>
      </View>

      {/* NAVIGATION MENU (also absolute) */}
      <NavigationMenu isVisible={isMenuOpen} toggleMenu={toggleMenu} userRole={userRole} />

      {/* MAIN CONTENT - pushed down by HEADER_HEIGHT */}
      <View style={{ flex: 1, marginTop: HEADER_HEIGHT }}>
        {children}
      </View>
    </View>
  );
};

export default HeaderWithMenu;

const styles = StyleSheet.create({
  header: {
    backgroundColor: "#800000",
    paddingHorizontal: 15,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    zIndex: 2,
    // We define `height` in-line above
  },
  iconContainer: {
    flexDirection: "row",
    gap: 15,
  },
  icon: {
    marginHorizontal: 10,
  },
});
