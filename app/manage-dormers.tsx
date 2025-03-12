import React from "react";
import { SafeAreaView, Text, TouchableOpacity, View } from "react-native";
import { SafeAreaProvider } from "react-native-safe-area-context";
import styles from "./styles";
import SearchComponent from "@/components/requests_search";
import DormersTableComponent from "@/components/dormers_table";
import { Ptitle } from "@/project_components";
import { Link, useRouter } from "expo-router";

const ManageDormers = () => {
  const router = useRouter();

  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <View style={styles.manageDormersContainer}>
          <Ptitle style={{ marginBottom: 10 }}>
            Manage Dormer's Information
          </Ptitle>
          <SearchComponent />
          <DormersTableComponent />
          <TouchableOpacity
            style={styles.addDormerButton}
            onPress={() => router.push("/add-dormers")}
          >
            <Text style={styles.addDormerButtonText}>Add Dormer</Text>
          </TouchableOpacity>
        </View>
      </SafeAreaView>
    </SafeAreaProvider>
  );
};

export default ManageDormers;
