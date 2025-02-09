import React from "react";
import { Text, View } from "react-native";
import { styles } from "@/app/styles";

// This component contains the login card

const Login = () => {
  return (
    <>
      <View style={styles.LoginCard}>
        <Text style={styles.White}>This is the login component</Text>
      </View>
    </>
  );
};

export default Login;
