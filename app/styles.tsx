import { StyleSheet, Dimensions } from "react-native";

// All styles should be placed here to have a more uniform styling.
// Style names should be in PascalCase (camelCase but first letter capitalized).
// To add multiple styles to a component do style={[styles.style1, styles.style2]}

const windowWidth = Dimensions.get("window").width;
const windowHeight = Dimensions.get("window").height;

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
  googleSignInButton: {
    width: "100%",
    height: 45,
    backgroundColor: "#8B0000",
    borderRadius: 4,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 8,
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
  tabHeader: {
    fontSize: 36,
    fontWeight: "bold",
  },
  manageRequestsContainer: {
    flex: 1,
    backgroundColor: "#fff",
    width: "100%",
    paddingLeft: 33,
    paddingRight: 33,
    marginBottom: 33,
    marginTop: 100,
    flexDirection: "column",
  },
  manageDormersContainer: {
    flex: 1,
    backgroundColor: "#fff",
    width: "100%",
    paddingLeft: 33,
    paddingRight: 33,
    marginBottom: 33,
    // marginTop: 50,
    flexDirection: "column",
  },
  tableContainer: {
    paddingTop: 5,
    width: "100%",
    alignContent: "center",
    justifyContent: "center",
    paddingBottom: 5,
  },
  head: {
    height: 40,
    backgroundColor: "#D3D3D3",
    //padding: 5,
  },
  headtext: {
    margin: 10,
    textAlign: "left",
    fontSize: 13,
    fontWeight: "bold",
  },
  text: {
    margin: 10,
    textAlign: "left",
    fontSize: 13,
  },
  tableScrollView: {
    maxHeight: 500,
  },
  row: {
    flexDirection: "row",
    height: 50,
    alignItems: "center",
    borderBottomWidth: 1,
    borderColor: "#b2b2b2",
    borderLeftWidth: 1,
    borderRightWidth: 1,
  },
  cell: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    minWidth: 100,
  },
  requestTypesText: {
    fontSize: 12,
    color: "#333",
    marginBottom: 3,
    textAlign: "left",
    fontWeight: "bold",
  },
  requestTypesContainer: {
    backgroundColor: "#fff",
    justifyContent: "center",
    width: "100%",
    marginTop: 20,
    borderBottomWidth: 1,
    borderBottomColor: "#fff",
  },
  dormDetailsButton: {
    width: "100%",
    height: 45,
    backgroundColor: "#F6F6F6",
    borderRadius: 7,
    borderColor: "#CACACA",
    borderWidth: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 8,
  },
  dormDetailsButtonText: {
    color: "#000",
    fontSize: 18,
    fontWeight: "bold",
    alignSelf: "flex-start",
    marginLeft: 20,
  },
  addDormerButton: {
    width: "100%",
    height: 45,
    backgroundColor: "#F6F6F6",
    borderRadius: 7,
    borderColor: "#CACACA",
    borderWidth: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 8,
  },
  addDormerButtonText: {
    color: "#000",
    fontSize: 18,
    fontWeight: "bold",
    alignSelf: "flex-start",
    marginLeft: 20,
    padding: 5,
  },
});

export default styles;
