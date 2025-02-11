import React from "react";
import { Image, Text, TextInput, View, TouchableOpacity } from "react-native";
import { styles } from "@/app/styles";
const logo = require("../assets/images/icon.png");
import { COLORS, FONT, SIZES } from "../constants/theme";
// This component contains the login card

const Login = () => {
  return (
    <>
      <View style={styles.LoginCard}>
        <Text style={styles.userName}>Username</Text>
        <View>
          <TextInput style={styles.searchInput}></TextInput>
        </View>

        <Text style={styles.userName}>Password</Text>
        <View>
          <TextInput style={styles.searchInput2}></TextInput>
        </View>

        <TouchableOpacity style={styles.signInBtn}>
          <Text
            style={{
              fontFamily: FONT.regular,
              fontSize: SIZES.medium,
              color: "white",
            }}
          >
            Sign In
          </Text>
        </TouchableOpacity>

        <TouchableOpacity>
          <Text
            style={{
              textDecorationLine: "underline",
              fontFamily: FONT.regular,
              fontSize: SIZES.small,
              color: "black",
            }}
          >
            Forgot Password?
          </Text>
        </TouchableOpacity>
      </View>
    </>
  );
};

const Logo = () => {
  return (
    <View style={styles.ImageWrapper}>
      <Image source={logo} style={styles.Logo} />
    </View>
  );
};

export { Login, Logo };
