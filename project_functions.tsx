import { signOut } from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { Router } from "expo-router";

export const handleLogout = async (router: Router) => {
  try {
    await signOut(FIREBASE_AUTH); // Sign out from Firebase
    await AsyncStorage.removeItem("userToken"); // Remove stored token
    await AsyncStorage.removeItem("userEmail"); // Remove stored email
    router.replace("/"); // Redirect to login
  } catch (error) {
    console.error("Failed to logout:", error);
  }
};
