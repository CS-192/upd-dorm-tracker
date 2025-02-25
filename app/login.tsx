import React, { useState, useEffect } from "react";
import {
  Image,
  Text,
  View,
  TextInput,
  TouchableOpacity,
  Alert,
  Button,
} from "react-native";
import styles from "@/app/styles";
import { useRouter } from "expo-router";
import Toast from "react-native-toast-message";
import AsyncStorage from "@react-native-async-storage/async-storage";
import {
  createUserWithEmailAndPassword,
  signInWithEmailAndPassword,
} from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";

const logo = require("../assets/images/logo_circle.png");

const Login = () => {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const auth = FIREBASE_AUTH;

  // Check kung nakalog in na yung user during launch
  useEffect(() => {
    const checkLoginStatus = async () => {
      const userToken = await AsyncStorage.getItem("userToken");
      if (userToken) {
        // If user is logged in, punta sa dashboard agad
        router.push("/dashboard");
      }
    };

    checkLoginStatus();
  }, []);

  const signIn = async () => {
    if (!email || !password) {
      showToast(
        "Fill out " +
          (!email && !password
            ? "username and password"
            : !email
            ? "username"
            : "password") +
          " field"
      );
      return; // Exit the function if fields are missing
    }
    setLoading(true);

    try {
      const response = await signInWithEmailAndPassword(auth, email, password);
      console.log(response);

      // Creates and saves unique user token
      const userToken = response.user.uid; // Use the UID or a JWT token if available
      await AsyncStorage.setItem("userToken", userToken);
      await AsyncStorage.setItem("userEmail", email); // Store email persistently

      showToast("Successful Log In");
      router.push("/dashboard");
    } catch (error: any) {
      console.log(error);
      alert("Error " + error);
    } finally {
      setLoading(false);
    }
  };

  const signUp = async () => {
    if (!email || !password) {
      showToast(
        "Fill out " +
          (!email && !password
            ? "username and password"
            : !email
            ? "username"
            : "password") +
          " field"
      );
      return; // Exit the function if fields are missing
    }

    // Makes sure na up mail gamit
    if (!email.endsWith("@up.edu.ph")) {
      showToast("Only @up.edu.ph emails are allowed for sign up.");
      return;
    }

    setLoading(true);

    try {
      const response = await createUserWithEmailAndPassword(
        auth,
        email,
        password
      );
      console.log(response);
      showToast("Check your email");
    } catch (error: any) {
      console.log(error);
      alert("Error " + error);
    } finally {
      setLoading(false);
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
          value={email}
          onChangeText={(text) => setEmail(text)}
          autoCapitalize="none"
        />

        <Text style={styles.label}>Password</Text>
        <TextInput
          style={styles.input}
          placeholder="Password"
          value={password}
          onChangeText={(text) => setPassword(text)}
          secureTextEntry
        />

        <TouchableOpacity style={styles.signInButton} onPress={signIn}>
          <Text style={styles.signInButtonText}>Sign in</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.forgotPasswordContainer}>
          <Text style={styles.forgotPassword}>Forgot password?</Text>
        </TouchableOpacity>

        <View style={{ marginTop: 15 }}>
          <Button title="Sign up with UP mail" onPress={signUp} />
        </View>
      </View>
    </View>
  );
};

const showToast = (message: string) => {
  Toast.show({
    type: "error", // Type of toast ('success', 'error', 'info')
    text1: message, // Main toast message
    visibilityTime: 2000, // Duration before the toast disappears (in ms)
    autoHide: true, // Automatically hide the toast after the given time
  });
};

export default Login;
