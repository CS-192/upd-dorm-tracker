import { StyleSheet } from "react-native";

// All styles should be placed here to have a more uniform styling.
// Style names should be in PascalCase (camelCase but first letter capitalized).
// To add multiple styles to a component do style={[styles.style1, styles.style2]}

export const styles = StyleSheet.create({
  White: {
    color: "white",
  },
  LoginCard: {
    backgroundColor: "transparent",
    flexDirection: "column",
    justifyContent: "space-between",
    padding: 24,
    borderRadius: 8,
    width: "78%",
    borderColor: "#d9d9d9",
    borderWidth: 1,
  },
  Logo:{
    width: 204,
    height: 204,
    resizeMode: "contain",
  },
  ImageWrapper:{
    padding: 24,
    justifyContent: "center",
  }

});

