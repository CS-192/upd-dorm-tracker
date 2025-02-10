import React from "react";
import { Text, View } from "react-native";
import { styles } from "@/app/styles";
const logo = require("../assets/images/icon.png")

// This component contains the login card

const Login = () => {
  return (
    <>
      <View style={styles.LoginCard}>
        <Text>Add fields and other stuff here ?</Text>
      </View>
    </>
  );
};



const Logo = () => {
  return (
    <View style={styles.ImageWrapper}>
      <img src={logo}
          style={styles.Logo}/>
    </View>
    
  );
};

export {Login, Logo};