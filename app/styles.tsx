import { StyleSheet } from "react-native";

// All styles should be placed here to have a more uniform styling.
// Style names should be in PascalCase (camelCase but first letter capitalized).
// To add multiple styles to a component do style={[styles.style1, styles.style2]}


import { COLORS, FONT, SIZES } from "../constants/theme";

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
  },
  searchInput: {
    fontFamily: FONT.regular,
    paddingHorizontal: SIZES.small,
    paddingVertical: 8, // Added vertical padding
    borderWidth: 1,
    borderColor: "#d9d9d9", // changed color to match figma
    borderRadius: 8,
    height: 37, // Increased height
    textAlignVertical: 'center', // Center text vertically (Android)
    marginTop:5,
    marginBottom:15,
    alignSelf:'center',
    width:260
              
  },
  searchInput2: {
    fontFamily: FONT.regular,
    paddingHorizontal: SIZES.small,
    paddingVertical: 8, // Added vertical padding
    borderWidth: 1,
    borderColor: "#d9d9d9",
    borderRadius: 8,
    height: 37, // Increased height
    textAlignVertical: 'center', // Center text vertically (Android)
    marginTop:5,
    marginBottom:25,
    alignSelf:'center',
    width:260
              
  },
  searchWrapper: {
    flex: 1,
    //backgroundColor: COLORS.white,
    marginRight: SIZES.small,
    justifyContent: "center",
    alignItems: "center",
    marginTop:10,
    marginLeft: 10,
    borderWidth:1
  
    
  },
  userName: {
    fontFamily: FONT.regular,
    fontSize: SIZES.medium,
    color: COLORS.primary,
  },
  signInBtn:{
    borderWidth: 1,
    height: 35,
    borderRadius: 8,
    alignSelf: 'center',
    width: 260,
    backgroundColor: 'maroon', // Set maroon background
    justifyContent: 'center', // Center text vertically
    alignItems: 'center', // Center text horizontally
    marginBottom:15
  }

});

export default styles;