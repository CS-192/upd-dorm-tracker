import React, { useEffect, useState } from "react";
import { SafeAreaView, Text, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { AntDesign, FontAwesome } from "@expo/vector-icons";
import SearchComponent from "@/components/requests_search";
import UserRequests from "@/components/user_requests";

const UserProfile = () => {
  const [userRole, setUserRole] = useState<"admin" | "student">("admin"); // Change to "student/admin" to test student/admin menu

  const [email, setEmail] = useState("Guest"); // Default to Guest

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

  return (
    <SafeAreaProvider>
        <SafeAreaView style={styles.container}>
            <SafeAreaView style={styles.userProfileContainer}>
                <SafeAreaView style={{flexDirection: "row", justifyContent: "space-between"}}>
                    <SafeAreaView>
                        <Text style={styles.tabHeader}>Profile</Text>
                        <Text style={styles.subTabHeader}>LName, FName, M.I.</Text>
                    </SafeAreaView>
                    {userRole === "student" &&
                      <FontAwesome
                      name="user"
                      size={80}
                      color="black"
                    />
                    }
                </SafeAreaView>
                <Text>{'\n'}</Text>
                <Text>
                  <Text style={{fontWeight: 'bold'}}>Email: </Text>
                  <Text>{email}</Text>
                </Text>
                <Text>
                  <Text style={{fontWeight: 'bold'}}>Residence: </Text>
                  <Text>Molave Residence Hall</Text> 
                </Text>
                {userRole === "student" &&
                  <SafeAreaView>
                    <SearchComponent />
                    <UserRequests />
                  </SafeAreaView>
                }
            </SafeAreaView>
        </SafeAreaView>
    </SafeAreaProvider>
  );
};


export default UserProfile;
