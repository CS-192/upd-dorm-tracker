import { StyleSheet, Dimensions } from "react-native";

// All styles should be placed here to have a more uniform styling.
// Style names should be in PascalCase (camelCase but first letter capitalized).
// To add multiple styles to a component do style={[styles.style1, styles.style2]}

const windowWidth = Dimensions.get("window").width;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
    width: "100%",
  },
  imageWrapper: {
    padding: 24,
    justifyContent: "center",
  },
  logo: {
    width: 204,
    height: 204,
    resizeMode: "contain",
  },
  formCard: {
    width: windowWidth * 0.85,
    backgroundColor: "#fff",
    padding: 20,
    borderRadius: 8,
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  label: {
    fontSize: 16,
    color: "#333",
    marginBottom: 8,
  },
  input: {
    width: "100%",
    height: 40,
    borderWidth: 1,
    borderColor: "#ddd",
    borderRadius: 4,
    marginBottom: 16,
    paddingHorizontal: 12,
    backgroundColor: "#fff",
  },
  signInButton: {
    width: "100%",
    height: 45,
    backgroundColor: "#8B0000",
    borderRadius: 4,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 8,
  },
  signInButtonText: {
    color: "#fff",
    fontSize: 16,
    fontWeight: "500",
  },
  forgotPasswordContainer: {
    marginTop: 16,
    alignItems: "center",
  },
  forgotPassword: {
    color: "#666",
    fontSize: 14,
    textDecorationLine: "underline",
  },
});

export default styles;
