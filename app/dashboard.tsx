import React, { useState, useEffect } from "react";
import { View, Text, Button } from "react-native";
import { useRouter } from "expo-router";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { signOut } from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";

const Dashboard = () => {
  const router = useRouter();
  const [email, setEmail] = useState("Guest"); // Default to Guest
  const auth = FIREBASE_AUTH;

  //Basically retrieves email from email stored in login.tsx
  useEffect(() => {
    const fetchEmail = async () => {
      const storedEmail = await AsyncStorage.getItem("userEmail");
      if (storedEmail) {
        setEmail(storedEmail); //Set email to be displayed to the stored email
      }
    };

    fetchEmail();
  }, []);

  const handleLogout = async () => {
    try {
      await signOut(auth); // Log out from Firebase
      await AsyncStorage.removeItem("userToken"); // Remove stored token
      await AsyncStorage.removeItem("userEmail"); // Remove stored email
      router.push("/"); // Balik login
    } catch (error) {
      console.error("Failed to logout:", error);
    }
  };

  return (
    <View>
      <Text>Welcome to the Dashboard!</Text>
      <Text>Hello, {email.split("@")[0]}!</Text> 
      <Button title="Logout" onPress={handleLogout} />
    </View>
  );
};

export default Dashboard;
