import React, { useState } from "react";
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
import { useRouter, Link } from "expo-router";
import Toast from "react-native-toast-message";
import { createUserWithEmailAndPassword, signInWithEmailAndPassword } from "firebase/auth";
import { FIREBASE_AUTH } from "@/FirebaseConfig";

const logo = require("../assets/images/logo_circle.png");

const Login = () => {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading,setLoading] =useState(false);
  //const [testCounter, setTestCounter] = useState(0);
  const auth=FIREBASE_AUTH;


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
    
    try{
      const response=await signInWithEmailAndPassword(auth,email,password);
      console.log(response);
      showToast("Successful Log In");
      router.push("/dashboard");

      
      
    }
    catch (error:any) {
      console.log(error);
      alert("Error "+ error);

    }
    finally {
      setLoading(false);
    }
  }

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
      const response = await createUserWithEmailAndPassword(auth, email, password);
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

        <TouchableOpacity style={styles.signInButton} onPress={()=>signIn()}>
          <Text style={styles.signInButtonText}>Sign in</Text>
        </TouchableOpacity>


        <TouchableOpacity style={styles.forgotPasswordContainer}>
          <Text style={styles.forgotPassword}>Forgot password?</Text>
        </TouchableOpacity>
        <Link href="/manage_requests">Go to Manage Requests</Link> // temporary link to manage_requests
        <Link href="/manage_dorm_details">Go to Manage Dorm Details</Link> // temporary link to manage_dorm_details

         <View style={{marginTop:15}}>
        <Button
            title="Sign in with Google"
            onPress={()=>signUp()}
        />
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

export { Login };
  function setLoading(arg0: boolean) {
    throw new Error("Function not implemented.");
  }

