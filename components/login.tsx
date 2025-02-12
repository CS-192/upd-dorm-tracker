import React, { useState } from "react";
import {
  Image,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Alert,
} from "react-native";
import styles from "@/app/styles";
import { useRouter } from "expo-router";
import Toast from "react-native-toast-message";

const logo = require("../assets/images/logo_circle.png");

const Login = () => {
  const router = useRouter();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {
    console.log("Login attempted with:", username, password);

    if (username && password) {
      setTimeout(() => {
        router.push("/dashboard");
      }, 500);
    } else {
      Toast.show({
        type: "error", // Type of toast ('success', 'error', 'info')
        text1: "Username or Password is empty!", // Main toast message
        visibilityTime: 2000, // Duration before the toast disappears (in ms)
        autoHide: true, // Automatically hide the toast after the given time
      });
    }
  };

  return (
    <View style={styles.container}>
      {/* Logo outside the form card */}
      <View style={styles.imageWrapper}>
        <Image source={logo} style={styles.logo} resizeMode="contain" />
      </View>

      {/* Form card */}
      <View style={styles.formCard}>
        <Text style={styles.label}>Username</Text>
        <TextInput
          style={styles.input}
          placeholder="Username"
          value={username}
          onChangeText={setUsername}
          autoCapitalize="none"
        />

        <Text style={styles.label}>Password</Text>
        <TextInput
          style={styles.input}
          placeholder="Password"
          value={password}
          onChangeText={setPassword}
          secureTextEntry
        />

        <TouchableOpacity style={styles.signInButton} onPress={handleLogin}>
          <Text style={styles.signInButtonText}>Sign in</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.forgotPasswordContainer}>
          <Text style={styles.forgotPassword}>Forgot password?</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

export { Login };
